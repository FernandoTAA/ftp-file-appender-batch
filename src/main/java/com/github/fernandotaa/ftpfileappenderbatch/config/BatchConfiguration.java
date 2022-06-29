package com.github.fernandotaa.ftpfileappenderbatch.config;

import com.github.fernandotaa.ftpfileappenderbatch.appender.CounterReader;
import com.github.fernandotaa.ftpfileappenderbatch.appender.HostConcaterProcessor;
import com.github.fernandotaa.ftpfileappenderbatch.appender.FTPFileWriter;
import com.github.fernandotaa.ftpfileappenderbatch.validator.FTPFileReader;
import com.github.fernandotaa.ftpfileappenderbatch.validator.ErrorCheckingPrinterListener;
import com.github.fernandotaa.ftpfileappenderbatch.validator.HostFilterAndRemoverProcessor;
import com.github.fernandotaa.ftpfileappenderbatch.validator.ValidatorCheckerWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    @Bean("appenderStep")
    public Step step(
            StepBuilderFactory stepBuilderFactory,
            CounterReader counterReader,
            HostConcaterProcessor hostConcaterProcessor,
            FTPFileWriter ftpFileWriter
    ) {
        return stepBuilderFactory.get("appender_ftp_step")
                .<Long, String>chunk(3)
                .reader(counterReader)
                .processor(hostConcaterProcessor)
                .writer(ftpFileWriter)
                .faultTolerant()
                .build();
    }

    @Bean("checkerStep")
    public Step checkerStep(
            StepBuilderFactory stepBuilderFactory,
            FTPFileReader ftpFileReader,
            HostFilterAndRemoverProcessor hostFilterAndRemoverProcessor,
            ValidatorCheckerWriter validatorCheckerWriter,
            ErrorCheckingPrinterListener errorCheckingPrinterListener
    ) {
        final SimpleStepBuilder simpleStepBuilder = stepBuilderFactory.get("checker__ftp_step")
                .<String, Long>chunk(3)
                .reader(ftpFileReader)
                .processor(hostFilterAndRemoverProcessor)
                .writer(validatorCheckerWriter);

        simpleStepBuilder.listener(errorCheckingPrinterListener);

        return simpleStepBuilder.build();
    }

    @Bean
    public Job job(
            JobBuilderFactory jobBuilderFactory,
            @Qualifier("appenderStep") Step appenderStep,
            @Qualifier("checkerStep") Step checkerStep
    ) {
        return jobBuilderFactory.get("main_ftp_job")
                .start(appenderStep)
                .next(checkerStep)
                .build();
    }
}
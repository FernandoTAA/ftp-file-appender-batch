package com.github.fernandotaa.ftpfileappenderbatch.validator;

import com.github.fernandotaa.ftpfileappenderbatch.config.component.Checker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
public class ErrorCheckingPrinterListener implements StepExecutionListener {
    private final Checker checker;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("RUNNING beforeStep");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("RUNNING afterStep | checker.getNotExistingList: " + checker.getNotExistingList());
        return ExitStatus.COMPLETED;
    }
}

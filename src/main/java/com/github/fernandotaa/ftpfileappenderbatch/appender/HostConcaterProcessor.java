package com.github.fernandotaa.ftpfileappenderbatch.appender;

import com.github.fernandotaa.ftpfileappenderbatch.config.component.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HostConcaterProcessor implements ItemProcessor<Long, String> {

    private final Host host;

    @Override
    public String process(Long number) {
        return String.format("%s -- %s", host.getHost(), number);
    }
}

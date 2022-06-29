package com.github.fernandotaa.ftpfileappenderbatch.validator;

import com.github.fernandotaa.ftpfileappenderbatch.config.component.Host;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class HostFilterAndRemoverProcessor implements ItemProcessor<String, Long> {

    private final Host host;

    @Override
    public Long process(String text) throws Exception {
        if (!text.startsWith(host.getHost())) {
            return null;
        }
        final String[] partsOfString = text.split(" -- ");
        if (partsOfString.length < 2) {
            log.error("ERROR wrong text: " + text);
            return null;
        }
        return Long.valueOf(partsOfString[1]);
    }
}

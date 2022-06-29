package com.github.fernandotaa.ftpfileappenderbatch.appender;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CounterReader implements ItemReader<Long> {

    private final Long runUtil;
    private final AtomicLong cursor = new AtomicLong(1);

    public CounterReader(@Value("${app.run-util}") Long runUtil) {
        this.runUtil = runUtil;
    }

    @Override
    public Long read() {
        if (cursor.get() > runUtil) {
            return null;
        }
        return cursor.getAndIncrement();
    }
}

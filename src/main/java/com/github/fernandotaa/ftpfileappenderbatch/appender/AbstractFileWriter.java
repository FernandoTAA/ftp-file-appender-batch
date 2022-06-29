package com.github.fernandotaa.ftpfileappenderbatch.appender;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public abstract class AbstractFileWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) {
        list.forEach(this::append);
    }

    public abstract void append(final String text);
}

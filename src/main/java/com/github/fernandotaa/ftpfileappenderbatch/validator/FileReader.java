package com.github.fernandotaa.ftpfileappenderbatch.validator;

import lombok.SneakyThrows;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.util.Objects;

@Component
public class FileReader implements ItemReader {

    private final File file;
    private BufferedReader bufferedReader;

    public FileReader(@Value("${app.file}") File file) {
        this.file = file;
    }

    @SneakyThrows
    protected String readLine() {
        if (Objects.isNull(bufferedReader)) {
            bufferedReader = new BufferedReader(new java.io.FileReader(file));
        }
        final String line = bufferedReader.readLine();
        if (Objects.isNull(line)) {
            bufferedReader.close();
        }
        return line;
    }

    @Override
    public Object read() {
        return readLine();
    }

}

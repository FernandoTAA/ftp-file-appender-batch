package com.github.fernandotaa.ftpfileappenderbatch.validator;

import com.github.fernandotaa.ftpfileappenderbatch.config.component.Checker;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ValidatorCheckerWriter implements ItemWriter<Long> {

    private final Checker checker;

    @Override
    public void write(List<? extends Long> list) {
        list.stream()
                .filter(Objects::nonNull)
                .forEach(checker::check);
    }
}

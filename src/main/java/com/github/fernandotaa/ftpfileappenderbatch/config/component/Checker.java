package com.github.fernandotaa.ftpfileappenderbatch.config.component;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Checker {

    @Getter
    private final Set<Long> notExistingList = new HashSet<>();

    private final AtomicLong expectedNumber = new AtomicLong(1);

    public void check(Long number) {
        Long expectedNumber = this.expectedNumber.get();
        if (number > expectedNumber) {
            add(expectedNumber, number - 1);
            this.expectedNumber.set(number + 1);
            return;
        }
        if (number < expectedNumber) {
            remove(number);
            return;
        }
        this.expectedNumber.incrementAndGet();
    }

    private void add(Long from, Long to) {
        for (Long i = from; i <= to; i++) {
            notExistingList.add(i);
        }
    }

    private void remove(Long number) {
        notExistingList.remove(number);
    }
}

package com.chandlercup.supplier;

import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.function.Supplier;

@Configuration
public class OffsetDateTimeSupplier implements Supplier<OffsetDateTime> {

    @Override
    public OffsetDateTime get() {
        return OffsetDateTime.now();
    }
}

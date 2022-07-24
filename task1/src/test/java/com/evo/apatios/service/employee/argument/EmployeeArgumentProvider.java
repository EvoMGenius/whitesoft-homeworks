package com.evo.apatios.service.employee.argument;

import com.evo.apatios.service.params.SearchParams;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.UUID;
import java.util.stream.Stream;

public class EmployeeArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(SearchParams.builder()
                                         .firstName("Ivan")
                                         .lastName("Ivanov")
                                         .postId(UUID.randomUUID())
                                         .build())
                        );
    }
}

package com.evo.apatios.util;

import com.evo.apatios.exception.CreationEmployeeException;

import java.util.function.Function;

public class Guard {
    public static void check(boolean condition, String error, Function<String, ? extends RuntimeException> exceptionSupplier) {

        if (!condition) {
            throw exceptionSupplier.apply(error);
        }
    }
}

package com.avicia.api.util;

import java.util.function.Supplier;

/**
 * Utilitário para permitir o uso de lambdas que lançam exceções
 * dentro de métodos como orElseThrow(), sem precisar de return null.
 */
public class Throwing {

    private Throwing() {}

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        void run() throws RuntimeException;
    }

    public static <T> Supplier<T> supplier(ThrowingSupplier<T> throwingSupplier) {
        return () -> {
            throwingSupplier.run();
            return null; // nunca será alcançado
        };
    }

}

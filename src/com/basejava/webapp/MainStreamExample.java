package com.basejava.webapp;

import java.util.stream.IntStream;

public class MainStreamExample {
    private static int[] values = {1, 5, 0, 1, 4, 9, 2, 3, 3, 2, 3};

    public static void main(String[] args) {
        System.out.println(minValue(values));
    }

    public static int minValue(int[] values) {
        return IntStream.of(values)
                .boxed()
                .distinct()
                .sorted()
                .reduce(0, (result, i) -> 10 * result + i);
    }
}
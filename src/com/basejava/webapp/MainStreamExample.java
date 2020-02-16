package com.basejava.webapp;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MainStreamExample {

    private static int[] values = {1, 5, 1, 4, 2, 3, 3, 2, 3};
    private static int count;

    public static void main(String[] args) {
        System.out.println(minValue(values));
    }

    public static int minValue(int[] values) {
        Supplier<IntStream> is =()-> IntStream.of(values)
                .distinct()
                .sorted();

        count = (int) is.get().count();
        return is.get().reduce(0, (result, i) ->
                result + i * (int) Math.pow(10, --count));
    }
}
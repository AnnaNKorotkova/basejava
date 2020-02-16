package com.basejava.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KillEvenOrOdd {

    private static List<Integer> integers = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            integers.add((int) (Math.random() * 100));
        }
        integers.forEach(o -> System.out.print(o + ", "));
        System.out.println();
        oddOrEven(integers).forEach(o -> System.out.print(o + ", "));
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {

        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy((p) -> p % 2 == 0));
        return map.get(false).size() % 2 == 0 ? map.get(false) : map.get(true);
    }
}
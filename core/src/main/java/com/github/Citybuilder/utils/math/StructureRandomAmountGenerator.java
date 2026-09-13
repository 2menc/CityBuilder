package com.github.citybuilder.utils.math;

import java.util.Random;
import java.util.random.RandomGenerator;

public class StructureRandomAmountGenerator {

    public static int calculateCitizensInAHouse() {

        final RandomGenerator gen = new Random();

        return Math.abs(gen.nextInt() % 5);
    }

}

package org.drdivago.model;


import io.vavr.Tuple2;

import java.util.List;

public record Settings(
        List<Tuple2<Integer, Integer>> pattern,
        List<Tuple2<Integer, Integer>> patternGoal,
        String technique,
        String rest,
        String notes
        ) {
}

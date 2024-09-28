package org.drdivago.model;


import io.vavr.Tuple2;

import java.util.List;

public record Settings(
        List<Tuple2<Integer, Integer>> pattern,
        String rest,
        String notes
        ) {
}

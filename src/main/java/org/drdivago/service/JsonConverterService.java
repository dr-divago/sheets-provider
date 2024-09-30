package org.drdivago.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.drdivago.model.Exercise;
import org.drdivago.model.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class JsonConverterService {

    public List<Exercise> parse(List<List<String>> data) {

        List<Exercise> exercises = new ArrayList<>();
        for (List<String> row : data) {
            String exerciseName = "";
            String pattern = "";
            String goalPattern = "";
            String technique = "";
            String rest = "";
            String notes = "";
            for (int j = 0; j < row.size(); j++) {
                switch (j) {
                    case 0 -> exerciseName = row.get(j);
                    case 1 -> pattern = row.get(j);
                    case 2 -> goalPattern = row.get(j);
                    case 3 -> technique = row.get(j);
                    case 4 -> rest = row.get(j);
                    case 6 -> notes = row.get(j);
                }
                System.out.println(row.get(j));

            }
            exercises.add(new Exercise(exerciseName, new Settings(Collections.emptyList(), Collections.emptyList(), "",  rest, notes)));
        }
        return exercises;
    }
}

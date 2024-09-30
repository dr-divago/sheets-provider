package org.drdivago.service;

import org.drdivago.model.Exercise;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterServiceTest {

    /*
    @Test
    void testParse() {
        JsonConverterService service = new JsonConverterService();

        List<List<String>> testData = Arrays.asList(
                Arrays.asList("Lat Machine Singola", "", "4x12", "Slow", "60s", "", "Keep elbows close"),
                Arrays.asList("Squats", "4x15", "5x20", "Explosive", "90s", "", "Go deep")
        );

        List<Exercise> result = service.parse(testData);

        assertEquals(2, result.size());

        Exercise pushups = result.get(0);
        assertEquals("Lat Machine Singola", pushups.name());
        assertEquals("60s", pushups.getSettings().getRest());
        assertEquals("Keep elbows close", pushups.getSettings().getNotes());

        Exercise squats = result.get(1);
        assertEquals("Squats", squats.getName());
        assertEquals("90s", squats.getSettings().getRest());
        assertEquals("Go deep", squats.getSettings().getNotes());
    }

    @Test
    void testParseEmptyList() {
        JsonConverterService service = new JsonConverterService();

        List<List<String>> emptyData = Arrays.asList();

        List<Exercise> result = service.parse(emptyData);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseIncompleteData() {
        JsonConverterService service = new JsonConverterService();

        List<List<String>> incompleteData = Arrays.asList(
                Arrays.asList("Incomplete Exercise")
        );

        List<Exercise> result = service.parse(incompleteData);

        assertEquals(1, result.size());
        Exercise incompleteExercise = result.get(0);
        assertEquals("Incomplete Exercise", incompleteExercise.getName());
        assertEquals("", incompleteExercise.getSettings().getRest());
        assertEquals("", incompleteExercise.getSettings().getNotes());
    }

     */
}
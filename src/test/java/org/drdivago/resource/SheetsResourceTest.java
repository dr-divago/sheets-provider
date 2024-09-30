package org.drdivago.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.drdivago.service.SpreadSheetsService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class SheetsResourceTest {

    @InjectMock
    SpreadSheetsService spreadSheetsService;

    @Inject
    SheetsResource sheetsResource;

    @Test
    void testGetData_ReturnOkWithExercises() throws GeneralSecurityException, IOException {
        List<List<String>> mockData = Arrays.asList(
                Arrays.asList("1", "John", "Doe"),
                Arrays.asList("2", "Jane", "Smith")
        );

        // Mock the method call with parameters
        String spreadSheetsId = "testSpreadsheetId";
        String range = "Sheet1!A1:C2";

        when(spreadSheetsService.getData(spreadSheetsId, range)).thenReturn(mockData);

        String path = "/api/v1/sheet/{spreadSheetId}";
        given()
                .pathParam("spreadSheetId", spreadSheetsId)
                .queryParam("range", range)
                .when().get(path)
                .then()
                .statusCode(200)
                .body(is("[{\"name\":\"1\",\"setting\":{\"pattern\":[],\"patternGoal\":[],\"technique\":\"\",\"rest\":\"\",\"notes\":\"\"}},{\"name\":\"2\",\"setting\":{\"pattern\":[],\"patternGoal\":[],\"technique\":\"\",\"rest\":\"\",\"notes\":\"\"}}]"));
    }

    @Test
    public void testGetSheetDataNoContent() throws IOException, GeneralSecurityException {
        String spreadSheetsId = "testSpreadsheetId";
        String range = "Sheet1!A1:C2";

        String path = "/api/v1/sheet/{spreadSheetId}";
        when(spreadSheetsService.getData(spreadSheetsId, range)).thenReturn(Collections.emptyList());

        given()
                .pathParam("spreadSheetId", spreadSheetsId)
                .queryParam("range", range)
                .when().get(path)
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetSheetData_InternalServerStatus_IOException() throws IOException, GeneralSecurityException {
        String spreadSheetsId = "testSpreadsheetId";
        String range = "Sheet1!A1:C2";

        String path = "/api/v1/sheet/{spreadSheetId}";
        when(spreadSheetsService.getData(spreadSheetsId, range)).thenThrow(new IOException("Test IO Exception"));
        given()
                .pathParam("spreadSheetId", spreadSheetsId)
                .queryParam("range", range)
                .when().get(path)
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(is("{\"errorMessage\":\"Error accessing Google Sheets\"}"));
    }

    @Test
    public void testGetSheetData_ThrowsInternalServerError_OnGeneralSecurityException() throws IOException, GeneralSecurityException {
        String spreadSheetsId = "testSpreadsheetId";
        String range = "Sheet1!A1:C2";

        String path = "/api/v1/sheet/{spreadSheetId}";
        when(spreadSheetsService.getData(spreadSheetsId, range)).thenThrow(new GeneralSecurityException("Test Security Exception"));
        given()
                .pathParam("spreadSheetId", spreadSheetsId)
                .queryParam("range", range)
                .when().get(path)
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(is("{\"errorMessage\":\"Security Error\"}"));
    }
}
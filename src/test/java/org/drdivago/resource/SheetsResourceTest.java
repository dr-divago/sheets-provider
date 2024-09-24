package org.drdivago.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
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
    void testHelloEndpoint() throws GeneralSecurityException, IOException {
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
                .body(is("[[1, John, Doe], [2, Jane, Smith]]"));
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
                .statusCode(204)
                .body(is("No data found."));
    }
}
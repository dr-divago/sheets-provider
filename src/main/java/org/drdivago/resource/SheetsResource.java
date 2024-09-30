package org.drdivago.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.drdivago.model.Exercise;
import org.drdivago.service.JsonConverterService;
import org.drdivago.service.SpreadSheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Path("/api/v1")
public class SheetsResource {

    @Inject
    SpreadSheetsService spreadSheetsService;

    @Inject
    JsonConverterService jsonConverterService;

    @GET
    @Path("/sheet/{spreadSheetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("spreadSheetId") String spreadId, @QueryParam("range")String range) {
        try {
            List<List<String>> data = spreadSheetsService.getData(spreadId, range);
            if (data.isEmpty()) {
                return Response.noContent().build();
            }
            List<Exercise> exercises =  jsonConverterService.parse(data);
            if (exercises.isEmpty()) {
                return Response.noContent().build();
            }

            return Response.ok(exercises).build();

        } catch (IOException e) {
            throw new WebApplicationException("Error accessing Google Sheets", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (GeneralSecurityException e) {
            throw new WebApplicationException("Security Error", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
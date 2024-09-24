package org.drdivago.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.drdivago.service.SpreadSheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Path("/api/v1")
public class SheetsResource {

    @Inject
    SpreadSheetsService spreadSheetsService;

    @GET
    @Path("/sheet/{spreadSheetId}")
    @Produces(MediaType.TEXT_PLAIN)
    public List<List<String>> getData(@PathParam("spreadSheetId") String spreadId, @QueryParam("range")String range) {
        try {
            return spreadSheetsService.getData(spreadId, range);
        } catch (IOException e) {
            throw new WebApplicationException("Error accessing Google Sheets", Response.Status.INTERNAL_SERVER_ERROR);
        } catch (GeneralSecurityException e) {
            throw new WebApplicationException("Security Error", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
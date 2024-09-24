package org.drdivago.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GoogleSheetsExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(new ErrorMessage(e.getMessage()))
                .build();
    }
}

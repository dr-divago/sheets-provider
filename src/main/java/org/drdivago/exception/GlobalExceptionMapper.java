package org.drdivago.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage("An unexpected error occured"))
                .build();
    }
}

package com.victor.stock.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.WebApplicationException;

@Provider
public class InvalidPathParamMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof jakarta.ws.rs.BadRequestException ||
                exception instanceof NumberFormatException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid or missing path parameter. Please provide a valid numeric ID.")
                    .build();
        }

        if (exception instanceof WebApplicationException) {
            return Response.status(((WebApplicationException) exception).getResponse().getStatus())
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Unexpected error: " + exception.getMessage())
                .build();
    }
}
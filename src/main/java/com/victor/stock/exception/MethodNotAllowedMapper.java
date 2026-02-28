package com.victor.stock.exception;

import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MethodNotAllowedMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("HTTP method not allowed for this endpoint.")
                .build();
    }
}
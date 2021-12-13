package com.pas.app.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        Logger.getLogger(getClass().getName()).warning(e.getMessage());
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
    }
}

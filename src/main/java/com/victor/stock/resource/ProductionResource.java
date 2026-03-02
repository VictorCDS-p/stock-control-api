package com.victor.stock.resource;

import com.victor.stock.dto.SimulationResponseDTO;
import com.victor.stock.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/production")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductionResource {

    @Inject
    ProductService productService;

    @GET
    @Path("/{id}/simulation")
    public Response simulate(@PathParam("id") Long id) {

        SimulationResponseDTO simulation = productService.simulateProduction(id);

        if (simulation == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }

        return Response.ok(simulation).build();
    }
}
package com.victor.stock.resource;

import com.victor.stock.dto.RawMaterialRequestDTO;
import com.victor.stock.dto.RawMaterialResponseDTO;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.service.RawMaterialService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/raw-materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    @Inject
    RawMaterialService service;

    @GET
    public Response list() {
        List<RawMaterialResponseDTO> dtos = service.listAll()
                .stream()
                .map(RawMaterialResponseDTO::new)
                .collect(Collectors.toList());
        return Response.ok()
                .entity(dtos)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        RawMaterial rm = service.findById(id);
        if (rm == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }
        return Response.ok(new RawMaterialResponseDTO(rm)).build();
    }

    @POST
    public Response create(RawMaterialRequestDTO dto) {
        RawMaterial rm = new RawMaterial();
        rm.code = dto.code;
        rm.name = dto.name;
        rm.stockQuantity = dto.stockQuantity;
        service.create(rm);
        return Response.status(Response.Status.CREATED)
                .entity(new RawMaterialResponseDTO(rm))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, RawMaterialRequestDTO dto) {
        RawMaterial existing = service.findById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }
        existing.code = dto.code;
        existing.name = dto.name;
        existing.stockQuantity = dto.stockQuantity;
        RawMaterial updated = service.update(id, existing);

        return Response.ok(new RawMaterialResponseDTO(updated))
                .entity("Raw material updated successfully")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }
        return Response.ok("Raw material with ID " + id + " deleted successfully").build();
    }
}
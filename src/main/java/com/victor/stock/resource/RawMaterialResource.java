package com.victor.stock.resource;

import com.victor.stock.dto.RawMaterialRequestDTO;
import com.victor.stock.dto.RawMaterialResponseDTO;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.service.RawMaterialService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
    RawMaterialService rawMaterialService;

    @GET
    public Response list() {
        List<RawMaterialResponseDTO> materials = rawMaterialService.listAll()
                .stream()
                .map(RawMaterialResponseDTO::new)
                .collect(Collectors.toList());

        return Response.ok(materials).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        RawMaterial material = rawMaterialService.findById(id);

        if (material == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }

        return Response.ok(new RawMaterialResponseDTO(material)).build();
    }

    @POST
    public Response create(@Valid RawMaterialRequestDTO dto) {
        RawMaterial material = rawMaterialService.create(dto);

        return Response.status(Response.Status.CREATED)
                .entity(new RawMaterialResponseDTO(material))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
                           @Valid RawMaterialRequestDTO dto) {

        RawMaterial material = rawMaterialService.update(id, dto);

        if (material == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }

        return Response.ok(new RawMaterialResponseDTO(material)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = rawMaterialService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + id + " not found")
                    .build();
        }

        return Response.ok("Raw material with ID " + id + " deleted successfully").build();    }
}
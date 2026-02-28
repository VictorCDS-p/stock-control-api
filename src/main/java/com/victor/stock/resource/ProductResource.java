package com.victor.stock.resource;

import com.victor.stock.dto.*;
import com.victor.stock.entity.Product;
import com.victor.stock.entity.ProductMaterial;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.service.ProductService;
import com.victor.stock.service.RawMaterialService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @Inject
    RawMaterialService rawMaterialService;

    @GET
    public Response list() {
        List<ProductDTO> products = productService.listAll()
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Product product = productService.findByIdWithMaterials(id);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }

        return Response.ok(new ProductDTO(product)).build();
    }

    @POST
    public Response create(@Valid ProductRequestDTO dto) {
        Product product = productService.create(dto);

        return Response.status(Response.Status.CREATED)
                .entity(new ProductDTO(product))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProductRequestDTO dto) {
        Product product = productService.update(id, dto);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }

        return Response.ok(new ProductDTO(product)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = productService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }

        return Response.ok("Product with ID " + id + " deleted successfully").build();    }

    @POST
    @Path("/{id}/add-material")
    public Response addMaterial(@PathParam("id") Long id,
                                @Valid ProductMaterialRequestDTO dto) {

        RawMaterial rawMaterial = rawMaterialService.findById(dto.rawMaterialId);

        if (rawMaterial == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Raw material with ID " + dto.rawMaterialId + " not found")
                    .build();
        }

        ProductMaterial pm = productService.addMaterial(id, rawMaterial, dto.requiredQuantity);

        if (pm == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }

        return Response.status(Response.Status.CREATED)
                .entity(new ProductMaterialDTO(pm))
                .build();
    }
}
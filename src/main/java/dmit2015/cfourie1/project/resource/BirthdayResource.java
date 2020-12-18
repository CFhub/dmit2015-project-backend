package dmit2015.cfourie1.project.resource;

import dmit2015.cfourie1.project.model.Birthday;
import dmit2015.cfourie1.project.service.BirthdayService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

/**
 * Get all Birthday
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday

 * Get one Birthday with id 3
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday/3

 * Post a Birthday
 curl -i -X POST http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday \
 -d '{"personName":"Adam","birthdayDate":"2021-05-21","bought":false}' \
 -H 'Content-Type:application/json'

 curl -i -X POST http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday \
 -d '{"personName":"Lily","birthdayDate":"2021-10-10","price":300, "bought":true}' \
 -H 'Content-Type:application/json'

 * Put a Birthday update
 curl -i -X PUT http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday/7 \
   -d '{"id":7, "personName":"Dan","birthdayDate":"2021-08-21","gift":"glasses", "bought":true}' \
   -H 'Content-Type:application/json'

 * Delete a Birthday
 curl -i -X DELETE http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday/4

 **/

@Path("Birthday")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BirthdayResource {

    @Inject
    private BirthdayService currentBirthdayService;

    @Context
    private UriInfo currentUriInfo;

    @POST
    public Response createBirthday(@Valid Birthday newBirthday) {
        if (newBirthday == null) {
            throw new BadRequestException();
        }
        currentBirthdayService.createBirthday(newBirthday);
        URI locationUri = currentUriInfo
                .getAbsolutePathBuilder()
                .path(newBirthday.getId().toString())
                .build();
        return Response.created(locationUri).build();
    }

    @GET
    @Path("{id}")
    public Response readOneBirthday(@PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        Optional<Birthday> optionalBirthday = currentBirthdayService.readOne(id);
        if(optionalBirthday.isEmpty()) {
            throw new NotFoundException();
        }
        Birthday existingBirthday = optionalBirthday.get();
        return Response.ok(existingBirthday).build();
    }

    @GET
    public Response readAll() {
        return Response.ok(currentBirthdayService.readAll()).build();
    }

    @PUT
    @Path("{id}")
    public Response updateBirthday(@PathParam("id") Long id, Birthday updatedBirthday) {
        if (id == null || !id.equals(updatedBirthday.getId())) {
            throw new BadRequestException();
        }
        Optional<Birthday> optionalBirthday = currentBirthdayService.readOne(id);
        if (optionalBirthday.isEmpty()) {
            throw new NotFoundException();
        }
        currentBirthdayService.updateBirthday(updatedBirthday);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBirthday(@PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        Optional<Birthday> optionalBirthday = currentBirthdayService.readOne(id);
        if (optionalBirthday.isEmpty()) {
            throw new NotFoundException();
        }
        currentBirthdayService.deleteBirthday(id);
        return Response.noContent().build();
    }


}

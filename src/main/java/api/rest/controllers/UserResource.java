package api.rest.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.commons.Utils;
import api.persistence.entities.UserEntity;
import api.persistence.repositories.UserRepository;
import api.rest.models.request.CreateUserRequest;
import api.rest.models.responses.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserRepository userRepository;
    private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator) {
        this.userRepository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        var violations = validator.validate(userRequest);
        if (!violations.isEmpty()) {
            Response responseError = ResponseError.createFromValidation(violations).withStatusCode(422);

            return responseError;
        }

        UserEntity user = Utils.mapObject(userRequest, UserEntity.class);

        userRepository.persist(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    public Response listAllUsers() {
        PanacheQuery<UserEntity> userQuery = userRepository.findAll();

        return Response.ok(userQuery.list()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
        UserEntity user = userRepository.findById(id);

        if (user != null) {
            Utils.mapObject(userData, user);

            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        UserEntity user = userRepository.findById(id);

        if (user != null) {
            userRepository.delete(user);
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}

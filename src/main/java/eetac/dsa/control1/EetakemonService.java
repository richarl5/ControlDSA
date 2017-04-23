package eetac.dsa.control1;

import eetac.dsa.control1.entity.Uobject;
import eetac.dsa.control1.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by Home on 19/04/2017.
 */
@Path("")
public class EetakemonService {

    //EetakemonManagerImpl e = EetakemonManagerImpl.getInstance();
    EetakemonManagerImplDB e = EetakemonManagerImplDB.getInstance();

    @GET
    @Path("/allusers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() throws Exception {
        List<User> list = e.listUsers();
        return list;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser (User u) {
        boolean res = e.addUser(u);
        if(res) return Response.status(201).entity("User added successfully: " + u).build();
        else return Response.status(409).entity("User already exists!").build();
    }

    @POST
    @Path("/user/{id}/mod")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modUser (@PathParam("id") Integer id, User u) {
        boolean res = e.modUser(id,u);
        if(res) return Response.status(201).entity("User updated successfully: " + u).build();
        else return Response.status(409).entity("User does not exists!").build();
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User infoUser (@PathParam("id") Integer id) throws Exception {
        User u=e.readUser(id);
        return u;
    }

    @GET
    @Path("/user/{id}/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uobject> objectsUser (@PathParam("id") Integer id) throws Exception {
        List<Uobject> objects = e.readObject(e.readUser(id));
        return objects;
    }

    @POST
    @Path("/user/{id}/addobject")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addObject (@PathParam("id") Integer id, Uobject o) {
        boolean res = e.addObject(e.readUser(id),o);
        if(res) return Response.status(201).entity(o + " added successfully to user: " + e.readUser(id).getName()).build();
        else return Response.status(409).entity("User does not exists!").build();
    }

}

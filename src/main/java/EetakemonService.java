import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by Home on 19/04/2017.
 */
@Path("")
public class EetakemonService {

    EetakemonManagerImpl e = EetakemonManagerImpl.getInstance();

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEetakemon(User u) {
            boolean res = e.addUser(u.getId(),u);
            if(res) return Response.status(201).entity("User added successfully: " + e).build();
            else return Response.status(409).entity("User already exists!").build();
    }


    @GET
    @Path("/allusers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() throws Exception {
        List<User> list = e.listUser();
        return list;
    }


    @GET
    @Path("/user/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User searchByName (@PathParam("name") String name) throws Exception {
        Object o=e.readUser(name);
        User u = (User)o;
        return u;
    }
}

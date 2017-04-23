package eetac.dsa.control1;
import eetac.dsa.control1.entity.User;
import eetac.dsa.control1.entity.Uobject;
import java.util.List;

/**
 * Created by Home on 19/04/2017.
 */
public interface EetakemonManager {
    List<User> listUsers() throws Exception;
    boolean addUser(User u);
    boolean modUser(Integer id, User u);
    User readUser(Integer id);
    List<Uobject> readObject(User u);
    boolean addObject(User u, Uobject o);
}

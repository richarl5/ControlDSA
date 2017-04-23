package eetac.dsa.control1;

import eetac.dsa.control1.entity.Uobject;
import eetac.dsa.control1.entity.User;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 19/04/2017.
 */
public class EetakemonManagerImplDB implements EetakemonManager {
    private static EetakemonManagerImplDB ourInstance = new EetakemonManagerImplDB();

    public static EetakemonManagerImplDB getInstance() {
        return ourInstance;
    }

    private static Logger log = Logger.getLogger(EetakemonManagerImplDB.class);

    private EetakemonManagerImplDB() {
    }


    @Override
    public List<User> listUsers() throws Exception {
        User u = new User();
        log.info("Sorting list of all users");
        List<User> lista = u.findAll();
        Collections.sort(lista);
        log.info("Sorted list of all users: " + lista);
        return lista;
    }

    @Override
    public boolean addUser (User u) {
        log.info("Adding new: " + u);
        /*List<Uobject> objects = new ArrayList<>();
        u.setObjectlist(objects);*/
        try {
            u.insert();
            log.info(u);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modUser(Integer id, User u) {
        log.info("Updating: " + u);
        try {
            u.update();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User not found!");
            return false;
        }
    }

    @Override
    public User readUser(Integer id) {
        User u = new User();
        log.info("Reading user with id: " + id);
        try {
            u.setId(id);
            Object object = u.select();
            log.info(u);
            return (User) object;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User not found!");
            return null;
        }

    }

    @Override
    public List<Uobject> readObject(User u) {
        Uobject uo = new Uobject();
        log.info("Reading objects from: " + u);
        List<Uobject> objects;
        try {
            objects = uo.selectObjects(u.getId());
            log.info(objects);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addObject(User u, Uobject o) {
        log.info("Adding: " + o + "to: " + u);
        try {
            o.addobject(u.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User not found!");
            return false;
        }
    }

}

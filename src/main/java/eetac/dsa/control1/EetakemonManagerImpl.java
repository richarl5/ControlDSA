package eetac.dsa.control1;

import eetac.dsa.control1.entity.Uobject;
import eetac.dsa.control1.entity.User;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by Home on 19/04/2017.
 */
public class EetakemonManagerImpl implements EetakemonManager {
    private static EetakemonManagerImpl ourInstance = new EetakemonManagerImpl();

    public static EetakemonManagerImpl getInstance() {
        return ourInstance;
    }

    private HashMap<Integer,User> listUsers;
    private Integer id=1;
    private static Logger log = Logger.getLogger(EetakemonManagerImpl.class);


    private EetakemonManagerImpl() {
        this.listUsers = new HashMap<Integer, User>();
    }


    @Override
    public List<User> listUsers() {
        log.info("Sorting list of all users");
        List<User> lista = new ArrayList<User>();
        for(User u: listUsers.values())
            lista.add(u);
        Collections.sort(lista);
        for (User u: lista) {
            System.out.println(u.toString());
        }
        log.info(lista);
        return lista;
    }

    @Override
    public boolean addUser (User u) {
        log.info("Adding new: " + u);
        u.setId(id);
        List<Uobject> objects = new ArrayList<>();
        //u.setObjectlist(objects);
        if(listUsers.putIfAbsent(id,u)==null){
            id++;
            log.info(u);
            return true;
        }
        log.info(u);
        return false;
    }

    @Override
    public boolean modUser(Integer id, User u) {
        log.info("Updating: " + u);
        if(listUsers.get(id)!=null) {
            u.setId(id);
            //if(u.getObjectlist()== null) u.setObjectlist(new ArrayList<Uobject>());
            listUsers.put(id,u);
            log.info(u);
            return true;
        }
        log.error("User not found!");
        return false;
    }

    @Override
    public User readUser(Integer id) {
        log.info("Reading user with id: " + id);
        if (listUsers.containsKey(id)) {
            User user = listUsers.get(id);
            log.info(user);
            return user;
        }
        log.error("User not found!");
        return null;
    }

    @Override
    public List<Uobject> readObject(User u) {
        log.info("Reading objects from: " + u);
        if (listUsers.containsKey(u.getId())) {
            //List<Uobject> objects = listUsers.get(u.getId()).getObjectlist();
            //log.info(u.getObjectlist());
            //return objects;
        }
        log.error("User not found!");
        return null;
    }

    @Override
    public boolean addObject(User u, Uobject o) {
        log.info("Adding: " + o + "to: " + u);
        if(this.readUser(u.getId()) != null) {
            //List<Uobject> objects = u.getObjectlist();//objects.add(o);
            // u.setObjectlist(objects);
            this.listUsers.put(u.getId(), u);
            log.info(u);
            return true;
        }
        log.error("User not found!");
        return false;
    }

}

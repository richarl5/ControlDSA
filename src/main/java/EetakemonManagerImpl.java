import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Home on 19/04/2017.
 */
public class EetakemonManagerImpl<E> implements EetakemonManager<E> {
    private static EetakemonManagerImpl ourInstance = new EetakemonManagerImpl();

    public static EetakemonManagerImpl getInstance() {
        return ourInstance;
    }

    private HashMap<E,E> listUsers;
    private ArrayList<Object> objects;

    private EetakemonManagerImpl() {
        this.listUsers = new HashMap<E,E>();
        this.objects = new ArrayList<Object>();
    }


    public List<E> listUser() {
        List<E> list = new ArrayList<E>();
        for (E e:listUsers.values())
            list.add(e);
        return list;
    }

    public boolean addUser(E i,E u) {
        if(listUsers.putIfAbsent(i,u)==null) return true;
        return false;
    }

    public void modUser(E u) {

    }

    public E readUser(E u) {
        if (listUsers.containsKey(u)) {
            E e = listUsers.get(u);
            return e;
        }
        return null;
    }

    public void readObject(E u) {

    }

    public void addObject(E u) {

    }
}

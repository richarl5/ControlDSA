import java.util.List;

/**
 * Created by Home on 19/04/2017.
 */
public interface EetakemonManager <E>{
    List<E> listUser();
    boolean addUser(E i,E e);
    void modUser(E u);
    E readUser(E u);
    void readObject(E u);
    void addObject(E u);
}

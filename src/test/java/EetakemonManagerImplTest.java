/**
 * Created by Home on 19/04/2017.
 */
import eetac.dsa.control1.EetakemonManagerImpl;
import eetac.dsa.control1.entity.Uobject;
import eetac.dsa.control1.entity.User;

import static org.junit.Assert.assertEquals;


public class EetakemonManagerImplTest {
    private EetakemonManagerImpl myManager;
    private User myUser;

    @org.junit.Before
    public void setUp() throws Exception {
        myManager = EetakemonManagerImpl.getInstance();
        //myUser = new User(null,"Maria",null);
        myManager.addUser(myUser);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        this.myManager = null;
    }

    @org.junit.Test
    public void addUser() throws Exception {
        //this.myManager.addUser(new User(null,"Paco", null));
        assertEquals(myManager.listUsers().size(), 2);
    }

    @org.junit.Test
    public void addobject() throws Exception {
        Uobject obj = new Uobject("Coche", "Descapotable");
        this.myManager.addObject(myUser, obj);
        assertEquals(myManager.readObject(myUser).size(), 1);
    }
}
/**
 * Created by Home on 28/02/2017.
 */
public class User
{
    private Integer id;
    private String name;
    private String address;

    public User()
    {
    }
    public User(Integer id, String name, String address){
        this.id = id;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Integer getId()
    {
        return id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(String pass)
    {
        this.id = id;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPrimaryKey() {
        return "id";
    }

    public String getPKMethod() {
        return "getId";
    }
}

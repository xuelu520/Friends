package makyu.friends.beans;

/**
 * Created by -(^_^)- on 2016/4/28.
 */
public class Contact {
    private Long id;
    private String name;
    private String phone;

    public Contact() {}

    public  Contact(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID:" + this.getId() +
                "   name:" + this.getName() +
                "  phone:" + this.getPhone();
    }
}

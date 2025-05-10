package Modul;

public class Contact {// в
    private String name;
    private String phone;
    private String email;
    private String group;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return name + "|" + phone + "|" + email + "|Группа " + group ;
    }

    public Contact(String name, String phone, String email, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Contact cont = (Contact) obj;
        return (getName() != null ? getName().equals(cont.getName()) : cont.getName() == null) &&
                (getPhone() != null ? getPhone().equals(cont.getPhone()) : cont.getPhone() == null);

    }
    @Override
public int hashCode () {
int result =(getName() != null ? getName().hashCode() :0);
result = 31 * result+(getPhone() != null ? getPhone().hashCode():0);
return  result;
    }
}

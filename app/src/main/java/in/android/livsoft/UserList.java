package in.android.livsoft;

public class UserList {
    private String name, mobile, gender;
    public UserList(){

    }

    public UserList(String name, String mobile, String gender) {
        this.name = name;
        this.mobile = mobile;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package sample;

public class Admin {

    private String userAdmin;
    private String userPass;

    public Admin(String userAdmin, String userPass) {
        this.userAdmin = userAdmin;
        this.userPass = userPass;
    }

    public String getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(String userAdmin) {
        this.userAdmin = userAdmin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}

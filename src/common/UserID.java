package common;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class UserID {

    private String username, password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean verifyLogin(String username, String password){

        if(username.toUpperCase().equals(this.username.toUpperCase())==true && password.equals(this.password.toUpperCase())==true)
            return true;
        return false;

    }
}
package buzzword;

/**
 * Created by Feazan on 11/26/2016.
 */
public class UserProfile {
    private String userName;
    private String hashedPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}

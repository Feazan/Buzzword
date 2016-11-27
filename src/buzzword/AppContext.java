package buzzword;

/**
 * Created by Feazan on 11/26/2016.
 */
public class AppContext {
    static AppContext singleton;
    ProfileManager profileManager;
    UserProfile currentUser;

    public UserProfile getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    private AppContext() {
        this.profileManager = new ProfileManager();
    }

    public static AppContext getSingleton() {
        if (singleton == null)
            singleton = new AppContext();
        return singleton;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void resetContext()
    {
        this.setCurrentUser(null);
    }



}


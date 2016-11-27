package buzzword.Model;

import javafx.scene.Scene;

/**
 * Created by Feazan on 11/26/2016.
 */
public class AppContext {
    static AppContext singleton;
    ProfileManager profileManager;
    UserProfile currentUser;
    GameState gameState;
    Scene currentScene;

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public UserProfile getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    public GameState getGameState() {
        return gameState;
    }

    private AppContext() {
        this.profileManager = new ProfileManager();
        this.gameState = new GameState();

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


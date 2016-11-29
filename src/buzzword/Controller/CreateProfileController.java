package buzzword.Controller;

import buzzword.Model.AppContext;
import buzzword.Model.ProfileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Feazan on 11/26/2016.
 */
public class CreateProfileController extends Controller {
    @FXML private TextField createProfileUsername;
    @FXML private TextField createProfilePassword;
    @FXML private TextField createProfileConfirmPassword;
    @FXML private TextField errorDisplay = new TextField();

    @FXML
    public void submitCreateNewProfile(ActionEvent event) throws IOException
    {
        ProfileManager profileManager = AppContext.getSingleton().getProfileManager();

        String userName = this.createProfileUsername.getText().trim();
        String password = this.createProfilePassword.getText().trim();
        String confirmPassword = this.createProfileConfirmPassword.getText().trim();

        if(userName == null || userName.isEmpty())
        {
            //TODO set visibility on incorrect username
            errorDisplay.setVisible(true);
            System.out.println("make sure username isnt empty");
            errorDisplay.setText("USERNAME EMPTY");
            return;
        }
        if(password == null || password.isEmpty())
        {
            errorDisplay.setVisible(true);
            //TODO set visibility on incorrect username
            System.out.println("make sure password isnt empty");
            errorDisplay.setText("PASSWORD EMPTY");
            return;
        }
        if(confirmPassword == null || confirmPassword.isEmpty())
        {
            //TODO set visibility on incorrect username
            errorDisplay.setVisible(true);
            errorDisplay.setText("PASSWORD EMPTY");
            System.out.println("make sure confirm password isnt empty");
            return;
        }
        if (!password.equals(confirmPassword))
        {
            errorDisplay.setVisible(true);
            errorDisplay.setText("PASSWORD MUST MATCH");
            //TODO set visibility on password match
            System.out.println("confirm password");
            return;
        }
        if (profileManager.checkIfProfileExists(userName)){
            //TODO set visibility on already exists
            errorDisplay.setVisible(true);
            errorDisplay.setText("PROFILE ALREADY EXISTS");
            System.out.println("user profile already exists");
            return;
        }
        profileManager.createNewProfile(userName,password);
        System.out.println("Login Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("../login.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @Override
    public void initialize()
    {
        errorDisplay.setVisible(false);
    }

}

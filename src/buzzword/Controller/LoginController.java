package buzzword.Controller;

import buzzword.Model.AppContext;
import buzzword.Model.ProfileManager;
import buzzword.Model.UserProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Feazan on 11/26/2016.
 */
public class LoginController extends Controller
{
    @FXML private TextField loginProfileUsername;
    @FXML private TextField loginProfilePassword;

    @FXML
    public void handleUserLogin (ActionEvent event) throws  IOException
    {
        ProfileManager profileManager = AppContext.getSingleton().getProfileManager();
        String userName = this.loginProfileUsername.getText().trim();
        String password = this.loginProfilePassword.getText().trim();

        if(profileManager.verifyUserLogin(userName, password))
        {
            System.out.println("Login Successful");
            UserProfile currentUser = profileManager.getUserProfile(userName);
            AppContext.getSingleton().setCurrentUser(currentUser);
            goToScene("../modeSelector.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
        }
        else
        {
            System.out.println("Validation Failed");
            //TODO set visibility for incorrect user
        }


    }


}

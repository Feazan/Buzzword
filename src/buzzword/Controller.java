package buzzword;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/*
@author Feazan Yaseen
*/

public class Controller {
    @FXML private TextField settingUser;
    @FXML public ChoiceBox modeChoices = new ChoiceBox();
    ObservableList<String> gameModeList = FXCollections.observableArrayList("Animals", "Colors", "Sports Teams");

    protected void goToScene(String sceneToLoad, Stage currentStage) throws IOException
    {
        Parent gameScreen = FXMLLoader.load(getClass().getResource(sceneToLoad));
        Scene mainScene = new Scene(gameScreen);
        currentStage.setScene(mainScene);
        currentStage.setFullScreen(true);
        currentStage.show();
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException
    {
        //TODO save current game status
        AppContext.getSingleton().resetContext();
        goToScene("sample.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    public void handleGoToLoginScreen (ActionEvent event) throws IOException
    {
        System.out.println("Login Pressed");
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        goToScene("login.fxml", currentStage);
    }

    @FXML
    public void createProfile (ActionEvent event) throws IOException
    {
        System.out.println("Create Profile Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("create.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void loginAfterCreate (ActionEvent event) throws IOException
    {
        System.out.println("Logging in after Created Profile");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void returnToHome (ActionEvent event) throws IOException
    {
        System.out.println("Returning to Home");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void initialize()
    {
        modeChoices.setValue("English Dictionary");
        modeChoices.setItems(gameModeList);
    }

    @FXML
    public void levelSelection (ActionEvent event) throws IOException
    {
        System.out.println("Playing Game...");

        // THIS IS WHAT MODE THE USER WANTS
        System.out.println(modeChoices.getValue());

        if(modeChoices.getValue().equals("Animals"))
        {

        }
        else if(modeChoices.getValue().equals("Colors"))
        {
            Parent gameScreen = FXMLLoader.load(getClass().getResource("famousPeopleLevel.fxml"));
            Scene mainScene = new Scene(gameScreen);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.setScene(mainScene);
            loginStage.show();
        }
        else
        {
            Parent gameScreen = FXMLLoader.load(getClass().getResource("levelSelector.fxml"));
            Scene mainScene = new Scene(gameScreen);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.setScene(mainScene);
            loginStage.show();
        }
    }

    public void handlePlayButton (ActionEvent event)
    {
        Button btn = (Button) event.getSource();
        if(btn.getText().equals("Play"))
        {
            btn.setText("Pause");
        }
        else
        {
            btn.setText("Play");
        }
    }

    public void play(ActionEvent event) throws IOException
    {
        System.out.println("Playing Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("playing.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }


    public void closeProgram ()
    {
        System.exit(0);
    }
}
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

    ObservableList<String> gameModeList = FXCollections.observableArrayList("English Dictionary", "Places", "Famous People");

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
    public void login (ActionEvent event) throws IOException
    {
        System.out.println("Login Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("login.fxml"));
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

        if(modeChoices.getValue().equals("Places"))
        {
            Parent gameScreen = FXMLLoader.load(getClass().getResource("placesLevel.fxml"));
            Scene mainScene = new Scene(gameScreen);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.setScene(mainScene);
            loginStage.show();
        }
        else if(modeChoices.getValue().equals("Famous People"))
        {
            Parent gameScreen = FXMLLoader.load(getClass().getResource("famousPeopleLevel.fxml"));
            Scene mainScene = new Scene(gameScreen);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.setScene(mainScene);
            loginStage.show();
        }
        else
        {
            Parent gameScreen = FXMLLoader.load(getClass().getResource("englishDictionaryLevel.fxml"));
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
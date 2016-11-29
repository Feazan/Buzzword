package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*
@author Feazan Yaseen
*/

public class Controller {
    @FXML private TextField settingUser;
    @FXML public ChoiceBox modeChoices = new ChoiceBox();
    ObservableList<String> gameModeList = FXCollections.observableArrayList("Animals", "English Dictionary", "Sports Teams");

    protected void goToScene(String sceneToLoad, Stage currentStage) throws IOException
    {
        Parent gameScreen = FXMLLoader.load(getClass().getResource(sceneToLoad));
        Scene mainScene = new Scene(gameScreen);
        AppContext.getSingleton().setCurrentScene(mainScene);
        currentStage.setScene(mainScene);
        currentStage.setFullScreen(true);
        currentStage.show();
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException
    {
        //TODO save current game status
        AppContext.getSingleton().resetContext();
        goToScene("../sample.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    public void handleGoToLoginScreen (ActionEvent event) throws IOException
    {
        System.out.println("Login Pressed");
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        goToScene("../login.fxml", currentStage);
    }

    @FXML
    public void createProfile (ActionEvent event) throws IOException
    {
        System.out.println("Create Profile Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("../create.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void loginAfterCreate (ActionEvent event) throws IOException
    {
        System.out.println("Logging in after Created Profile");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("modeSelector.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void returnToHome (ActionEvent event) throws IOException
    {
        System.out.println("Returning to Home");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }

    @FXML
    public void initialize()
    {
        modeChoices.setValue("Animals");
        modeChoices.setItems(gameModeList);
    }

    public void play(ActionEvent event) throws IOException
    {
        System.out.println("Playing Pressed");
        Parent gameScreen = FXMLLoader.load(getClass().getResource("../playing.fxml"));
        Scene mainScene = new Scene(gameScreen);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.setScene(mainScene);
        loginStage.show();
    }


    public void closeProgram (ActionEvent event)
    {
        Button yes = new Button();
        Button no = new Button();
        Button btn = (Button)event.getSource();
        btn.setOnAction(
                event1 -> {
                    Stage stageTheLabelBelongs = (Stage) btn.getScene().getWindow();
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(stageTheLabelBelongs);
                    VBox dialogVbox = new VBox(10);
                    dialogVbox.getChildren().add(yes);
                    dialogVbox.getChildren().add(no);
                    dialogVbox.getChildren().add(new Text("Are you sure you want to exit?"));
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    yes.setText("YES");
                    no.setText("NO");
                    yes.setOnAction(even-> Platform.exit());
                    no.setOnAction(even-> dialog.close());
                });
    }
}
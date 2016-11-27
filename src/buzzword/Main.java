package buzzword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.security.krb5.internal.APOptions;

/*
@author Feazan Yaseen
*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Buzzword");
        primaryStage.setScene(new Scene(root, 2000, 650));
        primaryStage.setFullScreen(true);
        primaryStage.show();

        AppContext.getSingleton();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

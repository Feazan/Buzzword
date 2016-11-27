package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feazan on 11/27/2016.
 */
public class GameBoardController extends Controller {
    Label theLabel;
    List<Label> myList = new ArrayList<Label>();
    @FXML BorderPane theBorderPane;


    public void changeBackground(MouseEvent event)
    {
        theLabel = (Label)event.getSource();
        Label letter =  theLabel;
        myList.add(letter);

        System.out.println(myList.toString());

    }

    @Override
    public void initialize()
    {
       // Label temp = (Label) AppContext.getSingleton().getCurrentScene().lookup("#0");
        //temp.setText("TESTESTESETS");
    }
}

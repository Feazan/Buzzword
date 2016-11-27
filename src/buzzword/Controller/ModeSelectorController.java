package buzzword.Controller;

import buzzword.Model.AppContext;
import buzzword.Model.GameModeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Feazan on 11/26/2016.
 */
public class ModeSelectorController extends Controller {
    @FXML
    public void levelSelection (ActionEvent event) throws IOException
    {
        System.out.println("Playing Game...");

        // THIS IS WHAT MODE THE USER WANTS
        System.out.println(modeChoices.getValue());

        if(modeChoices.getValue().equals("Animals"))
        {
            GameModeEnum gameModeEnum = GameModeEnum.ANIMAL;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);
        }
        else if(modeChoices.getValue().equals("English Dictionary"))
        {
            GameModeEnum gameModeEnum = GameModeEnum.DICTIONARY;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);
        }
        else
        {
            GameModeEnum gameModeEnum = GameModeEnum.SPORTSTEAMS;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);
        }
        goToScene("../levelSelector.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}

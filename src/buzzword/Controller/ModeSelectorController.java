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

        // Clear out the dictionary
        AppContext.getSingleton().getGameState().getDictionary().clear();

        GameModeEnum gameModeEnum;
        if(modeChoices.getValue().equals("English"))
        {
            gameModeEnum = GameModeEnum.ENGLISH;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);

        }
        else if(modeChoices.getValue().equals("Spanish"))
        {
            gameModeEnum = GameModeEnum.SPANISH;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);
        }
        else
        {
            gameModeEnum = GameModeEnum.ITALIAN;
            AppContext.getSingleton().getGameState().setGameMode(gameModeEnum);
        }

        //TODO: Load dictionary for the selected game mode
        // parse the file and make all the words upper case
        // this is wheere im pulling the giant list
        // add them as words the dictionary

        goToScene("../levelSelector.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}

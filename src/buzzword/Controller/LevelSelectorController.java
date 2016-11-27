package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Feazan on 11/26/2016.
 */
public class LevelSelectorController extends Controller {
    @FXML private TextField modeLabel;

    @Override
    public void initialize()
    {
        modeLabel.setText(AppContext.getSingleton().getGameState().getGameMode().toString());
        System.out.println("initialize inside of level selector");

    }




}

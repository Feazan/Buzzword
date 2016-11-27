package buzzword.Model;

/**
 * Created by Feazan on 11/26/2016.
 */
public class GameState {
    private GameModeEnum gameMode;

    public void setGameMode(GameModeEnum gameMode)
    {
        this.gameMode = gameMode;
    }

    public GameModeEnum getGameMode() {
        return gameMode;
    }
}

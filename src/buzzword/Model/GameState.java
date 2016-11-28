package buzzword.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Feazan on 11/26/2016.
 */
public class GameState {
    private GameModeEnum gameMode;
    private List<String> dictionary = new ArrayList<>();
    private Set<String> allowedWords = new HashSet<>();

    public void setGameMode(GameModeEnum gameMode)
    {
        this.gameMode = gameMode;
    }

    public GameModeEnum getGameMode() {
        return gameMode;
    }

    public List<String> getDictionary() {
        return dictionary;
    }

    public Set<String> getAllowedWords() {
        return allowedWords;
    }
}

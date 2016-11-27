package buzzword.Model;

/**
 * Created by Feazan on 11/26/2016.
 */
public enum GameModeEnum {
    ANIMAL("Animals"),
    DICTIONARY("English Dictionary"),
    SPORTSTEAMS("Sports Teams");

    private String name;

    GameModeEnum(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

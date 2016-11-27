package buzzword;

/**
 * Created by Feazan on 11/26/2016.
 */
public enum GameModeEnum {
    ANIMAL("Animals"),
    COLORS("Colors"),
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

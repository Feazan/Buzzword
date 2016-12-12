package buzzword.Model;

/**
 * Created by Feazan on 11/26/2016.
 */
public enum GameModeEnum {
    ENGLISH("English"),
    SPANISH("Spanish"),
    ITALIAN("Italian");

    private String name;

    GameModeEnum(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

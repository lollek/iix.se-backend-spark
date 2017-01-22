package model;

import java.io.Serializable;

public class Game extends Model implements Serializable {
    @SuppressWarnings("WeakerAccess")
    public String name;

    @SuppressWarnings("WeakerAccess")
    public String link;

    @SuppressWarnings("WeakerAccess")
    public String image;

    public Game(String name, String link, String image) {
        this.name = name;
        this.link = link;
        this.image = image;
    }
}

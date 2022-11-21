package Class.Entity;

import java.util.ArrayList;

public class Developer {
    private String name;
    private ArrayList<Game> games;

    public Developer() {
        games = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}

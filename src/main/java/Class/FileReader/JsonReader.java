package Class.FileReader;

import Class.Entity.Developer;
import Class.Entity.Game;
import Interface.FileReader.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader implements Reader {
    @Override
    public Object read(String fileName) {
        JSONParser parser = new JSONParser();
        FileReader reader = null;
        JSONObject document = null;

        try {
            reader = new FileReader(fileName);
            document = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return getDevelopers(document);
    }

    private ArrayList<Developer> getDevelopers(JSONObject document) {
        ArrayList<Developer> developers = new ArrayList<>();

        JSONArray developersJson = (JSONArray) document.get("developers");
        for (Object developerJson : developersJson) {
            JSONObject developerData = (JSONObject) developerJson;
            JSONObject developerProperties = (JSONObject) developerData.get("developer");
            Developer developer = getDeveloper(developerProperties);
            fillDevelopersInGames(developer);
            developers.add(developer);
        }

        return developers;
    }

    private Developer getDeveloper(JSONObject developerProperties) {
        Developer developer = new Developer();

        developer.setName(developerProperties.get("name").toString());
        JSONArray gamesJson = (JSONArray) developerProperties.get("games");
        ArrayList<Game> games = getGames(gamesJson);
        developer.setGames(games);

        return developer;
    }

    private void fillDevelopersInGames(Developer developer) {
        ArrayList<Game> games = developer.getGames();
        for (Game game : games) {
            game.setDeveloper(developer.getName());
        }
    }

    private ArrayList<Game> getGames(JSONArray gamesJson) {
        ArrayList<Game> games = new ArrayList<>();

        for (Object gameJson : gamesJson) {
            JSONObject gameData = (JSONObject) gameJson;
            Game game = getGame(gameData);
            games.add(game);
        }
        return games;
    }

    private Game getGame(JSONObject gameData) {
        Game game = new Game();
        game.setName(gameData.get("name").toString());
        game.setPublisher(gameData.get("publisher").toString());
        game.setRelease_date(gameData.get("releaseDate").toString());
        JSONArray tagsJson = (JSONArray) gameData.get("tags");
        setTagsToGame(tagsJson, game);
        return game;
    }

    private void setTagsToGame(JSONArray tagsJson, Game game) {
        ArrayList<String> tags = new ArrayList<>();
        for (Object tag : tagsJson) {
            tags.add(tag.toString());
        }
        game.setTags(tags);
    }
}

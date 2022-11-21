package Class.FileConverter;

import Class.Entity.Developer;
import Class.Entity.Game;
import Class.FileExtension.FileExtension;
import Class.FileReader.XMLReader;
import Class.FileWriter.JsonWriter;
import Interface.FileConverter.FileConverter;

import java.util.ArrayList;

public class XMLToJson implements FileConverter {
    @Override
    public void convert(String inputFileName, String outputFileName) {
        if (!FileExtension.getExtension(outputFileName).equals("json")) {
            throw new IllegalArgumentException("Invalid file extension");
        }

        XMLReader reader = new XMLReader();
        ArrayList<Game> games = (ArrayList<Game>) reader.read("data/input/" + inputFileName);
        ArrayList<Developer> developers = convertGamesToDevelopers(games);

        JsonWriter writer = new JsonWriter();
        writer.write(developers, "data/output/" + outputFileName);
    }

    private ArrayList<Developer> convertGamesToDevelopers(ArrayList<Game> games) {
        ArrayList<Developer> developers = new ArrayList<>();

        for (Game game : games) {
            Developer developer = new Developer();
            developer.setName(game.getDeveloper());
            if (contains(developers, developer.getName())) continue;

            ArrayList<Game> developerGames = new ArrayList<>();
            for (Game gameLoop : games) {
                if (gameLoop.getDeveloper().equals(developer.getName())) {
                    developerGames.add(gameLoop);
                }
            }
            developer.setGames(developerGames);
            developers.add(developer);
        }

        return developers;
    }

    private boolean contains(ArrayList<Developer> developers, String developerName) {
        for (Developer developer : developers) {
            if (developer.getName().equals(developerName)) {
                return true;
            }
        }

        return false;
    }
}

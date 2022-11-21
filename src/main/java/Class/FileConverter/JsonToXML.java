package Class.FileConverter;

import Class.Entity.Developer;
import Class.Entity.Game;
import Class.FileExtension.FileExtension;
import Class.FileReader.JsonReader;
import Class.FileWriter.JsonWriter;
import Class.FileWriter.XMLWriter;
import Interface.FileConverter.FileConverter;

import java.util.ArrayList;

public class JsonToXML implements FileConverter {
    @Override
    public void convert(String inputFileName, String outputFileName) {
        if (!FileExtension.getExtension(outputFileName).equals("xml")) {
            throw new IllegalArgumentException("Invalid file extension");
        }

        JsonReader reader = new JsonReader();
        ArrayList<Developer> developers = (ArrayList<Developer>) reader.read("data/input/" + inputFileName);
        ArrayList<Game> games = convertDevelopersToGames(developers);

        XMLWriter writer = new XMLWriter();
        writer.write(games, "data/output/" + outputFileName);
    }

    private ArrayList<Game> convertDevelopersToGames(ArrayList<Developer> developers) {
        ArrayList<Game> games = new ArrayList<>();

        for (Developer developer : developers) {
            ArrayList<Game> developerGames = developer.getGames();
            games.addAll(developerGames);
        }

        return games;
    }
}

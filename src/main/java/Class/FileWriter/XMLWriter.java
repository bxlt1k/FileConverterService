package Class.FileWriter;

import Class.Entity.Game;
import Interface.FileWriter.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class XMLWriter implements Writer {
    @Override
    public void write(Object object, String fileName) {
        ArrayList<Game> games = (ArrayList<Game>) object;

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        try {
            writer = output.createXMLStreamWriter(new FileOutputStream(fileName), "UTF-8");
            writer.writeStartDocument("utf-8", "1.0");
            writer.writeStartElement("library");
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        for (int i = 0; i < games.size(); i++) {
            writeGame(writer, games.get(i), i);
        }

        try {
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeGame(XMLStreamWriter writer, Game game, int id) {
        try {
            writer.writeStartElement("game");
            writer.writeAttribute("id", String.valueOf(id));

            writer.writeStartElement("name");
            writer.writeCharacters(game.getName());
            writer.writeEndElement();

            writer.writeStartElement("developer");
            writer.writeCharacters(game.getDeveloper());
            writer.writeEndElement();

            writer.writeStartElement("publisher");
            writer.writeCharacters(game.getPublisher());
            writer.writeEndElement();

            writer.writeStartElement("releaseDate");
            writer.writeCharacters(game.getRelease_date());
            writer.writeEndElement();

            writeTags(writer, game);

            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeTags(XMLStreamWriter writer, Game game)  {
        try {
            writer.writeStartElement("tags");

            for (String tag : game.getTags()) {
                writer.writeStartElement("tag");
                writer.writeCharacters(tag);
                writer.writeEndElement();
            }
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}

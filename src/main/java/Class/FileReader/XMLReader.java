package Class.FileReader;

import Class.Entity.Game;
import Interface.FileReader.Reader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class XMLReader implements Reader {
    @Override
    public Object read(String fileName) {
        DocumentBuilder documentBuilder = null;
        Document document = null;

        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(fileName);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return getGames(document);
    }

    private ArrayList<Game> getGames(Document document) {
        ArrayList<Game> games = new ArrayList<>();

        Node root = document.getDocumentElement();
        NodeList gameNodes = root.getChildNodes();
        for (int i = 0; i < gameNodes.getLength(); i++) {
            Node gameNode = gameNodes.item(i);
            // Если нода не текст, то это игра
            if (gameNode.getNodeType() != Node.TEXT_NODE) {
                Game game = getGame(gameNode);
                games.add(game);
            }
        }
        return games;
    }

    private Game getGame(Node gameNode) {
        Game game = new Game();
        NodeList gameProperties = gameNode.getChildNodes();
        for (int i = 0; i < gameProperties.getLength(); i++) {
            Node gameProperty = gameProperties.item(i);
            // Если нода не текст, то это один из параметров игры
            if (gameProperty.getNodeType() != Node.TEXT_NODE) {
                switchProperty(gameProperty, game);
            }
        }
        return game;
    }

    private void switchProperty(Node gameProperty, Game game) {
        switch (gameProperty.getNodeName()) {
            case "name" -> game.setName(gameProperty.getTextContent());
            case "developer" -> game.setDeveloper(gameProperty.getTextContent());
            case "publisher" -> game.setPublisher(gameProperty.getTextContent());
            case "releaseDate" -> game.setRelease_date(gameProperty.getTextContent());
            case "tags" -> setTagsToGame(gameProperty, game);
            default -> throw new IllegalArgumentException("Неопознанное имя тега");
        }
    }

    private void setTagsToGame(Node gameProperty, Game game) {
        ArrayList<String> tags = new ArrayList<>();
        NodeList tagsNode = gameProperty.getChildNodes();
        for (int i = 0; i < tagsNode.getLength(); i++) {
            Node tagNode = tagsNode.item(i);
            if (tagNode.getNodeType() != Node.TEXT_NODE) {
                tags.add(tagNode.getTextContent());
            }
        }
        game.setTags(tags);
    }
}

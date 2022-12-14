package Main;

import Class.FileConverter.JsonToXML;
import Class.FileConverter.XMLToJson;
import Class.FileExtension.FileExtension;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        if (new File("data/output").mkdirs()) {
            System.out.println("Directory successfully created");
        }

        if (FileExtension.getExtension(args[0]).equals("xml")) {
            XMLToJson converter = new XMLToJson();
            converter.convert(args[0], args[1]);
            System.out.println("The conversion was successful, the result was saved to the " + args[1] + " file");
        }

        if (FileExtension.getExtension(args[0]).equals("json")) {
            JsonToXML converter = new JsonToXML();
            converter.convert(args[0], args[1]);
            System.out.println("The conversion was successful, the result was saved to the " + args[1] + " file");
        }
    }
}

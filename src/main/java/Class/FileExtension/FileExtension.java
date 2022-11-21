package Class.FileExtension;

import java.io.FileNotFoundException;

public class FileExtension {
    public static String getExtension(String fileName) {
        if (fileName.indexOf('.') < 0 || fileName.indexOf('.') == fileName.length() - 1) {
            try {
                throw new FileNotFoundException("Invalid file name");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}

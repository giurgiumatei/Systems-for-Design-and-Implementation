package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesCreationStartup {

    public static void createDirectories() {
        try {
            Path filesRoot = Paths.get("Files");
            if (!Files.exists(filesRoot))
                Files.createDirectory(filesRoot);

            Path textDirectory = Paths.get("Files/text");
            if (!Files.exists(textDirectory))
                Files.createDirectory(textDirectory);

            Path xmlDirectory = Paths.get("Files/xml");
            if (!Files.exists(xmlDirectory))
                Files.createDirectory(xmlDirectory);
        } catch (Exception e) {
            System.out.println("Could not create directories or files");
        }
    }

    public static void createTextFile() {
        try {
            Path cp = Paths.get("Files/text/clients.txt");
            if(!Files.exists(cp)) {
                Files.createFile(cp);
            }
            Path gp = Paths.get("Files/text/guntypes.txt");
            if(!Files.exists(gp)) {
                Files.createFile(gp);
            }
            Path rp = Paths.get("Files/text/rentals.txt");
            if(!Files.exists(rp)) {
                Files.createFile(rp);
            }
        } catch (Exception e) {
            System.out.println("Could not create text files");
        }
    }

    public static void createXMLFile() {
        try {
            Path cp = Paths.get("Files/xml/clients.xml");
            if(!Files.exists(cp)) {
                Files.createFile(cp);
                Files.write(cp, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<clients>\n</clients>\n".getBytes());
            }
            Path gp = Paths.get("Files/xml/guntypes.xml");
            if(!Files.exists(gp)) {
                Files.createFile(gp);
                Files.write(gp, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<guntypes>\n</guntypes>\n".getBytes());
            }
            Path rp = Paths.get("Files/xml/rentals.xml");
            if(!Files.exists(rp)) {
                Files.createFile(rp);
                Files.write(rp, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<rentals>\n</rentals>\n".getBytes());
            }
        } catch (Exception e) {
            System.out.println("Could not create text files");
        }
    }

}

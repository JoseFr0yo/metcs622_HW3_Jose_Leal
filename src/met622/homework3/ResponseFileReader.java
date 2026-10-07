package met622.homework3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// The ResponseFileReader Class reads the API response as a string.
public class ResponseFileReader {

    // Reads the file and returns the contents, returns null if the file does not exist.
    public String readFile(String fileName){
        File file = new File(fileName);

        // only read files that exist
        if (!file.exists()) {
            System.out.println("Cannot read " + fileName + ": the file does not exist.");
            return null;
        }

        StringBuilder content = new StringBuilder();
        // reader closes automatically when the block ends.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // readLine() returns null when it reaches the end of the content
            while ((line = reader.readLine()) != null) {
                content.append(line);
                // readLine() removes line break, added manually.
                content.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Could not read " + fileName + ": " + e);
            return null;
        }

        return content.toString();
    }
}
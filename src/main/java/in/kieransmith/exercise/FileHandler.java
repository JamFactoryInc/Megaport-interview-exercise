package in.kieransmith.exercise;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class FileHandler {

    /*
     * * Reads
     * 
     * @param path the path to read
     * 
     * @return ArrayList<String> the lines in order
     * 
     * @throws FileNotFoundException the path given does not exist or is not
     * accessible
     */
    public ArrayList<String> read(Path path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();
        File fileToRead = path.toFile();

        try (
                Scanner fileReader = new Scanner(fileToRead);) {
            while (fileReader.hasNextLine()) {
                lines.add(fileReader.nextLine());
            }
        }
        return lines;
    }

    /**
     * Writes the ArrayList of lines to the filepath, seperated by newline
     * characters
     *
     * @param path    the path to write to
     * @param strings the lines to write
     * @throws IOException the file cannot be written
     */
    public void write(Path path, ArrayList<String> lines) throws IOException, FileNotFoundException {
        // Using PrintStream to easily have os-specific newlines after each name
        try (
                PrintStream fileWriter = new PrintStream(path.toFile());) {
            for (String line : lines) {
                fileWriter.println(line);
            }
        }
    }

    /**
     * Removes the file extension from a path string
     * Example: "file.txt" -> "file"
     *
     * @param path the path string to remove the extension of
     * @return String the path string with the extension removed
     */
    public String stripExtension(String path) {
        return path.replaceAll("\\.[^\\.]*$", "");
    }

    /**
     * Generates a unique file path to use for temporary file testing
     *
     * @return Path the unique file path
     */
    public Path getUniquePath() {
        return Paths.get(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + UUID.randomUUID().toString()
                + ".txt");
    }

    /**
     * Alters the name and extension of a path
     * FileIO.modifyPathName(Paths.get("text.txt"), "%s-modified.txt") ->
     * Paths.get("text-modified.txt")
     *
     * @param path         the path to modify
     * @param formatString how the name should be formatted
     * @return Path the new path
     */
    public Path modifyPathName(Path path, String formatString) {
        return Paths.get(String.format(
                formatString,
                this.stripExtension(path.toString())));
    }
}

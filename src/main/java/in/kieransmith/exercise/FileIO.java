package in.kieransmith.exercise;

import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class FileIO {

    /**
     * @param path
     * @return ArrayList<String>
     * @throws FileNotFoundException
     */
    public static ArrayList<String> read(Path path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();

        File fileToRead = path.toFile();
        Scanner fileReader = new Scanner(fileToRead);
        while (fileReader.hasNext()) {
            lines.add(fileReader.nextLine());
        }
        fileReader.close();

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
    public static void write(Path path, ArrayList<String> lines) throws IOException, FileNotFoundException {
        // Using PrintStream to easily have os-specific newlines after each name
        PrintStream fileWriter = new PrintStream(path.toFile());
        for (String line : lines) {
            fileWriter.println(line);
        }
        fileWriter.close();
    }

    /**
     * @param path
     * @return String
     */
    public static String stripExtension(String path) {
        return path.replaceAll("\\.[^\\.]*$", "");
    }
}

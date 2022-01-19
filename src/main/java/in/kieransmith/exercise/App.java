package in.kieransmith.exercise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {

    public static Path inputFilePath;
    public static Path outputFilePath;

    /**
     * Prompts for input
     *
     * @param args
     */
    public static void main(String[] args) {
        // ! EDIT THIS LINE
        String temporaryArg = "C:\\Users\\jam\\Downloads\\names.txt";
        setInputPath(temporaryArg);

        // Scanner is initialized here and passed into `promptForInput` because
        // NoSuchElementException was thrown after more than one call.
        // This is due to the repeated opening/closing of the scanner and this
        // fixes that issue
        Scanner userInputReader = new Scanner(System.in);

        // Yell at the user until valid input is given
        while (true) {
            try {
                NameList names = NameList.from(FileIO.read(inputFilePath)).sort();
                FileIO.write(outputFilePath, names.toStringArray());
                System.out.println(String.format("Finished: created %s", outputFilePath.toString()));
                break;
            } catch (FileNotFoundException e) {
                setInputPath(promptForInput("Could not find '%s'. Please enter a valid path:",
                        new String[] { inputFilePath.toString() },
                        userInputReader));
            } catch (MalformedInputFileException e) {
                setInputPath(
                        promptForInput("Line '%s' in '%s' is not formatted correctly. Please enter a valid path:",
                                new String[] { e.badString, inputFilePath.toString() },
                                userInputReader));
            } catch (IOException e) {
                setInputPath(promptForInput("Cannot write %s. Please enter a valid path:",
                        new String[] { inputFilePath.toString() },
                        userInputReader));
            }
        }
        userInputReader.close();
    }

    /**
     * @param message    A message to show in order to prompt for a new path
     * @param formatArgs A String[] of args to use when formatting the `message`
     * @return String The filepath
     */
    public static String promptForInput(String message, String[] formatArgs, Scanner activeScanner) {
        String userInput = "";
        System.out.print(String.format(message, (Object[]) formatArgs));

        userInput = activeScanner.nextLine();

        return userInput;
    }

    /**
     * Simultaneously sets inputFilePath and derives outputFilePath
     *
     * @param path the path string to use
     */
    public static void setInputPath(String path) {
        inputFilePath = Paths.get(path);
        outputFilePath = Paths.get(String.format("%s-sorted.txt",
                FileIO.stripExtension(inputFilePath.toString())));
    }
}

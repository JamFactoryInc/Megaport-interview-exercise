package in.kieransmith.exercise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {

    public static Path inputFilePath;
    public static Path outputFilePath;

    // Scanner is initialized here and passed into `promptForInput` because
    // NoSuchElementException was thrown after more than one call.
    // This is due to the scanner being closed elsewhere when reading a file
    // with FileIO.read()
    public static Scanner userInputReader = new Scanner(System.in);

    /**
     * Prompts for input
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            run(promptForInput("Please enter a file path to sort:",
                    new String[] {},
                    userInputReader));
        } else if (args.length == 1) {
            run(args[0]);
        } else {
            bulkRun(args);
        }

    }

    public static void run(String arg) {
        setInputPath(arg);

        // Yell at the user until valid input is given
        while (true) {
            try {
                readSortAndWrite(inputFilePath, outputFilePath);
                break;
            } catch (FileNotFoundException e) {
                setInputPath(promptForInput("Could not find '%s'. Please enter a valid path:",
                        new String[] { inputFilePath.toString() },
                        userInputReader));
            } catch (MalformedInputException e) {
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

    public static void readSortAndWrite(Path input, Path output)
            throws FileNotFoundException, MalformedInputException, IOException {
        NameList names = NameList.from(FileIO.read(input)).sort();
        FileIO.write(output, names.toStringArray());
        System.out.println(String.format("Finished: created %s", output.toString()));
    }

    public static void bulkRun(String[] paths) {
        for (String path : paths) {
            try {
                setInputPath(path);
                readSortAndWrite(inputFilePath, outputFilePath);
            } catch (IOException | MalformedInputException e) {
                System.out.println(String.format("Error caught while sorting %s. Skipped.", outputFilePath.toString()));
            }
        }
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

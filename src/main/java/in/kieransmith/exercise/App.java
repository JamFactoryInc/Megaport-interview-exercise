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
    public static Scanner userInputReader;

    public static FileHandler fileHandler = new FileHandler();

    /**
     * Runs the app based on given args
     *
     * @param args optional file paths to sort
     */
    public static void main(String[] args) {
        userInputReader = new Scanner(System.in);
        App app = new App();

        if (args.length == 0) {
            app.run(app.promptForInput("Please enter a file path to sort:",
                    new String[] {},
                    userInputReader));
        } else if (args.length == 1) {
            app.run(args[0]);
        } else {
            app.bulkRun(args);
        }

    }

    /**
     * Is given a file path to try and sort, and keeps prompting for a file path
     * until a valid path is given
     *
     * @param arg the path to sort
     */
    public void run(String arg) {
        if (arg.equals("exit"))
            return;
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
                setInputPath(promptForInput("Cannot write '%s'. Please enter a valid path:",
                        new String[] { inputFilePath.toString() },
                        userInputReader));
            } finally {
                if (inputFilePath.toString().equals("exit")) {
                    userInputReader.close();
                    return;
                }
            }
        }
        userInputReader.close();
    }

    /**
     * The basic logic of the app. Reads the given input path into a NameList, sorts
     * it, and writes it to the given output path
     *
     * @param input  the path to read from
     * @param output the path to write to
     * @throws FileNotFoundException   the input file is unopenable or missing
     * @throws MalformedInputException the input file is not in the correct format
     * @throws IOException             the file cannot be written
     */
    public void readSortAndWrite(Path input, Path output)
            throws FileNotFoundException, MalformedInputException, IOException {
        NameList names = NameList.from(fileHandler.read(input)).sort();
        fileHandler.write(output, names.toStringArray());
        System.out.println(String.format("Finished: created %s", output.toString()));
    }

    /**
     * Made to process more than one file at a time. Skips invalid files rather that
     * prompting for a valid path
     *
     * @param paths the paths to process
     */
    public void bulkRun(String[] paths) {
        for (String path : paths) {
            try {
                setInputPath(path);
                readSortAndWrite(inputFilePath, outputFilePath);
            } catch (IOException | MalformedInputException e) {
                System.out.println(String.format("Error caught while sorting '%s'. Skipped.",
                        outputFilePath.toString()));
            }
        }
    }

    /**
     * @param message    A message to show in order to prompt for a new path
     * @param formatArgs A String[] of args to use when formatting the `message`
     * @return String The filepath
     */
    public String promptForInput(String message, String[] formatArgs, Scanner activeScanner) {
        String userInput = "";
        System.out.print(String.format(message, (Object[]) formatArgs));
        userInput = activeScanner.nextLine();
        return userInput;
    }

    /**
     * Simultaneously sets inputFilePath and derives outputFilePath
     * outputFilePath is in the format <inputFilePath>-sorted.txt
     *
     * @param path the path string to use
     */
    public void setInputPath(String path) {
        inputFilePath = Paths.get(path);
        outputFilePath = fileHandler.modifyPathName(inputFilePath, "%s-sorted.txt");
    }
}

package in.kieransmith.exercise.integration;

import static org.junit.Assert.assertTrue;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.FixMethodOrder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;

import in.kieransmith.exercise.FileIO;
import in.kieransmith.exercise.NameList;
import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

import in.kieransmith.exercise.App;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppIntegrationTest {
        @Test
        public void NameListFromFile_ExpectOrderedNameList() throws IOException, MalformedInputException {
                // Arrange
                Path inputPath = FileIO.getUniquePath();
                NameList desiredNameList = new NameList();
                desiredNameList.add(new Name("BAKER", "THEODORE"));
                desiredNameList.add(new Name("KENT", "MADISON"));
                desiredNameList.add(new Name("SMITH", "ANDREW"));
                desiredNameList.add(new Name("SMITH", "FREDRICK"));

                // Act
                FileIO.write(inputPath, new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK")));
                NameList actual = NameList.from(FileIO.read(inputPath)).sort();

                // Assert
                assertTrue(String.format("%s should equal %s", desiredNameList.toString(), actual.toString()),
                                desiredNameList.equals(actual));

                // Clean
                inputPath.toFile().delete();
        }

        @Test
        public void RunBulkFileSort_ExpectSortedFiles() throws IOException {
                // Arrange
                Path filePath1 = FileIO.getUniquePath();
                Path filePath2 = FileIO.getUniquePath();
                ArrayList<String> unsortedNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK"));
                ArrayList<String> desiredNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "KENT, MADISON", "SMITH, ANDREW", "SMITH, FREDRICK"));
                Path sortedFile1 = Paths.get(String.format("%s-sorted.txt",
                                FileIO.stripExtension(filePath1.toString())));
                Path sortedFile2 = Paths.get(String.format("%s-sorted.txt",
                                FileIO.stripExtension(filePath2.toString())));
                FileIO.write(filePath1, unsortedNames);
                FileIO.write(filePath2, unsortedNames);

                // Act
                App.main(new String[] { filePath1.toString(), filePath2.toString() });

                // Assert
                ArrayList<String> actual1 = FileIO.read(sortedFile1);
                assertTrue(String.format("%s should equal %s", unsortedNames.toString(),
                                actual1.toString()),
                                actual1.equals(desiredNames));
                ArrayList<String> actual2 = FileIO.read(sortedFile2);
                assertTrue(String.format("%s should equal %s", unsortedNames.toString(),
                                actual2.toString()),
                                actual2.equals(desiredNames));

                // Clean
                filePath1.toFile().delete();
                filePath2.toFile().delete();
                sortedFile1.toFile().delete();
                sortedFile2.toFile().delete();
        }

        @Test
        public void ReadFileWriteSortedFile_ExpectSortedFile() throws IOException, MalformedInputException {
                // Arrange
                Path filePath = FileIO.getUniquePath();
                ArrayList<String> unsortedNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK"));
                ArrayList<String> desiredNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "KENT, MADISON", "SMITH, ANDREW", "SMITH, FREDRICK"));

                FileIO.write(filePath, unsortedNames);

                // Act
                NameList names = NameList.from(FileIO.read(filePath)).sort();
                FileIO.write(filePath, names.toStringArray());

                ArrayList<String> actualNames = FileIO.read(filePath);

                // Assert
                assertTrue(String.format("%s should equal %s", actualNames.toString(), desiredNames.toString()),
                                actualNames.equals(desiredNames));

                // Clean
                filePath.toFile().delete();
        }
}

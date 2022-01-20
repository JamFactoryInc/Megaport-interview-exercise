package in.kieransmith.exercise.integration;

import static org.junit.Assert.assertTrue;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.FixMethodOrder;

import java.nio.file.Path;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;

import in.kieransmith.exercise.FileIO;
import in.kieransmith.exercise.NameList;
import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

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

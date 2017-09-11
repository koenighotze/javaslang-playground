package org.koenighotze.javaslangplayground;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * @author dschmitz
 */
public class FileReader {

    public List<String> readFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Baeh file " + path.toString() + " does not exist!");
        }

        return Files.readAllLines(path);
    }
}

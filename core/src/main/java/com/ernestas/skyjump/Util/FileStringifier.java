package com.ernestas.skyjump.Util;

import com.google.common.base.Joiner;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileStringifier {

    private FileStringifier() {}

    public static String fileToString(String path) throws IOException {
        return fileToString(new File(path));
    }

    public static String fileToString(File file) throws IOException {
        BufferedReader reader;
        try {
            FileReader fr = new FileReader(file);
            reader = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        }

        if (!file.isFile()) {
            throw new IOException("File is actually not a file.");
        }

        if (!file.canRead()) {
            throw new IOException("Could not read file. Check file permissions.");
        }

        return readerToString(reader);
    }

    public static String readerToString(BufferedReader reader) throws IOException {
        List<String> lines = new LinkedList<>();
        try {
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
        }

        return Joiner.on("").skipNulls().join(lines);
    }

}

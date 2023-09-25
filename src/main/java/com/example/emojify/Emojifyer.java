package com.example.emojify;

import io.ktor.utils.io.core.Input;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Emojifyer {
    private static final String EMOJIS_FILE_PATH = "/emojis.csv";
    Map<Pattern, String> emojis = new HashMap<>();

    Emojifyer() {
            readEmojis();
    }

    private void readEmojis() {
        InputStream emojisInputStream = Objects.requireNonNull(getClass().getResourceAsStream(EMOJIS_FILE_PATH));
        InputStreamReader inputStreamReader = new InputStreamReader(emojisInputStream);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineArr = line.split("; ");
                if (lineArr.length == 4) {
                    emojis.put(Pattern.compile(lineArr[1], Pattern.CASE_INSENSITIVE), lineArr[0]);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public String emojify(String text) {
        for (Pattern key: emojis.keySet()) {
            text = key.matcher(text).replaceAll(emojis.get(key));
        }
        return text;
    }
}

package com.zonief.trendsidentifier.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KeyWordCheckerService {
  private static final String FILENAME = "keywords.txt";

  private static void writeKeyword(String keyword, File file) {
    try (FileWriter writer = new FileWriter(file, true)) {
      writer.write(keyword + "\n");
    } catch (IOException e) {
      log.error("Error writing to file: " + e.getMessage());
    }
  }

  public boolean isNew(String keyword) {
    boolean foundKeyword = false;

    File file = new File(FILENAME);
    if (!file.exists()) {
      try {
        writeKeyword(keyword,file.createNewFile() ? file : null);
        return true;
      } catch (IOException e) {
        log.error("Error creating file: " + e.getMessage());
      }
    }

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.contains(keyword)) {
          return false;
        }
      }
    } catch (IOException e) {
      log.error("Error reading file: " + e.getMessage());
    }

    // if the keyword was not found, add it to the file
    if (!foundKeyword) {
      writeKeyword(keyword, file);
    }

    log.info("Keyword \"" + keyword + "\" " + (foundKeyword ? "was" : "was not") + " found in the file.");

    return !foundKeyword;
  }
}

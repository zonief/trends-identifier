package com.zonief.trendsidentifier.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

  public String giveFirstValidKeyword(List<String> keywords) {

    var resultKeyword = StringUtils.EMPTY;

    for (String keyword : keywords) {
      var keywordFound = false;

      File file = new File(FILENAME);
      if (!file.exists()) {
        try {
          writeKeyword(keyword, file.createNewFile() ? file : null);
          resultKeyword = keyword;
        } catch (IOException e) {
          log.error("Error creating file: " + e.getMessage());
        }
      }

      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          if (line.contains(keyword)) {
            keywordFound = true;
            break;
          }
        }
      } catch (IOException e) {
        log.error("Error reading file: " + e.getMessage());
      }

      if (!keywordFound) {
        writeKeyword(keyword, file);
        break;
      }
    }

    return resultKeyword;
  }
}

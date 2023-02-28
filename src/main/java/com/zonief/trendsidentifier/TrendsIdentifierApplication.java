package com.zonief.trendsidentifier;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class TrendsIdentifierApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrendsIdentifierApplication.class, args);
  }
}

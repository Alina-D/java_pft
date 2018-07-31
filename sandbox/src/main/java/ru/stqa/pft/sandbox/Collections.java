package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

  public static void main (String[] args) {
    String[] lang = {"Python", "Ruby", "JS", "Java"};

    List<String> languages = Arrays.asList("Python", "Ruby", "JS", "Java");

    for (int i =0; i < languages.size(); i++) {
      System.out.println("Я хочу выучить " + languages.get(i));
    }
    for (String l : languages) {
      System.out.println("Я хочу выучить " + l);
    }
  }
}

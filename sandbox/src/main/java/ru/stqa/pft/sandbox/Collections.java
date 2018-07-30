package ru.stqa.pft.sandbox;

public class Collections {

  public static void main (String[] args) {
    String[] lang = {"Python", "Ruby", "JS", "Java"};

    for (String l : lang) {
      System.out.println("Я хочу выучить " + l);
    }
  }
}

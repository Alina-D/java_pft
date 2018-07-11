package ru.stqa.pft.sandbox;

public class MySecondProgram {
  public static void main(String[] args) {

    Point p1 = new Point(5, 6);
    Point p2 = new Point(7, 9);

    System.out.println("Расстояние между точками: [" + p1.a + ", " + p2.a + "] и [" + p1.b + ", " + p2.b + "] " );
    System.out.println("Равно: " + p1.distance(p2));
  }
}

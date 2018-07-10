package ru.stqa.pft.sandbox;

public class Point {

  public double a;
  public double b;


  public Point(double a, double b) {
    this.a = a;
    this.b = b;
  }

  public double distanceCoordinate () {
    return  Math.pow((this.b - this.a),2);
  }

  public double distancePoints () {
    return  Math.sqrt((this.a + this.b));
  }

}

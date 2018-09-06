package ru.stqa.pft.mantis.model;

import java.util.Objects;

public class UserData {

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public UserData withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return id == userData.id &&
            Objects.equals(name, userData.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}

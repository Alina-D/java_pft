package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigatorHelper extends HelperBase{

  public NavigatorHelper(WebDriver wd) {
    super(wd);
  }

  public void goToGroupPage() {
    if(isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Group")
            && isElementPresent(By.name("new"))) {
    return;
    } else {
      click(By.linkText("groups"));
    }
  }

  public void goToContactPage() {
      click(By.linkText("add new"));
  }

  public void goToHomePage() {
    if(! isElementPresent(By.id("maintable"))) {
      click(By.linkText("home"));
    }
  }
}

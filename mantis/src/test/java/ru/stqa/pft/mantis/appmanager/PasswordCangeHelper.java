package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class PasswordCangeHelper extends HelperBase {

  public PasswordCangeHelper(ApplicationManager app) {
    super(app);

    wd = app.getDriver();
  }

  public void startAdm() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), app.getProperty("web.adminLogin"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value='Login']"));
  }


}

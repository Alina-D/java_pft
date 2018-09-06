package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

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

  public void openProfileUser(UserData user) {
    click(By.linkText("Manage Users"));
    click(By.linkText(user.getName()));
  }

  public void sendEmailWithRecoveryLink(UserData user) {
    openProfileUser(user);
    click(By.xpath("//input[@value=\"Reset Password\"]"));
  }
}

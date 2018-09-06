package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

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

  public void newPassword(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("//input[@value=\"Update User\"]"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}

package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase {

  @Test
  public void testPasswordChange() throws IOException, MessagingException {
    Users contacts = app.db().users();
    UserData user = contacts.iterator().next();
    String username = user.getName();
    while (username.equals("administrator")) {
      user = contacts.iterator().next();
      username = user.getName();
    }
    String email = user.getEmail();
    String password = "password";
    String newPassword = "password";

    app.pass().startAdm();
    app.james().drainEmail(username, password);
    app.pass().sendEmailWithRecoveryLink(user);
    List<MailMessage> mailMessages = app.james().waitForMail(username, password, 120000);
    String confirmationLink = app.pass().findConfirmationLink(mailMessages, email);
    app.pass().newPassword(confirmationLink, newPassword);

    HttpSession session = app.newSession();
    assertTrue(session.login(username, newPassword));
    assertTrue(session.isLoggerInAs(username));
  }
}

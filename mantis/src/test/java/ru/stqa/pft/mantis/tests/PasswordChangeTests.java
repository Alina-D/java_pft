package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase{

  @Test
  public void testPasswordChange () throws IOException {
    Users contacts = app.db().users();
    UserData user = contacts.iterator().next();

    System.out.println("_______________");
    System.out.println(user);
    System.out.println("_______________");
    app.pass().startAdm();
    app.pass().sendEmailWithRecoveryLink(user);
    
  }
}

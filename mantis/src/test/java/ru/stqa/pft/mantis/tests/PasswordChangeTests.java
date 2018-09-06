package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase{

  @Test
  public void testPasswordChange () throws IOException {
    app.pass().startAdm();

  }


}

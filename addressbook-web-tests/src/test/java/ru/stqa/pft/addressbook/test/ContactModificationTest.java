package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigatorHelper().goToHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData(
            "firstname1", "lastname1", "address1", "phone1", "email1"));
    app.getContactHelper().submitToContactModification();
    app.getContactHelper().returnToContactPage();
  }
}

package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigatorHelper().goToHomePage();
    if (!app.getContactHelper().idThereAContact()) {
      app.getNavigatorHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData(
              "firstname1", "lastname1", "address1", "phone1",
              "email1", "name1"), true);
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData(
            "firstname1", "lastname1", "address1", "phone1",
            "email1", null), false);
    app.getContactHelper().submitToContactModification();
    app.getContactHelper().returnToContactPage();
  }
}

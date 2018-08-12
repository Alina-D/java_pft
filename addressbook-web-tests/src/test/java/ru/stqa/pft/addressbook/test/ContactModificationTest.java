package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

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
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initContactModification();
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(),
            "firstname1", "lastname1", "address1", "phone1",
            "email1", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitToContactModification();
    app.getContactHelper().returnToContactPage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(before));
  }
}

package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigatorHelper().goToHomePage();
    if (!app.getContactHelper().idThereAContact()) {
      app.getNavigatorHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData(
              "firstname1", "lastname1", "address1", "phone1",
              "email1", "name1"), true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().closeWindowDeletion();
    app.getNavigatorHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);
  }
}

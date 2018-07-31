package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTest extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigatorHelper().goToHomePage();
    int before = app.getGroupHelper().getGroupCount();
    if (!app.getContactHelper().idThereAContact()) {
      app.getNavigatorHelper().goToContactPage();
      app.getContactHelper().createContact(new ContactData(
              "firstname1", "lastname1", "address1", "phone1",
              "email1", "name1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().closeWindowDeletion();
    app.getNavigatorHelper().goToHomePage();
    int after = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(after, before - 1);
  }
}

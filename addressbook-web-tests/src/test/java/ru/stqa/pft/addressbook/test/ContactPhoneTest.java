package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().contactPage();
      app.contact().create(new ContactData()
              .withFirstName("firstname1").withLastName("lastname1").withAddress("address1").withEmail("email1")
              .withGroup("name1").withHomePhone("111").withMobilePhone("222").withWorkPhone("333"), true);
    }
  }

  @Test
  public void testContactPhones(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFormEditForm = app.contact().intoFormEditForm(contact);

  }



}
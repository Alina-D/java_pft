package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  public static String cleaned(String address) {
    return address.replaceAll("\\s", "");
  }

  @BeforeMethod
  public void ensurePreconditions() {
    if(app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.goTo().contactPage();
      File photo = new File("src/test/resources/img.jpg");
      app.contact().create(new ContactData()
              .withFirstName("firstname1").withLastName("lastname1").withAddress("address1").withEmail("email1")
              .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
              .withMobilePhone("222").withWorkPhone("333").withPhoto(photo), true);
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFormEditForm = app.contact().intoFormEditForm(contact);

    assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFormEditForm.getAddress())));
  }

}

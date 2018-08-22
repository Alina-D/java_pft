package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("firstname1").withLastName("lastname1").withAddress("address1").withEmail("email1")
            .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
            .withMobilePhone("222").withWorkPhone("333");
    app.goTo().contactPage();
    File photo = new File("src/test/resources/img.jpg");
    app.contact().create(contact.withPhoto(photo), true);
    Contacts after = app.contact().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before.withAdded(
            contact.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("firstname1").withLastName("lastname1'").withAddress("address1").withEmail("email1")
            .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
            .withMobilePhone("222").withWorkPhone("333");
    app.goTo().contactPage();
    app.contact().create(contact, true);

    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

  @Test
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/img.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
}

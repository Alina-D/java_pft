package ru.stqa.pft.addressbook.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData()
            .withFirstName("firstname1").withLastName("lastname1").withAddress("address1").withEmail("email1")
            .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
            .withMobilePhone("222").withWorkPhone("333")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation (ContactData contact) {
    Contacts before = app.contact().all();
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
    Object[] name = new Object[]{new ContactData()
            .withFirstName("firstname1").withLastName("lastname1'").withAddress("address1").withEmail("email1")
            .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
            .withMobilePhone("222").withWorkPhone("333")};

    ContactData contact = new ContactData()
            .withFirstName("firstname1").withLastName("lastname1'").withAddress("address1").withEmail("email1")
            .withEmail2("email2").withEmail3("email3").withGroup("name1").withHomePhone("111")
            .withMobilePhone("222").withWorkPhone("333");
    Contacts before = app.contact().all();
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

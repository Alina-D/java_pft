package ru.stqa.pft.addressbook.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[] { new ContactData().withFirstName(split[0]).withLastName(split[1])
              .withAddress(split[2]).withEmail(split[3]).withEmail2(split[4]).withEmail3(split[5])
              .withGroup(split[6]).withHomePhone(split[7]).withMobilePhone(split[8]).withWorkPhone(split[9])});
      line = reader.readLine();
    }
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

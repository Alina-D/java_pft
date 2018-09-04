package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;


public class AddContactToGroupTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if(app.db().contacts().size() == 0) {
      if (app.db().groups().size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("name1").withHeader("header1").withFooter("footer1"));
      }
      File photo = new File("src/test/resources/img.jpg");
      Groups groups = app.db().groups();
      ContactData contact = new ContactData()
              .withFirstName("firstname1").withLastName("lastname1").withAddress("address1").withEmail("email1")
              .withEmail2("email2").withEmail3("email3").withHomePhone("111")
              .withMobilePhone("222").withWorkPhone("333").withPhoto(photo).inGroup(groups.iterator().next());
      app.goTo().homePage();
      app.goTo().contactPage();
      app.contact().create(contact, true);
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    int before = contact.getGroups().size();
    if(groups.size() == contact.getGroups().size()){
      app.contact().removeGroup(contact, group);
      app.contact().addContactToGroup(contact, group);
    } else {
      app.contact().addContactToGroup(contact, group);
    }
    int after = contact.getGroups().size();
//        assertThat(after, equalTo(before));
  }
}

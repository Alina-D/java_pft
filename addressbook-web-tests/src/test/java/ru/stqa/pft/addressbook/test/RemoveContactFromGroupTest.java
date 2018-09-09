package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTest extends TestBase{

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

    int beforeGroups = contact.getGroups().size();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    int beforeContacts = group.getContacts().size();

    for (ContactData c: contacts) {
      System.out.println("c " + c);
      if (c.getGroups().size() != 0) {
        contact = c;
        System.out.println("contact" + contact);
        break;
      }
    }

    if (beforeGroups == 0) {
      app.contact().addContactToGroup(contact, group);
      beforeContacts = app.db().groups().iterator().next().withId(group.getId()).getContacts().size();
      app.contact().removeGroup(contact, group);
    } else {
      group = contact.getGroups().iterator().next();
      app.contact().removeGroup(contact, group);
    }
    int afterGroups = contact.getGroups().size();
    int afterContacts = app.db().groups().iterator().next().withId(group.getId()).getContacts().size();

    assertThat(afterGroups, equalTo(beforeGroups));
    assertThat(afterContacts, equalTo(beforeContacts - 1));
  }
}

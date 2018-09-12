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
    for (ContactData c: contacts) {
      if (c.getGroups().size() != 0) {
        contact = c;
        break;
      }
    }

    Groups beforeGroups = contact.getGroups();
    Groups groups = app.db().groups();
    Contacts beforeContacts;
    GroupData group;

    if (beforeGroups.size() == 0) {
      group = groups.iterator().next();
      app.contact().addContactToGroup(contact, group);
      beforeGroups = app.db().contactWithId(contact.getId()).iterator().next().getGroups();
      beforeContacts = app.db().groupWithId(group.getId()).iterator().next().getContacts();
      app.contact().removeGroup(contact, group);
    } else {
      group = beforeGroups.iterator().next();
      beforeContacts = group.getContacts();
      app.contact().removeGroup(contact, group);
    }

    Groups afterGroups = app.db().contactWithId(contact.getId()).iterator().next().getGroups();
    Contacts afterContacts = app.db().groupWithId(group.getId()).iterator().next().getContacts();

    assertThat(afterGroups.size(), equalTo(beforeGroups.size() - 1));
    assertThat(afterContacts.size(), equalTo(beforeContacts.size() - 1));
    assertThat(afterContacts, equalTo(beforeContacts.without(contact)));
    assertThat(afterGroups, equalTo(beforeGroups.without(group)));
  }
}

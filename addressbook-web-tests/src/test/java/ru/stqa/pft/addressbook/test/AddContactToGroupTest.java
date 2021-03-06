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
    Contacts beforeContacts;

    for (ContactData c: contacts) {
      System.out.println("c " + c);
      if (groups.size() != c.getGroups().size()) {
        contact = c;
        System.out.println("contact" + contact);
        break;
      }
    }
    Groups beforeGroups = contact.getGroups();

    if(groups.size() == contact.getGroups().size()){
      app.contact().removeGroup(contact, group);
      beforeGroups = app.db().contactWithId(contact.getId()).iterator().next().getGroups();
      beforeContacts = app.db().groupWithId(group.getId()).iterator().next().getContacts();
      app.contact().addContactToGroup(contact, group);
    } else {
      groups.removeAll(contact.getGroups());
      group = groups.iterator().next();
      beforeContacts = app.db().groupWithId(group.getId()).iterator().next().getContacts();
      app.contact().addContactToGroup(contact, group);
    }

    Groups afterGroups = app.db().contactWithId(contact.getId()).iterator().next().getGroups();
    Contacts afterContacts = app.db().groupWithId(group.getId()).iterator().next().getContacts();

    assertThat(afterGroups.size(), equalTo(beforeGroups.size() + 1));
    assertThat(afterContacts.size(), equalTo(beforeContacts.size() +  1));

    beforeGroups.add(group);
    beforeContacts.add(contact);

    assertThat(afterGroups, equalTo(beforeGroups));
    assertThat(afterContacts, equalTo(beforeContacts));
  }
}

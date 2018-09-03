package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

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
    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next();
//    System.out.println("____contact______" + contact.getGroups().iterator().next().getName());
    if (contact.getGroups().size() == 0) {
      GroupData newGroup = new GroupData().withName("name1").withHeader("header1").withFooter("footer1");
      app.goTo().groupPage();
      app.group().create(newGroup);
      app.goTo().homePage();
      System.out.println("_______________- " + newGroup.getId() + newGroup.getName());
      System.out.println("new " + newGroup);
      app.contact().addContactToGroup(contact, newGroup);
      GroupData group = contact.getGroups().iterator().next();
      app.contact().removeGroup(contact, group);
    } else {
      GroupData group = contact.getGroups().iterator().next();
      System.out.println("old " + group);
      // GroupData{id='166', name='name1'}
      app.contact().removeGroup(contact, group);
    }

//    assertThat());
  }
}

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

public class ContactModificationTests extends TestBase {

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
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstName("firstname1").withLastName("lastname1")
            .withEmail2("email2").withEmail3("email3").withAddress("address1").withEmail("email1")
            .withHomePhone("111").withMobilePhone("222").withWorkPhone("333");
    app.contact().modify(contact);

    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}

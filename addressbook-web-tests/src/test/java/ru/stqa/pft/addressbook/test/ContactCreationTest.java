package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigatorHelper().goToContactPage();
        app.getContactHelper().fillContactForm(new ContactData(
                "firstname1", "lastname1", "address1", "phone1", "email1"));
        app.getContactHelper().submitToContactPage();
        app.getContactHelper().returnToContactPage();
    }

}

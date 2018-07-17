package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.goToContactPage();
        app.fillContactForm(new ContactData(
                "firstname1", "lastname1", "address1", "phone1", "email1"));
        app.submitToContactPage();
        app.returnToContactPage();
    }

}

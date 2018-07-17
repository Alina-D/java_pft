package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

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

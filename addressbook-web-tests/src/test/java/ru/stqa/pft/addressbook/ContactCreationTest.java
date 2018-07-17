package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        goToContactPage();
        fillContactForm(new ContactData(
                "firstname1", "lastname1", "address1", "phone1", "email1"));
        submitToContactPage();
        returnToContactPage();
    }

}

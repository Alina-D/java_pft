package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home page"));
  }

  public void submitToContactPage() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getPhone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactModification(int id) {
    wd.findElement(By.xpath("//input[@value='"+ id +"']/following::td[7]")).click();
  }

  public void submitToContactModification() {
    click(By.name("update"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='"+ id +"']")).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value=\"Delete\"]"));
  }

  public void closeWindowDeletion() {
    wd.switchTo().alert().accept();
  }

  public void create(ContactData contact, boolean creation) {
    fillContactForm(contact, creation);
    submitToContactPage();
    returnToContactPage();
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitToContactModification();
    returnToContactPage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    closeWindowDeletion();
  }

  public boolean idThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<ContactData>();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> sells = row.findElements(By.tagName("td")) ;
      String firstname = sells.get(2).getText();
      String lastname = sells.get(1).getText();
      String address = sells.get(3).getText();
      String phone = sells.get(5).getText();
      String email = sells.get(4).getText();

      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withFirstName(firstname).withLastName(lastname)
              .withAddress(address).withPhone(phone).withEmail(email);
      contacts.add(contact);
    }
    return contacts;
  }
}

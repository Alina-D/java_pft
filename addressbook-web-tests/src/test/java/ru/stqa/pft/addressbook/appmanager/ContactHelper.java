package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home page"));
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());


    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactModificationById(int id) {
//    wd.findElement(By.xpath("//input[@value='"+ id +"']/following::td[7]")).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
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
    submitContactCreation();
    contactCashe = null;
    returnToContactPage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitToContactModification();
    contactCashe = null;
    returnToContactPage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCashe = null;
    closeWindowDeletion();
  }

  public boolean idThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCashe = null;

  public Contacts all() {
    if (contactCashe != null) {
      return new Contacts(contactCashe);
    }
    contactCashe = new Contacts();
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
              .withAddress(address).withEmail(email).withHomePhone("111").withMobilePhone("222").withWorkPhone("333");
      contactCashe.add(contact);
    }
    return new Contacts(contactCashe);
  }

  public ContactData intoFormEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withHomePhone(home)
            .withMobilePhone(mobile).withWorkPhone(work);
  }
}

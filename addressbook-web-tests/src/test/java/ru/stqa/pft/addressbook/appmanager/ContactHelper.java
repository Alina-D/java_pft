package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
      attach(By.name("photo"), contactData.getPhoto());
      if(contactData.getGroups().size() > 0)
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroups().iterator().next().getName());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactModificationById(int id) {
//    wd.findElement(By.xpath("//input[@value='"+ id +"']/following::td[7]")).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  private void returnToGroupPage(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='./?group=%s']", id))).click();
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

  public void addContactToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    addContactToGroup(group);
    clickAddGroup();
    contactCashe = null;
    returnToGroupPage(group.getId());
  }

  public void removeGroup(ContactData contact, GroupData groups) {
    selectGroup(groups);
    selectContactById(contact.getId());
    removeGroupFromContact();
    contactCashe = null;
    returnToGroupPage(groups.getId());
    returnToAllGroupPage();
  }

  private void removeGroupFromContact() {
    click(By.name("remove"));
  }

  private void returnToAllGroupPage() {
    new Select(wd.findElement(By.name("group")))
          .selectByVisibleText("[all]");
  }

  private void selectGroup(GroupData group) {
    new Select(wd.findElement(By.name("group")))
              .selectByVisibleText(group.getName());
  }

  private void addContactToGroup(GroupData group) {
    new Select(wd.findElement(By.name("to_group")))
            .selectByVisibleText(group.getName());
  }

  private void clickAddGroup() {
    click(By.name("add"));
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
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = sells.get(1).getText();
      String firstname = sells.get(2).getText();
      String address = sells.get(3).getText();
      String allEmails = sells.get(4).getText();
      String allPhones = sells.get(5).getText();

      ContactData contact = new ContactData().withId(id).withFirstName(firstname).withLastName(lastname)
              .withAddress(address).withAllEmail(allEmails).withAllPhones(allPhones);
      contactCashe.add(contact);
    }
    return new Contacts(contactCashe);
  }

  public ContactData intoFormEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withAddress(address)
            .withEmail(email1).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }
}

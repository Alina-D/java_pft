package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigatorHelper().goToGroupPage();
    app.getGroupHelper().createGroup(new GroupData("name1", "header1", "footer1"));
  }

}

package ru.stqa.pft.rest;

import com.jayway.restassured.RestAssured;
import org.testng.SkipException;


public class TestBase {

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  private boolean isIssueOpen(int issueId) {
//    String json = RestAssured.get("http://bugify.stqa.ru/api/issues.json?limit=300").asString();
    return false;
  }
}

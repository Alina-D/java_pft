package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;


public class TestBase {


  private boolean isIssueOpen(int issueId) {
    String json = RestAssured.get(String.format("http://bugify.stqa.ru/api/issues/%s.json", issueId)).asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    JsonElement stateName = issues.getAsJsonArray().get(0).getAsJsonObject().get("state_name");
    Boolean result = !stateName.equals("Closed") || !stateName.equals("Resolved");
    return result;
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}

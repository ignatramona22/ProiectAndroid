package ro.ase.grupa1094;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserParser
{
  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private static final String PHONE = "phone";
  private static final String PASSWORD = "password";

  public static List<UserProfile> parsareJSON(String json)
  {
      try {
          JSONArray jsonArray = new JSONArray(json);
          return parsareUseri(jsonArray);
      } catch (JSONException e) {
          throw new RuntimeException(e);
      }
  }
  private static List<UserProfile> parsareUseri(JSONArray jsonArray) throws JSONException {
      List<UserProfile> useri = new ArrayList<>();
      for(int i=0; i< jsonArray.length(); i++)
      {
          useri.add(parsareUser(jsonArray.getJSONObject(i)));
      }
      return useri;
  }
  private static UserProfile parsareUser(JSONObject jsonObject) throws JSONException {
      String username = jsonObject.getString(USERNAME);
      String email = jsonObject.getString(EMAIL);
      String phone = jsonObject.getString(PHONE);
      String password = jsonObject.getString(PASSWORD);

      return new UserProfile(username, email, phone, password);
  }
}

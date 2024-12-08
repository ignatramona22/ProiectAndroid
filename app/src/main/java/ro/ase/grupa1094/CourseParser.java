package ro.ase.grupa1094;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseParser
{
  private static final String TITLE = "title";
  private static final String DESCRIPTION ="description";
  private static final String PRICE = "price";

  public static List<Course> parsareJSON(String json)
  {
      try {
          JSONArray jsonArray = new JSONArray(json);
          return parsareCursuri(jsonArray);
      } catch (JSONException e) {
          throw new RuntimeException(e);
      }
  }
  private static List<Course> parsareCursuri(JSONArray jsonArray) throws JSONException {
      List<Course> cursuri = new ArrayList<>();
      for(int i=0; i<jsonArray.length(); i++)
      {
          cursuri.add(parsareCurs(jsonArray.getJSONObject(i)));
      }
      return cursuri;
  }
  private static Course parsareCurs(JSONObject jsonObject) throws JSONException {
      String title = jsonObject.getString(TITLE);
      String description = jsonObject.getString(DESCRIPTION);
      float price = Float.parseFloat(jsonObject.getString(PRICE));
      return  new Course(title, description, price);
  }
}

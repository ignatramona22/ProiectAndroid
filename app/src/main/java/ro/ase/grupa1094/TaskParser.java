package ro.ase.grupa1094;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TaskParser
{
    private static final String TITLE = "title";
    private static final String DESCRIPTION =  "description";
    private static final String STATUS = "status";


   public static List<Task> parsareJSON(String json)
   {
       try {
           JSONArray jsonArray = new JSONArray(json);
           return parsareTaskuri(jsonArray);
       } catch (JSONException e) {
           throw new RuntimeException(e);
       }
   }
   private static List<Task> parsareTaskuri(JSONArray jsonArray) throws JSONException {
       List<Task> taskuri = new ArrayList<>();
       for (int i=0; i<jsonArray.length(); i++)
       {
           taskuri.add(parsareTask(jsonArray.getJSONObject(i)));
       }
       return taskuri;
   }
   private static Task parsareTask(JSONObject jsonObject) throws JSONException {
       String title = jsonObject.getString(TITLE);
       String description = jsonObject.getString(DESCRIPTION);
       Status status = Status.valueOf(jsonObject.getString(STATUS));

       return new Task(title, description, status);
   }
}

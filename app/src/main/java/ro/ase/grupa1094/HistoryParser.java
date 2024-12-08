package ro.ase.grupa1094;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryParser
{
   private static final String COURSE_TITLE =  "courseTitle";
   private static final String LESSON_TITLE = "lessonTitle";
   private static final String LAST_ACCESSED_DATE = "lastAccessedDate";
   private static final String PROGRESS = "progress";

   public static List<History> parsareJSON(String json)
   {
       try {
           JSONArray jsonArray = new JSONArray(json);
           return parsareIstorice(jsonArray);
       } catch (JSONException e) {
           throw new RuntimeException(e);
       }
   }
   private static List<History> parsareIstorice(JSONArray jsonArray) throws JSONException {
       List<History> historyList = new ArrayList<>();
       for(int i=0; i<jsonArray.length(); i++)
       {
           historyList.add(parsareIstoric(jsonArray.getJSONObject(i)));
       }
       return  historyList;
   }
   private static History parsareIstoric(JSONObject jsonObject) throws JSONException {
       String titleC = jsonObject.getString(COURSE_TITLE);
       String tittleL = jsonObject.getString(LESSON_TITLE);
       String date = jsonObject.getString(LAST_ACCESSED_DATE);
       int progress = jsonObject.getInt(PROGRESS);

       return  new History(titleC, tittleL, date, progress);
   }

}

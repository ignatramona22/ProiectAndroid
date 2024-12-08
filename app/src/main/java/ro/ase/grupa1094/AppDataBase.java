package ro.ase.grupa1094;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserProfile.class, Task.class, Details.class, Feedback.class, Course.class}, version = 5, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase
{
    public static AppDataBase instance;
    public abstract UserProfileDAO userProfileDao();
    public abstract  TaskDAO taskDAO();
    public abstract  DetailsDAO detailsDAO();
    public abstract  HomeDAO homeDAO();
    public static final String databaseName ="app.db";

    public  static AppDataBase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context, AppDataBase.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}

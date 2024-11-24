package ro.ase.grupa1094;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserProfile.class, Task.class, Details.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase
{
    public abstract UserProfileDAO userProfileDao();
    public abstract  TaskDAO taskDAO();
    public abstract  DetailsDAO detailsDAO();
}

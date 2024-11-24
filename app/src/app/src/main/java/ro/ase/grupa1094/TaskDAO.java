package ro.ase.grupa1094;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO
{
    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM Taskuri")
    List<Task> getAllTasks();

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);
}

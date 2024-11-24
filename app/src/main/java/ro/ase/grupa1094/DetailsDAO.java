package ro.ase.grupa1094;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DetailsDAO {
    @Insert
    void insertDetails(Details details);

    @Query("SELECT * FROM Details WHERE taskId = :taskId")
    List<Details> getDetailsByTaskId(int taskId);

    @Update
    void updateDetails(Details details);

    @Delete
    void deleteDetails(Details details);
}

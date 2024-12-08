package ro.ase.grupa1094;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HomeDAO
{
    @Insert
    void insertFeedback(Feedback feedback);

    @Insert
    void insertCourse(Course course);

    @Query("SELECT * FROM Feedback")
    List<Feedback> getAllFeedbacks();

    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();

    @Update
    void updateFeedback(Feedback feedback);

    @Update
    void updateCourse(Course course);
    @Delete
    void deleteFeedback(Feedback feedback);

    @Delete
    void  deleteCourse(Course course);
}

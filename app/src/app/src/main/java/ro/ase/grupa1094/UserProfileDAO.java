package ro.ase.grupa1094;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserProfileDAO {
    @Insert
    void insertUser(UserProfile user);

    @Query("SELECT * FROM Profile WHERE userId = :id")
    UserProfile getUserById(int id);

    @Update
    void updateUser(UserProfile user);

    @Delete
    void deleteUser(UserProfile user);

    @Query("DELETE FROM Profile WHERE username = :name")
    void deleteUserByName(String name);
}
package ro.ase.grupa1094;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="Details")
public class Details implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int detailId;
    private String taskData;
    private String additionalInfo;
    private int taskId;
    public Details(String taskData, String additionalInfo)
    {
        this.taskData = taskData;
        this.additionalInfo = additionalInfo;
    }


    public String getTaskData() {
        return taskData;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Task Data: " + taskData + "\nAdditional Info: " + additionalInfo;
    }
}

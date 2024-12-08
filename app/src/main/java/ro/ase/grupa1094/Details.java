package ro.ase.grupa1094;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "Details",
        foreignKeys = @ForeignKey(
                entity = Task.class,
                parentColumns = "taskId",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index(value = "taskId")}
)
public class Details implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int detailId;
    private String taskData;
    private String additionalInfo;
    private int taskId;
    public Details(String taskData, String additionalInfo, int taskId)
    {
        this.taskData = taskData;
        this.additionalInfo = additionalInfo;
        this.taskId = taskId;
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

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "Task Data: " + taskData + "\nAdditional Info: " + additionalInfo;
    }
}

package ro.ase.grupa1094;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

enum Status {Pending, InProgress, Completed}

@Entity(tableName="Taskuri")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int taskId;
    private String title;
    private String description;
    private Status status;

    public Task(String title, String description, Status status)
    {
        this.title = title;
        this.description = description;
        this.status = status;

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public String getTitle() { return title; }
    public String getDescription() { return description; }


    public Status getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

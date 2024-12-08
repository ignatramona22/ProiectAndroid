package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class TaskActivity extends AppCompatActivity {
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private Button saveTaskButton;
    private RadioGroup rgStatus;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        saveTaskButton = findViewById(R.id.saveTaskButton);
        rgStatus = findViewById(R.id.rgStareTask);

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("edit")) {
            isEditing = true;
            Task editTask = (Task) editIntent.getSerializableExtra("edit");
            taskTitleEditText.setText(editTask.getTitle());
            taskDescriptionEditText.setText(editTask.getDescription());

            switch (editTask.getStatus()) {
                case Pending:
                    rgStatus.check(R.id.rbPending);
                    break;
                case InProgress:
                    rgStatus.check(R.id.rbInProgress);
                    break;
                case Completed:
                    rgStatus.check(R.id.rbCompleted);
                    break;
            }
        }


        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();




        saveTaskButton.setOnClickListener(view -> {
            String title = taskTitleEditText.getText().toString().trim();
            String description = taskDescriptionEditText.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            Status status = getSelectedStatus();
            Task task = new Task(title, description, status);


            new Thread(() -> {
                AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "app.db").build();
                long taskId = db.taskDAO().insertTask(task);
                runOnUiThread(() -> {
                    Intent intent = new Intent(TaskActivity.this, DetailsActivity.class);
                    intent.putExtra("taskId", (int) taskId);
                    intent.putExtra("taskTitle", task.getTitle());
                    intent.putExtra("task", task);
                    startActivity(intent);

                    setResult(RESULT_OK, intent);
                    finish();
                });
            }).start();
        });
    }


    private Status getSelectedStatus() {
        int selectedStatusId = rgStatus.getCheckedRadioButtonId();
        if (selectedStatusId == R.id.rbPending) {
            return Status.Pending;
        } else if (selectedStatusId == R.id.rbInProgress) {
            return Status.InProgress;
        } else if (selectedStatusId == R.id.rbCompleted) {
            return Status.Completed;
        }
        return Status.Pending;
    }
}

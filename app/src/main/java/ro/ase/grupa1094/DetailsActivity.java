package ro.ase.grupa1094;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity
{
    private EditText additionalInfoEditText;
    private TextView taskDataTextView;
    private boolean isEditing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        taskDataTextView = findViewById(R.id.taskDataTextView);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);

        Button saveDetailsButton = findViewById(R.id.saveDetailsButton);

        Intent editIntent= getIntent();
        if(editIntent.hasExtra("edit"))
        {
            isEditing = true;
            Details editDetail= (Details) editIntent.getSerializableExtra("edit");
            taskDataTextView.setText(editDetail.getTaskData());
            additionalInfoEditText.setText(editDetail.getAdditionalInfo());

        }

        if (editIntent.hasExtra("taskTitle")) {
            String taskTitle = editIntent.getStringExtra("taskTitle");
            taskDataTextView.setText(taskTitle);
        }

        saveDetailsButton.setOnClickListener(view->{
            String taskData = taskDataTextView.getText().toString();
            String additionalInfo = additionalInfoEditText.getText().toString();
            int taskId = getIntent().getIntExtra("taskId", -1);
            if (taskId == -1) {
                Toast.makeText(this, "Invalid Task ID!", Toast.LENGTH_SHORT).show();
                return;
            }


            if (taskData.isEmpty() || additionalInfo.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            }

            Details details = new Details(taskData, additionalInfo, taskId);
            Toast.makeText(this, details.toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            if(isEditing)
            {
                intent.putExtra("edit", details);
                isEditing = false;
            }
            else {
                intent.putExtra("detail", details);
            }

            setResult(RESULT_OK, intent);
            finish();
        });
    }

}
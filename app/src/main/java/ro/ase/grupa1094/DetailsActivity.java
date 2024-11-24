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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        taskDataTextView = findViewById(R.id.taskDataTextView);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);

        Button saveDetailsButton = findViewById(R.id.saveDetailsButton);
        saveDetailsButton.setOnClickListener(v -> returnDetailsData());

        Intent editIntent= getIntent();
        if(editIntent.hasExtra("edit"))
        {
            Task editTask= (Task) editIntent.getSerializableExtra("edit");
            Toast.makeText(this, editTask.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void returnDetailsData() {
        String taskData = taskDataTextView.getText().toString();
        String aditionalInfo = additionalInfoEditText.getText().toString();

        Details detail = new Details(taskData, aditionalInfo);

        Toast.makeText(this, "Task details: " + detail.toString(), Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("detail", detail);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
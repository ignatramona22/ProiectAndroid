package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateCourseActivity extends AppCompatActivity {
    private EditText courseTitleEditText, courseDescriptionEditText, coursePriceEditText;
    private ImageView ivBackToHome;
    private boolean isEditing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        courseTitleEditText = findViewById(R.id.courseTitleEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        coursePriceEditText = findViewById(R.id.coursePriceEditText);
        ivBackToHome = findViewById(R.id.ivarrowBackToHome);

        ivBackToHome.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });

        Button saveButton = findViewById(R.id.saveButton);
        Intent editIntent = getIntent();
        if(editIntent.hasExtra("edit"))
        {
            isEditing = true;
            Course editCourse = (Course) editIntent.getSerializableExtra("edit");
            courseTitleEditText.setText(editCourse.getTitle());
            courseDescriptionEditText.setText(editCourse.getDescription());
            coursePriceEditText.setText(String.valueOf(editCourse.getPrice()));

        }

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        saveButton.setOnClickListener(view -> {
            String courseTitle = courseTitleEditText.getText().toString();
            String courseDescription = courseDescriptionEditText.getText().toString();
            String coursePriceStr = coursePriceEditText.getText().toString();
            float coursePrice = Float.parseFloat(coursePriceStr);

            if (courseTitle.isEmpty() || courseDescription.isEmpty() || coursePriceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Course course = new Course(courseTitle, courseDescription, coursePrice);
            Intent resultIntent = getIntent();
            if(isEditing)
            {
                resultIntent.putExtra("edit", course);
            } else {
                resultIntent.putExtra("course", course);
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

}

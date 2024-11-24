package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText feedbackEditText;
    private RatingBar ratingBar;

    private boolean isEditing = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        usernameEditText = findViewById(R.id.usernameEditText);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        ratingBar = findViewById(R.id.ratingBar);

        Button submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        Intent editIntent = getIntent();
        if(editIntent.hasExtra("edit"))
        {
            isEditing= true;
            Feedback editFeedback = (Feedback) editIntent.getSerializableExtra("edit");
            usernameEditText.setText(editFeedback.getUsername());
            feedbackEditText.setText(editFeedback.getFeedbackText());
            ratingBar.setRating(editFeedback.getRating());


        }

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        submitFeedbackButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String feedbackText = feedbackEditText.getText().toString();
            int rating = (int) ratingBar.getRating();

            if (username.isEmpty() || feedbackText.isEmpty() || rating == 0) {
                Toast.makeText(this, "Please fill all fields and provide a rating", Toast.LENGTH_SHORT).show();
                return;
            }


            Feedback feedback = new Feedback(username, feedbackText, rating);

            Intent resultIntent = getIntent();
            if(isEditing)
            {
                resultIntent.putExtra("edit", feedback);
            } else {
                resultIntent.putExtra("feedback", feedback);
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }



}

package ro.ase.grupa1094;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GeographyCourseActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView back;
    TextView textViewIntroduction, textViewOutro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geography_course);
        videoView = findViewById(R.id.videoGeography);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.geography));
        videoView.start();

        TextView textViewDescription = findViewById(R.id.tvDescriptionG);
        back = findViewById(R.id.ivarrow);

        textViewIntroduction = findViewById(R.id.textViewIntroductionG);
        textViewOutro = findViewById(R.id.textViewOutroG);

        textViewIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 0;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });


        textViewOutro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (4 * 60 * 1000) + (50 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });


        textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GeographyCourseActivity.this);
                builder.setTitle("Course Description");
                builder.setMessage("Embark on a captivating journey with begrepen.be as we unravel the tapestry of Europe! " +
                        "In this enlightening video, you'll discover the capitals of diverse European nations, each boasting its " +
                        "own unique language, culture, flag, and anthem. Immerse yourself in the rich diversity of Europe and gain " +
                        "insights into the geographical heart of each nation. Whether you're a travel enthusiast, culture lover, or " +
                        "just curious about the continent, this video promises an educational and visual experience.");
                builder.setPositiveButton("Close", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeographyCourseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

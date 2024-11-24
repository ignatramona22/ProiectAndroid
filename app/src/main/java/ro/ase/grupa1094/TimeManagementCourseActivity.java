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

public class TimeManagementCourseActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView back;
    TextView textViewIntroductionTM,  textViewAlgorithm, textViewLinux, textViewInterrupts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_management_course);
        videoView = findViewById(R.id.videoTM);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tmvideo));
        videoView.start();

        TextView textViewDescription = findViewById(R.id.tvDescriptionTM);
        back = findViewById(R.id.ivarrow);

        textViewIntroductionTM = findViewById(R.id.textViewIntroductionTM);
        textViewInterrupts = findViewById(R.id.textViewInterrupts);
        textViewAlgorithm = findViewById(R.id.textViewAlgoritm);
        textViewLinux = findViewById(R.id.textViewLinux);

        textViewIntroductionTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 0;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }

        });

        textViewAlgorithm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (1 * 60 * 1000) + (06 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewLinux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (1 * 60 * 1000) + (54 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });


        textViewInterrupts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (2 * 60 * 1000) + (50 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });


        textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TimeManagementCourseActivity.this);
                builder.setTitle("Course Description");
                builder.setMessage("Human beings and computers alike share the challenge of how to get as much done as possible in a limited time. " +
                        "Over the last fifty or so years, computer scientists have learned a lot of good strategies for managing time effectively —" +
                        " and they have a lot of experience with what can go wrong. Brian Christian shares how we can use some of these insights to help make " +
                        "the most of our own lives.");
                builder.setPositiveButton("Close", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeManagementCourseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
package ro.ase.grupa1094;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MathCourseActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView back;
    TextView textViewIntroduction, textViewHistoryOfMath, textViewModernMath, textViewNumbers, textViewGroupTheory, textViewGeometry,
            textViewChanges, textViewAppliedMath, textViewPhysics, textViewCS, textViewFoundationMath, textViewOutro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_course);

        videoView = findViewById(R.id.videoMath);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mathvideo));
        videoView.start();

        TextView textViewDescription = findViewById(R.id.textViewDescription);
        back = findViewById(R.id.ivarrow);

        textViewIntroduction= findViewById(R.id.textViewIntroduction);
        textViewHistoryOfMath= findViewById(R.id.textViewHistoryOfMath);
        textViewModernMath= findViewById(R.id.textViewModernMath);
        textViewNumbers = findViewById(R.id.textViewNumbers);
        textViewGroupTheory = findViewById(R.id.textViewGroupTheory);
        textViewGeometry= findViewById(R.id.textViewGeometry);
        textViewChanges= findViewById(R.id.textViewChanges);
        textViewAppliedMath= findViewById(R.id.textViewAppliedMath);
        textViewPhysics = findViewById(R.id.textViewPhysics);
        textViewCS = findViewById(R.id.textViewCS);
        textViewFoundationMath = findViewById(R.id.textViewFoundationMath);
        textViewOutro = findViewById(R.id.textViewOutro);




        textViewIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 0;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewHistoryOfMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 0 * 60 * 1000 + 15 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewModernMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 1 * 60 * 1000 + 05 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 2 * 60 * 1000 + 22 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewGroupTheory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 3 * 60 * 1000 + 27 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewGeometry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 4 * 60 * 1000 + 10 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 5 * 60 * 1000 + 12 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewAppliedMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 5 * 60 * 1000 + 52 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewPhysics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 6 * 60 * 1000 + 9 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 7 * 60 * 1000 + 55 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewFoundationMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 8 * 60 * 1000 + 48 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewOutro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 10 * 60 * 1000 + 38 * 1000;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MathCourseActivity.this);
                builder.setTitle("Course Description");

                builder.setMessage("The entire field of mathematics summarised in a single map! "
                        + "This shows how pure mathematics and applied mathematics relate to each other and all of the sub-topics they are made from.");

                builder.setPositiveButton("Close", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathCourseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

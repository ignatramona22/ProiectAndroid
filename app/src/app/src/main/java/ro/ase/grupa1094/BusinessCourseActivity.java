package ro.ase.grupa1094;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BusinessCourseActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView back;
    TextView textViewIntroduction, textViewDefinition, textViewGoodsServices, textViewHybrids, textViewProfit,
            textViewRevenue, textViewProfitability, textViewEarnings, textViewBeneficial, textViewEarnRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_course);
        videoView = findViewById(R.id.videoBusiness);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.business));
        videoView.start();

        TextView textViewDescription = findViewById(R.id.tvDescriptionBusiness);
        back = findViewById(R.id.ivarrow);

        textViewIntroduction = findViewById(R.id.textViewIntroductionBusiness);
        textViewDefinition = findViewById(R.id.textViewDefinition);
        textViewGoodsServices = findViewById(R.id.textViewGoodsServices);
        textViewHybrids = findViewById(R.id.textViewHybrids);
        textViewProfit = findViewById(R.id.textViewProfit);
        textViewRevenue = findViewById(R.id.textViewRevenue);
        textViewProfitability = findViewById(R.id.textViewProfitability);
        textViewEarnings = findViewById(R.id.textViewEarnings);
        textViewBeneficial = findViewById(R.id.textViewBeneficial);
        textViewEarnRevenue = findViewById(R.id.textViewEarnRevenue);

        textViewIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = 0;
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });

        textViewDefinition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (0 * 60 * 1000) + (45 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewGoodsServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (1 * 60 * 1000) + (45 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });


        textViewHybrids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (3 * 60 * 1000) + (10 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (3 * 60 * 1000) + (45 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (4 * 60 * 1000) + (30 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewProfitability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (8 * 60 * 1000) + (10 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (9 * 60 * 1000) + (40 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewBeneficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (10 * 60 * 1000) + (20 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();
            }
        });
        textViewEarnRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeInMillis = (12 * 60 * 1000) + (0 * 1000);
                videoView.seekTo(timeInMillis);
                videoView.start();

            }
        });
        textViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessCourseActivity.this);
                builder.setTitle("Course Description");
                builder.setMessage("In this lecture, I'll answer the question: What is a business? Dop answer this question will breakdown what " +
                        "separates a business from other organizations as a concept necessary for free-market businesses to thrive: " +
                        "the mutually beneficial exchange. Enjoy!");
                builder.setPositiveButton("Close", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessCourseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
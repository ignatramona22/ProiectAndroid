package ro.ase.grupa1094;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseAvatarActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);

        ImageView avatar1 = findViewById(R.id.avatar1);
        ImageView avatar2 = findViewById(R.id.avatar2);
        ImageView avatar3 = findViewById(R.id.avatar3);
        ImageView avatar4 = findViewById(R.id.avatar4);
        ImageView avatar5 = findViewById(R.id.avatar5);
        ImageView avatar6 = findViewById(R.id.avatar6);
        ImageView avatar7 = findViewById(R.id.avatar7);
        ImageView avatar8 = findViewById(R.id.avatar8);
        ImageView avatar9 = findViewById(R.id.avatar9);
        ImageView avatar10 = findViewById(R.id.avatar10);
        ImageView avatar11 = findViewById(R.id.avatar11);
        ImageView avatar12 = findViewById(R.id.avatar12);
        ImageView avatar13 = findViewById(R.id.avatar13);
        ImageView avatar14 = findViewById(R.id.avatar14);
        ImageView avatar15 = findViewById(R.id.avatar15);
        ImageView avatar16 = findViewById(R.id.avatar16);
        ImageView avatar17 = findViewById(R.id.avatar17);
        ImageView avatar18 = findViewById(R.id.avatar18);
        ImageView avatar19 = findViewById(R.id.avatar19);
        ImageView avatar20 = findViewById(R.id.avatar20);



        avatar1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar1);
            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar2);
            }
        });


        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar3);
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar4);
            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar5);
            }
        });

        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar6);
            }
        });


        avatar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar7);
            }
        });

        avatar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar8);
            }
        });


        avatar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar9);
            }
        });

        avatar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar10);
            }
        });

        avatar11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar11);
            }
        });

        avatar12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar12);
            }
        });

        avatar13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar13);
            }
        });

        avatar14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar14);
            }
        });

        avatar15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar15);
            }
        });

        avatar16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar16);
            }
        });

        avatar17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar17);
            }
        });

        avatar18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar18);
            }
        });

        avatar19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar19);
            }
        });

        avatar20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(R.drawable.avatar20);
            }
        });



    }
    private void sendResult(int avatarResource) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("avatar", avatarResource);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
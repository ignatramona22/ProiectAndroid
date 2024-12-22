package ro.ase.grupa1094;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView ivBackToProfile;
    List<UserProfile> userProfileList = new ArrayList<>();
    private static final String urlUsers = "https://www.jsonkeeper.com/b/Z8M4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        initComponents();
        incarcareUseriDinRetea();

    }

    private void incarcareUseriDinRetea()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlUsers);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(()->{
                    preluareUseriDinJson(rezultat);
                });
            }
        };
        thread.start();
    }
    private void preluareUseriDinJson(String json)
    {
        userProfileList.addAll(UserParser.parsareJSON(json));
        UsersAdapter adapter = (UsersAdapter) recyclerView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponents() {
        ivBackToProfile = findViewById(R.id.ivBackToProfile);
        ivBackToProfile.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UsersAdapter adapter = new UsersAdapter(this, userProfileList);
        recyclerView.setAdapter(adapter);
    }

}
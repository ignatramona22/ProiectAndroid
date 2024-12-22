package ro.ase.grupa1094;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView ivBackToHome;
    private List<History> historyList = new ArrayList<>();
    private static String urlHistory = "https://www.jsonkeeper.com/b/LOXH";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initComponenteHistory();
        incarcareHistoryDinRetea();

    }

    private void incarcareHistoryDinRetea()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlHistory);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(()->{
                    preluareHistoryDinJSON(rezultat);
                });
            }
        };
        thread.start();
    }

    private void preluareHistoryDinJSON(String json)
    {
        historyList.addAll(HistoryParser.parsareJSON(json));
        HistoryAdapter adapter =(HistoryAdapter) recyclerView.getAdapter();
        adapter.notifyDataSetChanged();
    }
    private void initComponenteHistory()
    {
        ivBackToHome = findViewById(R.id.ivarrowBackToHomeActivity);
        ivBackToHome.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter adapter = new HistoryAdapter(this, historyList);
        recyclerView.setAdapter(adapter);
    }
}

package ro.ase.grupa1094;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyList = loadHistoryData();
        historyAdapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(historyAdapter);
    }

    private List<History> loadHistoryData() {
        List<History> list = new ArrayList<>();
        list.add(new History("Mathematics", "The Map of Mathematics", "10-10-2024", 75));
        list.add(new History("Geography", "Capitals", "09-10-2024", 50));
        list.add(new History("Time management", "Benefits of Being Organised", "08-10-2024", 100));
        list.add(new History("Business", "Resilient Leadership", "08-10-2024", 55));
        return list;
    }
}

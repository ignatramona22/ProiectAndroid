package ro.ase.grupa1094;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private List<Course> courseList;
   private ItemAdapter itemAdapter;
   private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.svSearch);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        courseList = new ArrayList<>();

        courseList.add(new Course("Mathematics", "Learn algebra and calculus.", 19.99F));
        courseList.add(new Course("Geography", "Explore the world's geography.", 15.99F));
        courseList.add(new Course("Time Management", "Master your time.", 12.99F));
        courseList.add(new Course("Business", "Learn the basics of business.", 29.99F));


        itemAdapter = new ItemAdapter(SearchActivity.this, courseList);
        recyclerView.setAdapter(itemAdapter);

    }

    private void searchList(String text){
        List<Course> dataSearchList = new ArrayList<>();
        for (Course data : courseList){
            if (data.getTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            itemAdapter.setSearchList(dataSearchList);
        }
    }
}

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText searchEditText;
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    List<Course> courseList;
    List<Course> filteredCourses;
    ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerView);
        arrowBack = findViewById(R.id.ivarrow);

        courseList = new ArrayList<>();
        courseList.add(new Course("Mathematics", "Learn algebra and calculus.", 19.99F));
        courseList.add(new Course("Geography", "Explore the world's geography.", 15.99F));
        courseList.add(new Course("Time Management", "Master your time.", 12.99F));
        courseList.add(new Course("Business", "Learn the basics of business.", 29.99F));
        courseList.add(new Course("History", "Discover past events.", 14.99F));

        filteredCourses = new ArrayList<>(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(this, R.layout.view_course, filteredCourses, getLayoutInflater());
      //  recyclerView.setAdapter(courseAdapter);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterCourses(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "You're back on the home page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void filterCourses(String query) {
        filteredCourses.clear();
        for (Course course : courseList) {
            if (course.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredCourses.add(course);
            }
        }
        courseAdapter.notifyDataSetChanged();
    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}

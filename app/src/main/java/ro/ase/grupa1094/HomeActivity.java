package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
{
    ImageView arrow;
    TextView title;
    TextView maths;
    TextView geography;
    TextView timeManagement;
    TextView business;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton createCourseButton;

    ListView lvMathFeedback;
    ListView lvAddCourse;
    private int pozitieMathInLista;
    private int pozitieCourseInLista;

    private static final int CREATE_COURSE_REQUEST = 1;
    private TextView courseListTextView;

    private static final int FEEDBACK_REQUEST = 2;

    private Button btnMath;

    ActivityResultLauncher<Intent> launcherM;
    ActivityResultLauncher<Intent> launcherC;

    private ArrayList<Feedback> feedbackListM = new ArrayList<>();
    private ArrayList<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        title=findViewById(R.id.tvdash);
        arrow=findViewById(R.id.ivarrow);
        maths = findViewById(R.id.tvMath);
        geography = findViewById(R.id.tvGeography);
        timeManagement = findViewById(R.id.tvTimeManagement);
        business = findViewById(R.id.tvBusiness);
        createCourseButton = findViewById(R.id.createCourseButton);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        courseListTextView = findViewById(R.id.courseListTextView);
        createCourseButton = findViewById(R.id.createCourseButton);


        lvMathFeedback = findViewById(R.id.lvMathFeedback);
        lvAddCourse = findViewById(R.id.lvAddCourse);


        btnMath= findViewById(R.id.btnMathFeedback);

        createCourseButton.setOnClickListener(v -> openCreateCourseActivity());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nvg_home) {
                    Toast.makeText(HomeActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                    return true;
                } else if (id == R.id.navigation_search) {
                    Toast.makeText(HomeActivity.this, "Search Selected", Toast.LENGTH_SHORT).show();
                    Intent intentSearch = new Intent(HomeActivity.this, SearchActivity.class);
                    startActivity(intentSearch);
                    return true;
                } else if (id == R.id.navigation_history) {
                    Toast.makeText(HomeActivity.this, "History Selected", Toast.LENGTH_SHORT).show();
                    Intent intentHistory = new Intent(HomeActivity.this, HistoryActivity.class);
                    startActivity(intentHistory);
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Toast.makeText(HomeActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                    Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intentProfile);
                    return true;
                }

                return false;
            }
        });


        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "You're back in the login page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Your courses", Toast.LENGTH_SHORT).show();
            }
        });

        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "WOW", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MathCourseActivity.class);
                startActivity(intent);
            }
        });

        geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "It's time to travel!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, GeographyCourseActivity.class);
                startActivity(intent);

            }
        });

        timeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Great choice", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, TimeManagementCourseActivity.class);
                startActivity(intent);
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "WOW", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, BusinessCourseActivity.class);
                startActivity(intent);
            }
        });


        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                intent.putExtra("course", "math");
                launcherM.launch(intent);
            }
        });



        lvMathFeedback.setOnItemClickListener((adapterView, view, poition, l)->{
            pozitieMathInLista = poition;
            Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
            intent.putExtra("edit", feedbackListM.get(pozitieMathInLista));
            launcherM.launch(intent);
        });

        launcherM = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getData().hasExtra("feedback"))
            {
                Intent intent = result.getData();
                Feedback feedback = (Feedback) intent.getSerializableExtra("feedback");
                if(feedback!=null)
                {
                    feedbackListM.add(feedback);
                }

                FeedbackAdapter adapter =  new FeedbackAdapter(this, R.layout.view_feedback, feedbackListM, getLayoutInflater());
                lvMathFeedback.setAdapter(adapter);
            } else if(result.getData().hasExtra("edit"))
            {
                Intent intent = result.getData();
                Feedback feedback = (Feedback) intent.getSerializableExtra("edit");
                if(feedback!=null)
                {
                    Feedback editedFeedback= feedbackListM.get(pozitieMathInLista);
                    editedFeedback.setUsername(feedback.getUsername());
                    editedFeedback.setFeedbackText(feedback.getFeedbackText());
                    editedFeedback.setRating(feedback.getRating());

                    FeedbackAdapter adapter = (FeedbackAdapter) lvMathFeedback.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", "token1234");
        editor.apply();

        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();


        lvAddCourse.setOnItemClickListener((adapterView, view, pozition, l)->{
            pozitieCourseInLista = pozition;
            Intent intent = new Intent(getApplicationContext(), CreateCourseActivity.class);
            intent.putExtra("edit", courseList.get(pozitieCourseInLista));
            launcherC.launch(intent);
        });

        launcherC= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getData().hasExtra("course"))
            {
                Intent intent = result.getData();
                Course course = (Course) intent.getSerializableExtra("course");
                if(course!=null)
                {
                    courseList.add(course);
                }
                CourseAdapter adapter = new CourseAdapter(this, R.layout.view_course, courseList, getLayoutInflater());
                lvAddCourse.setAdapter(adapter);
            } else if(result.getData().hasExtra("edit"))
            {
                Intent intent = result.getData();
                Course course = (Course) intent.getSerializableExtra("edit");
                if(course!=null)
                {
                    Course editedCourse = courseList.get(pozitieCourseInLista);
                    editedCourse.setTitle(course.getTitle());
                    editedCourse.setDescription(course.getDescription());
                    editedCourse.setPrice(course.getPrice());

                    CourseAdapter adapter = (CourseAdapter) lvAddCourse.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void openFeedbackActivity() {
        Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
        launcherM.launch(intent);
    }

    private void openCreateCourseActivity() {
        Intent intent = new Intent(HomeActivity.this, CreateCourseActivity.class);
        launcherC.launch(intent);
    }



    public void openMainActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    RecyclerView rvAddCourse;
    private int pozitieMathInLista;
    private int pozitieCourseInLista;

    private Button btnMath;

    ActivityResultLauncher<Intent> launcherM;
    ActivityResultLauncher<Intent> launcherC;

    private List<Feedback> feedbackListM = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    AppDataBase db;

    private static String urlCourses= "https://www.jsonkeeper.com/b/3KRM";

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


        createCourseButton = findViewById(R.id.createCourseButton);


        lvMathFeedback = findViewById(R.id.lvMathFeedback);



        btnMath= findViewById(R.id.btnMathFeedback);

        db = AppDataBase.getInstance(getApplicationContext());

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


        new Thread(()->{
            feedbackListM = db.homeDAO().getAllFeedbacks();
            runOnUiThread(()->{
                FeedbackAdapter adapter =  new FeedbackAdapter(this, R.layout.view_feedback, feedbackListM, getLayoutInflater());
                lvMathFeedback.setAdapter(adapter);
            });
        }).start();

        lvMathFeedback.setOnItemClickListener((adapterView, view, poition, l)->{
            pozitieMathInLista = poition;
            Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
            intent.putExtra("edit", feedbackListM.get(pozitieMathInLista));
            launcherM.launch(intent);
        });

        lvMathFeedback.setOnItemLongClickListener((adapterView, view, position, l)->{
            pozitieMathInLista = position;
            Feedback feedbackDeSters = feedbackListM.get(position);

            new Thread(()->{
                db.homeDAO().deleteFeedback(feedbackDeSters);
                runOnUiThread(()->{
                    feedbackListM.remove(position);
                    FeedbackAdapter adapter = (FeedbackAdapter) lvMathFeedback.getAdapter();
                    adapter.notifyDataSetChanged();
                });
            }).start();
          return  true;
        });

        launcherM = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getData().hasExtra("feedback"))
            {
                Intent intent = result.getData();
                Feedback feedback = (Feedback) intent.getSerializableExtra("feedback");
                if(feedback!=null)
                {
                    new Thread(()->{
                        db.homeDAO().insertFeedback(feedback);
                        runOnUiThread(()->{
                            feedbackListM.add(feedback);
                            FeedbackAdapter adapter = (FeedbackAdapter) lvMathFeedback.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    }).start();
                }
            }
            else if(result.getData().hasExtra("edit"))
            {
                Intent intent = result.getData();
                Feedback feedback = (Feedback) intent.getSerializableExtra("edit");
                if(feedback!=null)
                {
                    Feedback editedFeedback= feedbackListM.get(pozitieMathInLista);
                    new Thread(()->{
                        db.homeDAO().updateFeedback(feedback);
                        runOnUiThread(()->{
                            editedFeedback.setUsername(feedback.getUsername());
                            editedFeedback.setFeedbackText(feedback.getFeedbackText());
                            editedFeedback.setRating(feedback.getRating());

                            FeedbackAdapter adapter = (FeedbackAdapter) lvMathFeedback.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    }).start();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", "token1234");
        editor.apply();

        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        initComponenteCursNou();
        incarcareCursuriDinRetea();

        launcherC= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            assert result.getData() != null;
            if(result.getData().hasExtra("course"))
            {
                Intent intent = result.getData();
                Course course = (Course) intent.getSerializableExtra("course");
                if(course!=null)
                {
                   new Thread(()->{
                       db.homeDAO().insertCourse(course);
                       runOnUiThread(()->{
                           courseList.add(course);
                           CourseAdapter adapter = (CourseAdapter) rvAddCourse.getAdapter();
                           adapter.notifyDataSetChanged();
                       });
                   }).start();
                }
            }
            else if(result.getData().hasExtra("edit"))
            {
                Intent intent = result.getData();
                Course course = (Course) intent.getSerializableExtra("edit");
                if(course!=null)
                {
                    Course editedCourse = courseList.get(pozitieCourseInLista);
                   new Thread(()->{
                       db.homeDAO().updateCourse(course);
                       runOnUiThread(()->{
                           editedCourse.setTitle(course.getTitle());
                           editedCourse.setDescription(course.getDescription());
                           editedCourse.setPrice(course.getPrice());

                           CourseAdapter adapter = (CourseAdapter) rvAddCourse.getAdapter();
                           adapter.notifyDataSetChanged();
                       });
                   }).start();
                }
            }
        });

    }



    private void openCreateCourseActivity() {
        Intent intent = new Intent(HomeActivity.this, CreateCourseActivity.class);
        launcherC.launch(intent);
    }



    public void openMainActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void incarcareCursuriDinRetea()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlCourses);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(()->{
                    preluareCursuriDinJSON(rezultat);
                });
            }
        };
        thread.start();
    }
    private void preluareCursuriDinJSON(String json)
    {
        courseList.addAll(CourseParser.parsareJSON(json));
        CourseAdapter adapter = (CourseAdapter) rvAddCourse.getAdapter();
        adapter.notifyDataSetChanged();
    }
    private void initComponenteCursNou() {
        rvAddCourse = findViewById(R.id.rvAddCourse);
        rvAddCourse.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter adapter = new CourseAdapter(this, courseList,
                new CourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Course course, int position) {
                        pozitieCourseInLista = position;
                        Intent intent = new Intent(HomeActivity.this, CreateCourseActivity.class);
                        intent.putExtra("edit", courseList.get(pozitieCourseInLista));
                        launcherC.launch(intent);
                    }
                },
                new CourseAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(Course course, int position) {
                        pozitieCourseInLista = position;
                        Course courseDeSters = courseList.get(position);
                        new Thread(()->{
                            db.homeDAO().deleteCourse(courseDeSters);
                            runOnUiThread(()->{
                                courseList.remove(position);
                                CourseAdapter adapter = (CourseAdapter) rvAddCourse.getAdapter();
                                adapter.notifyDataSetChanged();
                            });
                        }).start();
                    }
                });

        rvAddCourse.setAdapter(adapter);
    }


}
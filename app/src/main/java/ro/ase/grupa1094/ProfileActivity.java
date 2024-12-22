package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity
{
    private EditText etUsername, etEmail, etPhone, etPassword;

    private ImageView profilePicture;
    TextView changePicture;
    CustomButton customButton;
    Button createTaskBtn;
    ImageView arrowBackToHome;
    ListView lvCreateTask;
    ListView lvTaskDetail;
    List<Task> taskList = new ArrayList<>();
    List<Details> detailsList = new ArrayList<>();
    ActivityResultLauncher<Intent> taskLauncher;
    ActivityResultLauncher<Intent> detailsLauncher;
    private int pozitieTaskInLinsta;
    private int pozitieDetailInLinsta;
    private AppDataBase db;
    private static String urlTask = "https://www.jsonkeeper.com/b/3CTX";
    ImageView ivPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        customButton = findViewById(R.id.custom_button);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        profilePicture = findViewById(R.id.profile_picture);
        changePicture = findViewById(R.id.change_picture);
        arrowBackToHome = findViewById(R.id.btn_back);
        lvTaskDetail = findViewById(R.id.lvtaskDetails);
        createTaskBtn = findViewById(R.id.createTaskButton);
        ivPayment = findViewById(R.id.ivPayment);
        loadUserData();

        db = AppDataBase.getInstance(getApplicationContext());

        ImageView imgShare = findViewById(R.id.btn_share);
        imgShare.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AllUsersActivity.class);
            startActivity(intent);
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskActivity();
            }
        });

        arrowBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "You're back in home page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChooseAvatarActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        ivPayment.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), CoursePaymentActivity.class);
            startActivity(intent);
        });

        initComponenteTask();
        incarcareTaskDinRetea();

        new Thread(() -> {
            taskList = db.taskDAO().getAllTasks();
            runOnUiThread(() -> {
                TaskAdapter adapter = new TaskAdapter(this, R.layout.view_task, taskList, getLayoutInflater());
                lvCreateTask.setAdapter(adapter);
            });
        }).start();

        lvCreateTask.setOnItemClickListener((adapterView, view, pozition, l) -> {
            pozitieTaskInLinsta = pozition;
            Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
            intent.putExtra("edit", taskList.get(pozitieTaskInLinsta));
            taskLauncher.launch(intent);

        });


        lvCreateTask.setOnItemLongClickListener((adapterView, view, position, l) -> {
            pozitieTaskInLinsta = position;
            Task taskPtSters = taskList.get(position);
            new Thread(() -> {
                db.taskDAO().deleteTask(taskPtSters);
                runOnUiThread(() -> {
                    taskList.remove(position);
                    TaskAdapter adapter = (TaskAdapter) lvCreateTask.getAdapter();
                    adapter.notifyDataSetChanged();
                });
            }).start();
            return true;
        });

        taskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData().hasExtra("task")) {
                Intent intent = result.getData();
                Task task = (Task) intent.getSerializableExtra("task");
                if (task != null) {
                    new Thread(() -> {
                        db.taskDAO().insertTask(task);
                        runOnUiThread(() -> {
                            taskList.add(task);
                            TaskAdapter adapter = (TaskAdapter) lvCreateTask.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    }).start();

                }
            } else if (result.getData().hasExtra("edit")) {
                Intent intent = result.getData();
                Task task = (Task) intent.getSerializableExtra("edit");
                if (task != null) {
                    new Thread(() -> {
                        Task editTask = taskList.get(pozitieTaskInLinsta);
                        db.taskDAO().updateTask(task);
                        runOnUiThread(() -> {
                            editTask.setTitle(task.getTitle());
                            editTask.setDescription(task.getDescription());
                            editTask.setStatus(task.getStatus());
                            TaskAdapter adapter = (TaskAdapter) lvCreateTask.getAdapter();
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


        lvTaskDetail.setOnItemClickListener((adapterView, view, pozition, l) -> {
            pozitieDetailInLinsta = pozition;
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("edit", detailsList.get(pozitieDetailInLinsta));
            detailsLauncher.launch(intent);

        });

        new Thread(() -> {
            detailsList = db.detailsDAO().getAllDetails();
            runOnUiThread(() -> {
                DetailsAdapter adapter = new DetailsAdapter(getApplicationContext(), R.layout.view_detail, detailsList, getLayoutInflater());
                lvTaskDetail.setAdapter(adapter);
            });
        }).start();

        detailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData().hasExtra("detail")) {
                Intent intent = result.getData();
                Details detail = (Details) intent.getSerializableExtra("detail");
                if (detail != null) {
                    new Thread(() -> {
                        db.detailsDAO().insertDetails(detail);
                        runOnUiThread(() -> {
                            detailsList.add(detail);
                            DetailsAdapter adapter = (DetailsAdapter) lvTaskDetail.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    });
                }
            } else if (result.getData().hasExtra("edit")) {
                Intent intent = result.getData();
                Details detail = (Details) intent.getSerializableExtra("edit");
                if (detail != null) {
                    new Thread(() -> {
                        Details editDetails = detailsList.get(pozitieDetailInLinsta);
                        db.detailsDAO().updateDetails(detail);
                        runOnUiThread(() -> {
                            editDetails.setTaskData(detail.getTaskData());
                            editDetails.setAdditionalInfo(detail.getAdditionalInfo());
                            DetailsAdapter adapter = (DetailsAdapter) lvTaskDetail.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    }).start();
                }
            }
        });

    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    private void openTaskActivity() {
        Intent intent = new Intent(ProfileActivity.this,  TaskActivity.class);
        taskLauncher.launch(intent);
    }


    private void updateUserData() {

        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email");
            return;
        }


        if (phone.length() > 10) {
            etPhone.setError("Invalid phone number");
            return;
        }

        saveUserData(username, email, phone, password);
    }

    private void saveUserData(String username, String email, String phone, String password)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("password", password);

        editor.apply();

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        etUsername.setText(sharedPreferences.getString("username", ""));
        etEmail.setText(sharedPreferences.getString("email", ""));
        etPhone.setText(sharedPreferences.getString("phone", ""));
        etPassword.setText(sharedPreferences.getString("password", ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int avatarResource = data.getIntExtra("avatar", R.drawable.avatar8);
            profilePicture.setImageResource(avatarResource);
        }
    }


    private void incarcareTaskDinRetea()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager httpsManager = new HttpsManager(urlTask);
                String rezultat = httpsManager.procesare();
                new Handler(getMainLooper()).post(()->{
                    preluareTaskDinJSON(rezultat);
                });
            }
        };
        thread.start();
    }
    private void preluareTaskDinJSON(String json)
    {
        taskList.addAll(TaskParser.parsareJSON(json));
        TaskAdapter adapter = (TaskAdapter) lvCreateTask.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponenteTask()
    {
        lvCreateTask = findViewById(R.id.lvCreateTask);
        TaskAdapter adapter = new TaskAdapter(getApplicationContext(), R.layout.view_task, taskList, getLayoutInflater());
        lvCreateTask.setAdapter(adapter);
    }


}
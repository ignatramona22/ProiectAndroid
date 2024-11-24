package ro.ase.grupa1094;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    Button addTaskDetailBtn;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        customButton = findViewById(R.id.custom_button);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        profilePicture=findViewById(R.id.profile_picture);
        changePicture=findViewById(R.id.change_picture);
        arrowBackToHome=findViewById(R.id.btn_back);
        lvCreateTask = findViewById(R.id.lvCreateTask);
        lvTaskDetail = findViewById(R.id.lvtaskDetails);
        createTaskBtn = findViewById(R.id.createTaskButton);
        addTaskDetailBtn = findViewById(R.id.viewDetailsButton);
        loadUserData();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "app-database").build();


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

        addTaskDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailsActivity();
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

        lvCreateTask.setOnItemClickListener((adapterView, view, pozition, l)->{
            pozitieTaskInLinsta = pozition;
            Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
            intent.putExtra("edit", taskList.get(pozitieTaskInLinsta));
            taskLauncher.launch(intent);

        });

        taskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData().hasExtra("task")) {
                Intent intent = result.getData();
                Task task = (Task) intent.getSerializableExtra("task");
                if(task!=null)
                {
                    new Thread(() -> {
                        db.taskDAO().insertTask(task);
                        runOnUiThread(() -> {
                            taskList.add(task);
                            TaskAdapter adapter = new TaskAdapter(this, R.layout.view_task, taskList, getLayoutInflater());
                            lvCreateTask.setAdapter(adapter);
                        });
                    }).start();
                    new Thread(() -> {
                        taskList = db.taskDAO().getAllTasks();
                        runOnUiThread(() -> {
                            TaskAdapter adapter = new TaskAdapter(this, R.layout.view_task, taskList, getLayoutInflater());
                            lvCreateTask.setAdapter(adapter);
                        });
                    }).start();
                }
//                TaskAdapter adapter = new TaskAdapter(this, R.layout.view_task, taskList, getLayoutInflater());
//                lvCreateTask.setAdapter(adapter);
            } else if(result.getData().hasExtra("edit"))
            {
                Intent intent = result.getData();
                Task task = (Task) intent.getSerializableExtra("edit");
                if(task !=null)
                {
                    Task editTask = taskList.get(pozitieTaskInLinsta);
                    editTask.setTitle(task.getTitle());
                    editTask.setDescription(task.getDescription());
                    editTask.setStatus(task.getStatus());

                    TaskAdapter adapter = (TaskAdapter) lvCreateTask.getAdapter();
                    adapter.notifyDataSetChanged();
                 }
            }
        });

        SharedPreferences sharedPreferences= getSharedPreferences("local", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", "token1234");
        editor.apply();

        String token = sharedPreferences.getString("token", "Default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        lvTaskDetail.setOnItemClickListener((adapterView, view, pozition, l)->{
            pozitieDetailInLinsta = pozition;
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("edit", detailsList.get(pozitieDetailInLinsta));
            detailsLauncher.launch(intent);

        });

        detailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                Details detail = (Details) intent.getSerializableExtra("detail");
                if(detail!=null)
                {
                    detailsList.add(detail);
                }
                DetailsAdapter adapter = new DetailsAdapter(this, R.layout.view_detail, detailsList, getLayoutInflater());
                lvTaskDetail.setAdapter(adapter);
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

    private void openDetailsActivity() {
        Intent intent = new Intent(ProfileActivity.this,  DetailsActivity.class);
        detailsLauncher.launch(intent);
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

}
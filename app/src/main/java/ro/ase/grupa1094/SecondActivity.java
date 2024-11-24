package ro.ase.grupa1094;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText repeatPassword;
    Button signUpButton;
    TextView logInText;
    androidx.cardview.widget.CardView signUpcardView;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        signUpButton = findViewById(R.id.signUpButton);
        logInText = findViewById(R.id.logInText);
        signUpcardView = findViewById(R.id.signUpCardView);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repeatPass = repeatPassword.getText().toString();

                if (pass.equals(repeatPass)) {
                    saveCredentials(user, pass);

                    Toast.makeText(SecondActivity.this, "SignUp Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SecondActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SecondActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void saveCredentials(String user, String pass) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user);
        editor.putString(KEY_PASSWORD, pass);

        editor.apply();
    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}

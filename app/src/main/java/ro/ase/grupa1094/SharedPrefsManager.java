package ro.ase.grupa1094;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    private static final String PREF_NAME = "UserProfile";
    private SharedPreferences sharedPreferences;

    public SharedPrefsManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserProfile(UserProfile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", profile.getUsername());
        editor.putString("email", profile.getEmail());
        editor.putString("phone", profile.getPhone());
        editor.putString("password", profile.getPassword());
        editor.apply();
    }

    public UserProfile getUserProfile() {
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        String password = sharedPreferences.getString("password", "");
        return new UserProfile(username, email, phone, password);
    }
}

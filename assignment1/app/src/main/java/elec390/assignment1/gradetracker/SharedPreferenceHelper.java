package elec390.assignment1.gradetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SharedPreferenceHelper {
    private SharedPreferences sharedPreferences;

//  https://stackoverflow.com/a/18463758
//  https://github.com/google/gson
    Gson gson = new Gson();

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("ProfilePreference",
                Context.MODE_PRIVATE );
    }

    public void setProfile(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//      convert Profile object to json
        String profileJson = gson.toJson(profile);
        editor.putString("profile", profileJson);
        editor.commit();
    }

    public Profile getProfile() {
        String profileJson = sharedPreferences.getString("profile", null);
        if (profileJson != null) {
//          convert json to Profile object
            Profile profile = gson.fromJson(profileJson, Profile.class);
            return profile;
        }
        return null;
    }

    public void setGradeType(String type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type", type);
        editor.commit();
    }

    public Boolean getIsLetterGrade() {
        String type = sharedPreferences.getString("type", "");
        return (type == "Letter");
    }

    public void reset() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }
}

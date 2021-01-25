package elec390.assignment1.gradetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferenceHelper {
    private SharedPreferences sharedPreferences;
    private Profile profile;

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
}

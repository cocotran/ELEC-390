package elec390.assignment1.gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Intent startIntent;
    private Button profileActivityButton;
    private Button gradeActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferenceHelper = new SharedPreferenceHelper(MainActivity.this);

        profileActivityButton = (Button)findViewById(R.id.profileActivityButton);
        profileActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToProfileActivity();
            }
        });

        gradeActivityButton = (Button)findViewById(R.id.gradeActivityButton);
        gradeActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToGradeActivity();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Profile profile = sharedPreferenceHelper.getProfile();
        if (profile == null)
            goToProfileActivity();
        else
            profileActivityButton.setText(profile.getName());
    }

    private void goToProfileActivity() {
        startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(startIntent);
    }

    private void goToGradeActivity() {
        startIntent = new Intent(getApplicationContext(), GradeActivity.class);
        startActivity(startIntent);
    }
}
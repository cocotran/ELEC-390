package elec390.assignment1.gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    protected SharedPreferenceHelper sharedPreferenceHelper;
    private Profile profile;
    private Button saveButton;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        sharedPreferenceHelper = new SharedPreferenceHelper(ProfileActivity.this);
        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setProfile();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        profile = sharedPreferenceHelper.getProfile();
//        initialize edit text field
        editTextName = (EditText)findViewById(R.id.nameInput);
        editTextAge = (EditText)findViewById(R.id.ageInput);
        editTextID = (EditText)findViewById(R.id.studentIDInput);

        if (profile != null) {
            saveButton.setVisibility(View.GONE);

//            make edit text un-editable
            editTextName.setEnabled(false);
            editTextAge.setEnabled(false);
            editTextID.setEnabled(false);

//            set display value
            editTextName.setText(profile.getName());
            editTextAge.setText(profile.getAge());
            editTextID.setText(profile.getID());
        } else {
            inputProfileColor();
        }
    }

//    back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

//    create action bar
//    https://stackoverflow.com/a/17425803
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    protected void setProfile() {
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String ID = editTextID.getText().toString();
        if (!(name.equals("") && age.equals("") && ID.equals(""))) {
//            if all information is provided
            Profile newProfile = new Profile(name, age, ID);
            sharedPreferenceHelper.setProfile(newProfile);
        } else {
            // TODO: implement errors alert
        }
    }

    @SuppressLint("ResourceAsColor")
    protected void inputProfileColor() {
        editTextName.setTextColor(R.color.gray_900);
        editTextAge.setTextColor(R.color.gray_900);
        editTextID.setTextColor(R.color.gray_900);

        TextView nameTitle = (TextView)findViewById(R.id.nameTitle);
        TextView ageTitle = (TextView)findViewById(R.id.ageTitle);
        TextView studentIDTitle = (TextView)findViewById(R.id.studentIDTitle);
        nameTitle.setTextColor(R.color.gray_700);
        ageTitle.setTextColor(R.color.gray_700);
        studentIDTitle.setTextColor(R.color.gray_700);
    }
}
package elec390.assignment1.gradetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    protected SharedPreferenceHelper sharedPreferenceHelper;
    private Profile profile;
    private Button saveButton;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextID;
    private TextView nameTitle;
    private TextView ageTitle;
    private TextView studentIDTitle;
    private TextView errorMessage;

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
                setProfileOnClick();
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
//        initialize text view
        nameTitle = (TextView)findViewById(R.id.nameTitle);
        ageTitle = (TextView)findViewById(R.id.ageTitle);
        studentIDTitle = (TextView)findViewById(R.id.studentIDTitle);
        errorMessage = (TextView)findViewById(R.id.errorMessage);

        errorMessage.setVisibility(View.GONE);

        if (profile != null) {
            setViewMode();
        } else {
            setEditMode();
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
        inflater.inflate(R.menu.action_bar_profile, menu);
        return true;
    }

//    https://developer.android.com/training/appbar/actions.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                // User chose the "Edit" option
                setEditMode();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected void setProfileOnClick() {
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String ID = editTextID.getText().toString();

//        https://stackoverflow.com/a/6290970
//        if all information is provided
        if (name.trim().length() > 0 && age.trim().length() > 0 && ID.trim().length() > 0) {
//            correct age provided
            if (Integer.parseInt(age) >= 18) {
//                correct ID provided
                if (ID.trim().length() == 6) {
                    errorMessage.setVisibility(View.GONE);
                    try {
                        Profile newProfile = new Profile(name, age, ID);
                        sharedPreferenceHelper.setProfile(newProfile);
                        //                   return to view mode
                        setViewMode();
                        Toast.makeText(this, "Profile saved successfully.", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) { Toast.makeText(this, "Failed to save profile. Please try again.", Toast.LENGTH_SHORT).show(); }
                }
//                incorrect ID
                else
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText("Please enter a valid student ID.");
            }
//            incorrect age
            else
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Please enter a valid age.");
        }
//        missing info
        else errorMessage.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    protected void setEditMode() {
//        Enable save button
        saveButton.setVisibility(View.VISIBLE);
//        make edit text editable
        editTextName.setEnabled(true);
        editTextAge.setEnabled(true);
        editTextID.setEnabled(true);

//        change color of edit text fields
        editTextName.setTextColor(Color.parseColor("#000000"));
        editTextAge.setTextColor(Color.parseColor("#000000"));
        editTextID.setTextColor(Color.parseColor("#000000"));
        editTextName.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        editTextAge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        editTextID.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));

//        change color of text view
        nameTitle.setTextColor(R.color.gray_700);
        ageTitle.setTextColor(R.color.gray_700);
        studentIDTitle.setTextColor(R.color.gray_700);
    }

    @SuppressLint("ResourceAsColor")
    protected void setViewMode() {
        saveButton.setVisibility(View.GONE);

//        make edit text un-editable
        editTextName.setEnabled(false);
        editTextAge.setEnabled(false);
        editTextID.setEnabled(false);

//        change color of edit text fields
        editTextName.setTextColor(R.color.gray_500);
        editTextAge.setTextColor(R.color.gray_500);
        editTextID.setTextColor(R.color.gray_500);
        editTextName.setBackgroundTintList(ColorStateList.valueOf(R.color.gray_500));
        editTextAge.setBackgroundTintList(ColorStateList.valueOf(R.color.gray_500));
        editTextID.setBackgroundTintList(ColorStateList.valueOf(R.color.gray_500));

//        change color of text view
        nameTitle.setTextColor(Color.parseColor("#1A202C"));
        ageTitle.setTextColor(Color.parseColor("#1A202C"));
        studentIDTitle.setTextColor(Color.parseColor("#1A202C"));

//        fetch new profile
        profile = sharedPreferenceHelper.getProfile();

//        set display value
        editTextName.setText(profile.getName());
        editTextAge.setText(profile.getAge());
        editTextID.setText(profile.getID());
    }
}
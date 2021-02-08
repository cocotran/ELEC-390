package elec390.assignment2.gradeTrackerV2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AssignmentActivity extends AppCompatActivity {
    Button deleteCourseButton;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        controller = new Controller();
        deleteCourseButton = (Button)findViewById(R.id.deleteCourseButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteCourseDialog();
            }
        });
    }

    //    back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void openDeleteCourseDialog() {
        DeleteCourseDialogFragment DeleteCourseDialogFragment = new DeleteCourseDialogFragment();
        DeleteCourseDialogFragment.show(getSupportFragmentManager(), "delete course");
    }
}
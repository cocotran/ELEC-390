package elec390.assignment2.gradeTrackerV2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {
    private DatabaseHelper db;
    Button deleteCourseButton;
    private Controller controller;
    private TextView courseInfoTextView;
    private ArrayList<Assignment> assignmentArrayList;
    private ArrayAdapter assignmentAdapter;
    private ListView assignmentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        db = new DatabaseHelper(getApplicationContext());
        controller = new Controller();
        deleteCourseButton = (Button)findViewById(R.id.deleteCourseButton);

        FloatingActionButton fab = findViewById(R.id.addAssignment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddAssignmentDialog();
            }
        });
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

        courseInfoTextView = (TextView)findViewById(R.id.courseInfoTextView);
        Course course = controller.getSelectedCourse();
        courseInfoTextView.setText(course.getCourseCode() + "  -  " + course.getTitle());

        assignmentArrayList = new ArrayList<Assignment>();
        assignmentArrayList = db.getAllAssignmentsByCourse(controller.getSelectedCourse().getID());

        assignmentListView = (ListView) findViewById(R.id.assignmentListView);
        assignmentAdapter = new AssignmentAdapter(this, assignmentArrayList);
        assignmentListView.setAdapter(assignmentAdapter);
    }

    //    back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void openDeleteCourseDialog() {
        DeleteCourseDialogFragment deleteCourseDialogFragment = new DeleteCourseDialogFragment();
        deleteCourseDialogFragment.show(getSupportFragmentManager(), "delete course");
    }

    public void openAddAssignmentDialog() {
        AssignmentDialogFragment assignmentDialogFragment = new AssignmentDialogFragment();
        assignmentDialogFragment.show(getSupportFragmentManager(), "add assignment");
    }
}

class AssignmentAdapter extends ArrayAdapter<Assignment> {

    public AssignmentAdapter(Context context, ArrayList<Assignment> assignments) {
        super(context, 0, assignments);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Get the data item for this position
        Assignment assignment = getItem(position);

//        Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_assignment, parent, false);
        }

        TextView assignmentNameTextView = (TextView) convertView.findViewById(R.id.assignmentNameTextView);
        TextView assignmentGradeTextView = (TextView) convertView.findViewById(R.id.assignmentGradeTextView);

        assignmentNameTextView.setText(assignment.getTitle());
        assignmentGradeTextView.setText("Grade:  " + assignment.getGrade() + " %");

        return convertView;
    }
}
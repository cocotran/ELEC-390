package elec390.assignment2.gradeTrackerV2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Controller controller;
    private ArrayList<Course> courseArrayList;
    private ArrayAdapter courseAdapter;
    private ListView courseListView;
    private TextView assignmentAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        courseArrayList = new ArrayList<Course>();
        controller = new Controller();

        FloatingActionButton fab = findViewById(R.id.addCourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCourseDialog();
            }
        });


//        reset();      // Reset all database for testing purposes
    }

    @Override
    protected void onStart() {
        super.onStart();

        courseArrayList = db.getAllCourses();

        courseListView = (ListView)findViewById(R.id.courseListView);
        courseAdapter = new CourseAdapter(this, courseArrayList);
        courseListView.setAdapter(courseAdapter);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course) courseListView.getItemAtPosition(position);
                controller.setSelectedCourse(course);
                Intent intent = new Intent(MainActivity.this, AssignmentActivity.class);
                startActivity(intent);
            }
        });

        assignmentAverage = (TextView) findViewById(R.id.assignmentAverage);
        ArrayList<Double> gradeList =  db.getAllAssignmentGrades();
        String average = getAverageOfAllAssignments(gradeList);
        assignmentAverage.setText("Average of All Assignments:  " + average);
    }

    public void openCourseDialog() {
        CourseDialogFragment courseDialogFragment = new CourseDialogFragment();
        courseDialogFragment.show(getSupportFragmentManager(), "add course");
    }

    public static String getAverageOfAllAssignments(ArrayList<Double> gradeList) {
        double sum = 0;
        for (int i=0; i < gradeList.size(); i++) {
            sum += gradeList.get(i);
        }
        return String.format("%.2f", sum / gradeList.size());
    }

    private void reset() {
        db.clearDatabase("course");
        db.clearDatabase("assignments");
        Course.resetID();
        Assignment.resetID();
    }
}


class CourseAdapter extends ArrayAdapter<Course> {
    private DatabaseHelper db;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Get the data item for this position
        Course course = getItem(position);

//        Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false);
        }

//        Set course title
        TextView courseNameListView = (TextView) convertView.findViewById(R.id.courseNameTextView);
        courseNameListView.setText(course.getCourseCode() + "  -  " + course.getTitle());

//        Set  assignment average
        TextView averageTextView = (TextView) convertView.findViewById(R.id.averageTextView);
        db = new DatabaseHelper(getContext());
        ArrayList<Double> gradeList =  db.getAllAssignmentGradesByCourse(course.getID());
        averageTextView.setText("Assignment Average:  " + MainActivity.getAverageOfAllAssignments(gradeList));

        return convertView;
    }
}
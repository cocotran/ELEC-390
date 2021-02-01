package elec390.assignment1.gradetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GradeActivity extends AppCompatActivity {
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ListView courseListView;
    private ArrayAdapter courseAdapter;
    private ArrayList<Course> courseArrayList;
    public static ArrayList<Integer> assignmentGradeList;
    public static ArrayList<Integer> assignmentNo;
    public static ArrayList<Integer> averageNo;
    public static ArrayList<Double> averageGradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        sharedPreferenceHelper = new SharedPreferenceHelper(GradeActivity.this);

        assignmentGradeList = new ArrayList<Integer>();
        assignmentNo = new ArrayList<Integer>();
        averageNo = new ArrayList<Integer>();
        averageGradeList = new ArrayList<Double>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Random rnd = new Random();
        int courseNo = rnd.nextInt(5) + 1;
        courseArrayList = new ArrayList<>();

//        prevent courseID from incrementing forever
//        Course always starts from 0
        Course.resetCourseID();
        for (int i=0; i < courseNo; i++) {
            Course newCourse = Course.generateRandomCourse();
            courseArrayList.add(newCourse);
        }

        assignmentNo.clear();
        assignmentGradeList.clear();

        courseListView = (ListView)findViewById(R.id.courseListView);
        courseAdapter = new CourseAdapter(this, courseArrayList);
        courseListView.setAdapter(courseAdapter);
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
        inflater.inflate(R.menu.action_bar_grade, menu);
        return true;
    }

//    https://developer.android.com/training/appbar/actions.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_convertGrade:
                // User chose the "Convert" option
                convertGrade();
                if (sharedPreferenceHelper.getIsLetterGrade())
                    sharedPreferenceHelper.setGradeType("Percentage");
                else
                    sharedPreferenceHelper.setGradeType("Letter");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public static String convertToLetterGrade(int grade) {
        if (grade < 50) return "F";
        else if (grade >= 50 && grade < 60) return "D";
        else if (grade >= 60 && grade < 70) return "C";
        else if (grade >= 70 && grade < 80) return "B";
        else if (grade > 80 && grade < 90) return "A";
        else if (grade >= 90) return "A+";
        return null;
    }

    public static String convertToLetterGrade(double grade) {
        if (grade < 50) return "F";
        else if (grade >= 50 && grade < 60) return "D";
        else if (grade >= 60 && grade < 70) return "C";
        else if (grade >= 70 && grade < 80) return "B";
        else if (grade > 80 && grade < 90) return "A";
        else if (grade >= 90) return "A+";
        return "";
    }

    public static String getAverage(ArrayList<Assignment> assignmentArrayList) {
        double sum = 0;
        for (int i=0; i < assignmentArrayList.size(); i++) {
            sum += assignmentArrayList.get(i).getAssignmentGrade();
        }
        return String.format("%.2f", (sum/assignmentArrayList.size()));
    }

    private void convertGrade() {
        if (!sharedPreferenceHelper.getIsLetterGrade()) {
//            percentage to letter
            for (int i=0; i < assignmentNo.size(); i++) {
                try {
                    TextView assignment = (TextView)findViewById(assignmentNo.get(i));
                    String initialAssignment = assignment.getText().toString();
                    int percentageGrade = assignmentGradeList.get(i);
                    String newAssignmentString = initialAssignment.substring(0, initialAssignment.indexOf(":")+1) + "  " + GradeActivity.convertToLetterGrade(percentageGrade);
                    assignment.setText(newAssignmentString);
                } catch (Exception e) {
                    break;
                }
            }
            for (int i=0; i < averageNo.size(); i++) {
                try {
                    TextView assignment = (TextView)findViewById(averageNo.get(i));
                    assignment.setText("Average:  " + GradeActivity.convertToLetterGrade(averageGradeList.get(i)));
                } catch (Exception e) {
                    break;
                }
            }
        }
        else {
            for (int i=0; i < assignmentNo.size(); i++) {
//                letter to percentage
                try {
                    TextView assignment = (TextView)findViewById(assignmentNo.get(i));
                    String initialAssignment = assignment.getText().toString();
                    int letterGrade = assignmentGradeList.get(i);
                    String newAssignmentString = initialAssignment.substring(0, initialAssignment.indexOf(":")+1) + "  " + letterGrade;
                    assignment.setText(newAssignmentString);
                } catch (Exception e) {
                    break;
                }
            }
            for (int i=0; i < averageNo.size(); i++) {
                try {
                    TextView assignment = (TextView)findViewById(averageNo.get(i));
                    assignment.setText("Average:  " + averageGradeList.get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }
}

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
class CourseAdapter extends ArrayAdapter<Course> {
    private SharedPreferenceHelper sharedPreferenceHelper;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Get the data item for this position
        Course course = getItem(position);
        ArrayList<Assignment> assignmentArrayList = course.getAssignments();

        sharedPreferenceHelper = new SharedPreferenceHelper(getContext());

//        Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false);
        }

//        Lookup view for data population
//        Set course title
        TextView courseNameListView = (TextView) convertView.findViewById(R.id.courseNameTextView);
        courseNameListView.setText(course.getCourseTitle());

//        Set assignments
        LinearLayout assignmentLayout = (LinearLayout) convertView.findViewById(R.id.assignmentLayout);
        assignmentLayout.removeAllViews();
        for (int i = 0; i < assignmentArrayList.size(); i++) {
            TextView assignmentTextView = new TextView(getContext());
            Assignment assignment = assignmentArrayList.get(i);
            int ID = View.generateViewId();
            assignmentTextView.setId(ID);
            GradeActivity.assignmentNo.add(ID);
            GradeActivity.assignmentGradeList.add(assignment.getAssignmentGrade());
            if (!sharedPreferenceHelper.getIsLetterGrade())
                assignmentTextView.setText(assignment.getAssignmentTitle() + ":  " + assignment.getAssignmentGrade());
            else
                assignmentTextView.setText(assignment.getAssignmentTitle() + ":  " + GradeActivity.convertToLetterGrade(assignment.getAssignmentGrade()));
            assignmentTextView.setTextColor(Color.parseColor("#1A202C"));
            assignmentLayout.addView(assignmentTextView);
        }

//        Set average
        TextView averageTextView = (TextView) convertView.findViewById(R.id.averageTextView);
        int ID = View.generateViewId();
        averageTextView.setId(ID);
        GradeActivity.averageNo.add(ID);
        double average = Double.parseDouble(GradeActivity.getAverage(assignmentArrayList));
        GradeActivity.averageGradeList.add(average);
        if (!sharedPreferenceHelper.getIsLetterGrade())
            averageTextView.setText("Average:  " + average);
        else
            averageTextView.setText("Average:  " + GradeActivity.convertToLetterGrade(average));

        return convertView;
    }
}

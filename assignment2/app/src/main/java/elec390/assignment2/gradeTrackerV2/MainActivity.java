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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ArrayList<Course> courseArrayList;
    private ArrayAdapter courseAdapter;
    private ListView courseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        courseArrayList = new ArrayList<Course>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement onClick action
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        courseArrayList = db.getAllCourses();

        courseListView = (ListView)findViewById(R.id.courseListView);
        courseAdapter = new CourseAdapter(this, courseArrayList);
        courseListView.setAdapter(courseAdapter);
    }
}


class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

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
        averageTextView.setText("Assignment Average:  " + "NA");

        return convertView;
    }
}
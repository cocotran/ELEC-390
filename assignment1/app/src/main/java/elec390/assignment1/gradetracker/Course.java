package elec390.assignment1.gradetracker;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Tawfiq on 1/13/2017.
 */
public class Course {
    private static int courseID = 0; //static ID increments with every new Course created
    private String courseTitle; //cou
    private ArrayList<Assignment> assignments;

    public Course() {}

    private Course(String title, ArrayList<Assignment> assns) {
        courseTitle = title;
        assignments = assns;
        courseID++;
    }

    //returns a Course instant with random assignment values
    static public Course generateRandomCourse() {
        Random rnd = new Random();
        int assignmentNo = rnd.nextInt(5);
        ArrayList<Assignment> tempAssns = new ArrayList<Assignment>();
        for (int i = 0; i < assignmentNo; i++)
            tempAssns.add(Assignment.generateRandomAssignment());
        return  new Course("Course " + courseID, tempAssns);
    }

    //****get methods*****//
    public String getCourseTitle() {
        return courseTitle;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    static public void resetCourseID() {
        courseID = 0;
    }
}

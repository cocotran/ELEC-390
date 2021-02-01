package elec390.assignment1.gradetracker;

import java.util.Random;
/**
 * Created by Tawfiq on 1/13/2017.
 */

public class Assignment {
    private static int assID = 0; //static ID increments with every new assignment created
    private String assignmentTitle; //title of assignment
    private int assignmentGrade; //grade of assignment
    //private constructor. Increments ID.
    private Assignment(String title, int grade)
    {
        assignmentTitle = title;
        assignmentGrade = grade;
        assID++;
    }
    //returns an Assignment instance with random values
    static public Assignment generateRandomAssignment()
    {
        Random rnd = new Random();
        String tempTitle = "Assignment " + assID;
        int tempGrade = rnd.nextInt(100) + 1;
        return new Assignment(tempTitle, tempGrade);
    }
    //****get methods*****//
    public String getAssignmentTitle() {return assignmentTitle; }
    public int getAssignmentGrade() {return assignmentGrade;}
    public static int getAssID() {return assID;}
}
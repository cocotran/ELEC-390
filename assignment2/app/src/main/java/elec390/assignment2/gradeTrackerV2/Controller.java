package elec390.assignment2.gradeTrackerV2;

public class Controller {
    static private Course selectedCourse;

    public Controller() {}

    public static void setSelectedCourse(Course course) { selectedCourse = course; }

    public static Course getSelectedCourse() { return selectedCourse; }

    public static String getSelectedCourseID() { return selectedCourse.getID();}
}

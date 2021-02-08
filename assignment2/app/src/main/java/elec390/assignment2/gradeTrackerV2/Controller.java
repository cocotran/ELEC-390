package elec390.assignment2.gradeTrackerV2;

public class Controller {
    static private Course selectedCourse;

    public Controller() {}

    public void setSelectedCourse(Course course) { this.selectedCourse = course; }

    public Course getSelectedCourse() { return this.selectedCourse; }
}

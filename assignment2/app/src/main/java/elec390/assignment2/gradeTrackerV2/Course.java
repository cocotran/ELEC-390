package elec390.assignment2.gradeTrackerV2;

public class Course {
    private String ID;
    private String title;
    private String courseCode;
    public static int courseID = 0;

    public Course() {}

    public Course(String ID, String title, String courseCode) {
        this.ID = ID;
        this.title = title;
        this.courseCode = courseCode;
    }

    protected void setID(String ID) { this.ID = ID;}
    protected void setTitle(String title) { this.title = title; }
    protected void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public static String generateNewID() { return Integer.toString(++courseID); }
    public static void resetID() { courseID = 0; }

    protected String getID() { return ID; }
    protected String getTitle() { return title; }
    protected String getCourseCode() { return courseCode; }
}

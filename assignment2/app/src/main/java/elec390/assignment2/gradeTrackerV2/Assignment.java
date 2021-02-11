package elec390.assignment2.gradeTrackerV2;

public class Assignment {
    private String ID;
    private String title;
    private double grade;
    public static int assignmentID = 0;

    public Assignment() {}

    public Assignment(String ID, String title, double grade) {
        this.ID = ID;
        this.title = title;
        this.grade = grade;
    }

    protected void setID(String ID) {this.ID = ID;}
    protected void setTitle(String title) {this.title = title;}
    protected void setGrade(double grade) {this.grade = grade;}

    public static String generateNewID() { return Integer.toString(++assignmentID); }
    public static void resetID() { assignmentID = 0; }

    protected String getID() {return ID;}
    protected String getTitle() {return title;}
    protected double getGrade() {return grade;}
}

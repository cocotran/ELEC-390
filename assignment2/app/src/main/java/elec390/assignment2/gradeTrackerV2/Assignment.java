package elec390.assignment2.gradeTrackerV2;

public class Assignment {
    private int ID;
    private String title;
    private double grade;

    public void Assignment(int ID, int courseID, String title, double grade) {
        this.ID = ID;
        this.title = title;
        this.grade = grade;
    }

    protected void setID(int ID) {this.ID = ID;}
    protected void setTitle(String title) {this.title = title;}
    protected void setGrade(double grade) {this.grade = grade;}

    protected int getID() {return ID;}
    protected String getTitle() {return title;}
    protected double getGrade() {return grade;}
}

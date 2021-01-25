package elec390.assignment1.gradetracker;

public class Profile {
    private String name;
    private String age;
    private String ID;

    public Profile(String name, String age, String ID) {
        this.name = name;
        this.age = age;
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }

    public String getAge() {
        return this.age;
    }

    public String getID() {
        return this.ID;
    }
}

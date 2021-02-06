package elec390.assignment2.gradeTrackerV2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "courseManager";

    // Table Names
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_ASSIGNMENT = "assignments";

    private static final String KEY_ID = "id";

    // COURSE Table - column name
    private static final String KEY_COURSE = "course_id";
    private static final String KEY_COURSE_TITLE = "title";
    private static final String KEY_COURSE_CODE = "course_code";

    // ASSIGNMENT Table - column name
    private static final String KEY_ASSIGNMENT = "assignment_id";
    private static final String KEY_ASSIGNMENT_TITLE = "title";
    private static final String KEY_ASSIGNMENT_GRADE = "assignment_grade";

    // Table Create Statements
    // Course table create statement
    private static final String CREATE_COURSE_TODO = "CREATE TABLE "
            + TABLE_COURSE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COURSE + " TEXT," + KEY_COURSE_TITLE
            + " TEXT," + KEY_COURSE_CODE + " TEXT" + ")";

    // Assignment table create statement
    private static final String CREATE_ASSIGNMENT_TODO = "CREATE TABLE "
            + TABLE_ASSIGNMENT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ASSIGNMENT + " TEXT," + KEY_COURSE + " INTEGER," + KEY_ASSIGNMENT_TITLE
            + " TEXT," + KEY_ASSIGNMENT_GRADE + " DOUBLE" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_COURSE_TODO);
        db.execSQL(CREATE_ASSIGNMENT_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENT);

        // create new tables
        onCreate(db);
    }


//    Creating a course
    public void createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE, course.getID());
        values.put(KEY_COURSE_TITLE, course.getTitle());
        values.put(KEY_COURSE_CODE, course.getCourseCode());

        db.insert(TABLE_COURSE, null, values);
    }

    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE, course.getID());
        values.put(KEY_COURSE_TITLE, course.getTitle());
        values.put(KEY_COURSE_CODE, course.getCourseCode());

        db.update(TABLE_COURSE, values, KEY_ID + " = ?", new String[] {course.getID()});
    }

//    getting all courses
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT    * FROM " + TABLE_COURSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setID(c.getString((c.getColumnIndex(KEY_COURSE))));
                course.setTitle((c.getString(c.getColumnIndex(KEY_COURSE_TITLE))));
                course.setCourseCode((c.getString(c.getColumnIndex(KEY_COURSE_CODE))));

                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }

    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);

    }
}

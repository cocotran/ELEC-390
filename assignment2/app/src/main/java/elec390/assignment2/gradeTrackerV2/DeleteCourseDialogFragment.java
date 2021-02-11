package elec390.assignment2.gradeTrackerV2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteCourseDialogFragment extends AppCompatDialogFragment {
    private DatabaseHelper db;
    private Controller controller;
    private TextView deleteCourse;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_course_dialog_fragment, null);

        db = new DatabaseHelper(getContext());
        controller = new Controller();
        Course courseToDelete = controller.getSelectedCourse();

        deleteCourse = (TextView) view.findViewById(R.id.deleteCourseTextView);
        deleteCourse.setText(courseToDelete.getCourseCode() + " - " + courseToDelete.getTitle());

        builder.setView(view).setTitle("Confirm to delete this course?").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteCourse(courseToDelete.getID());                // remove course from database
                db.deleteAssignmentsByCourse(courseToDelete.getID());   // remove assignments of that course from database
                getActivity().finish();                                 // return to MainActivity
            }
        });

        return builder.create();
    }
}

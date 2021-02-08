package elec390.assignment2.gradeTrackerV2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CourseDialogFragment extends AppCompatDialogFragment {
    private DatabaseHelper db;
    EditText courseNameInput;
    EditText courseCodeInput;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.course_dialog_fragment, null);

        builder.setView(view).setTitle("Add course").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                courseNameInput = view.findViewById(R.id.courseNameInput);
                courseCodeInput = view.findViewById(R.id.courseCodeInput);
                Course course = new Course(Course.generateNewID(), courseNameInput.getText().toString(), courseCodeInput.getText().toString());
                db = new DatabaseHelper(getContext());
                db.createCourse(course);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });

        return builder.create();
    }

}

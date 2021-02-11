package elec390.assignment2.gradeTrackerV2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AssignmentDialogFragment extends AppCompatDialogFragment {
    private DatabaseHelper db;
    EditText assignmentNameInput;
    EditText assignmentGradeInput;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.assignment_dialog_fragment, null);

        builder.setView(view).setTitle("Add assignment").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assignmentNameInput = view.findViewById(R.id.assignmentNameInput);
                assignmentGradeInput = view.findViewById(R.id.assignmentGradeInput);
                Assignment assignment = new Assignment(Assignment.generateNewID(), assignmentNameInput.getText().toString(), Double.parseDouble(assignmentGradeInput.getText().toString()));
                db = new DatabaseHelper(getContext());
                db.createAssignment(assignment, Controller.getSelectedCourseID());
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });

        return builder.create();
    }
}

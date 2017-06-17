package concaro.sqlite.app.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import concaro.sqlite.app.MainActivity;
import concaro.sqlite.app.R;
import concaro.sqlite.app.model.TaskEntity;

/**
 * Created by CONCARO on 6/16/2017.
 */

public class EditTaskFragment extends Fragment {


    Button btnEdit;
    EditText edtName;
    EditText edtDes;
    int idTask;

    public static EditTaskFragment newInstance(int id) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnEdit = (Button) getActivity().findViewById(R.id.btnEdit);
        edtName = (EditText) getActivity().findViewById(R.id.edtName);
        edtDes = (EditText) getActivity().findViewById(R.id.edtDes);


        if (getArguments() != null) {
            idTask = getArguments().getInt("id", -1);
            if (idTask != -1) {
                final TaskEntity task = ((MainActivity) getActivity()).getDb().getTask(idTask);
                edtName.setText(task.getName());
                edtDes.setText(task.getDescription());

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).getDb().updateTask(new TaskEntity(idTask,
                                edtName.getText().toString().trim(),
                                edtDes.getText().toString().trim(),
                                task.getDateCreated()));
                        getActivity().onBackPressed();
                    }
                });
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

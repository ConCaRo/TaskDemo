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

public class CreateTaskFragment extends Fragment {

    Button btnCreate;
    EditText edtName;
    EditText edtDes;

    public static CreateTaskFragment newInstance() {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnCreate = (Button) getActivity().findViewById(R.id.btnCreate);
        edtName = (EditText) getActivity().findViewById(R.id.edtName);
        edtDes = (EditText) getActivity().findViewById(R.id.edtDes);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getDb().createTask(new TaskEntity((int) System.currentTimeMillis() / 1000,
                        edtName.getText().toString().trim(),
                        edtDes.getText().toString().trim()));
                getActivity().onBackPressed();
            }
        });
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

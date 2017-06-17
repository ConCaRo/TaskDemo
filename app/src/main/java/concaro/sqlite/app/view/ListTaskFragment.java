package concaro.sqlite.app.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import concaro.sqlite.app.MainActivity;
import concaro.sqlite.app.R;
import concaro.sqlite.app.model.TaskEntity;

/**
 * Created by CONCARO on 6/16/2017.
 */

public class ListTaskFragment extends Fragment implements TaskAdapter.ITaskAdapter {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    List<TaskEntity> lsTaskEntities;
    FloatingActionButton fab;

    public static ListTaskFragment newInstance() {
        ListTaskFragment fragment = new ListTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Trong", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Trong", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);
        initView(view);
        setAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Trong", "onActivityCreated");
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addCreateTaskFragment();
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


    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        lsTaskEntities = ((MainActivity) getActivity()).getDb().getAllTasks();
        adapter = new TaskAdapter(lsTaskEntities, ListTaskFragment.this, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void notifyDataSetChanged() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lsTaskEntities.clear();
                lsTaskEntities.addAll(((MainActivity) getActivity()).getDb().getAllTasks());
                adapter.notifyDataSetChanged();
            }
        }, 100);
    }

    @Override
    public void onClickDelete(TaskEntity task) {
        ((MainActivity) getActivity()).getDb().deleteTask(task);
        notifyDataSetChanged();
    }

    @Override
    public void onClickView(TaskEntity task) {
        ((MainActivity) getActivity()).addEditTaskFragment(task.getId());

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

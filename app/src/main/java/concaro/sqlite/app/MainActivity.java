package concaro.sqlite.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import concaro.sqlite.app.database.DatabaseHandler;
import concaro.sqlite.app.view.CreateTaskFragment;
import concaro.sqlite.app.view.EditTaskFragment;
import concaro.sqlite.app.view.ListTaskFragment;

/**
 * Created by CONCARO on 6/16/2017.
 */

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTaskListFragment();
    }

    public void addTaskListFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ListTaskFragment.newInstance())
                .commit();
    }

    public void addEditTaskFragment(int id) {
        replaceFragment(EditTaskFragment.newInstance(id), "EditTaskFragment");
        setTitle("Edit Task");
    }

    public void addCreateTaskFragment() {
        replaceFragment(CreateTaskFragment.newInstance(), "CreateTaskFragment");
        setTitle("Create Task");
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.container, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public DatabaseHandler getDb() {
        if (db == null) {
            db = new DatabaseHandler(this);
        }
        return db;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Trong", "onBackPressed");
        setTitle(R.string.app_name);
    }
}

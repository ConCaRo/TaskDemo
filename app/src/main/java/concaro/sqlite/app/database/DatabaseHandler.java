package concaro.sqlite.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import concaro.sqlite.app.model.TaskEntity;


/**
 * Created by CONCARO on 6/16/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqlite_demo";

    // Table name
    private static final String TABLE_TASKS = "tasks";

    // Columns names
    private static final String KEY_TASK_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE_CREATED = "date_created";
    private static final String KEY_DATE_UPDATED = "date_updated";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE_CREATED + " TEXT,"
                + KEY_DATE_UPDATED + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Creating new task
    public void createTask(TaskEntity task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_ID, task.getId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_DATE_CREATED, getCurrentTime());
        values.put(KEY_DATE_UPDATED, "");

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Getting detail task
    public TaskEntity getTask(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_TASK_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_DATE_CREATED, KEY_DATE_UPDATED}, KEY_TASK_ID + "=?",
                new String[]{String.valueOf(taskId)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TaskEntity task = new TaskEntity(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return task
        return task;
    }

    // Getting all tasks
    public List<TaskEntity> getAllTasks() {
        List<TaskEntity> taskList = new ArrayList<TaskEntity>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskEntity task = new TaskEntity();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDateCreated(cursor.getString(3));
                task.setDateUpdated(cursor.getString(4));

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    // Updating a task
    public int updateTask(TaskEntity task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_DATE_CREATED, task.getDateCreated());
        values.put(KEY_DATE_UPDATED, getCurrentTime());

        return db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    // Deleting a task
    public void deleteTask(TaskEntity task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    // Getting current time
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyy '-' h:mm aa");
        Date d = new Date();
        return format.format(d);
    }
}

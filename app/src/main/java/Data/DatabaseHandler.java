package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Todo;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TODOS_TABLE = "create table " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, " + Util.KEY_TITLE + " TEXT, "
                + Util.KEY_COMPLETED + " TEXT)";

        database.execSQL(CREATE_TODOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS "+ Util.TABLE_NAME);

        // CREATE TABLE AGAIN
        onCreate(database);
    }

    public void addTodo(Todo todo) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_TITLE, todo.getTitle());
        values.put(Util.KEY_COMPLETED, todo.isCompleted());

        database.insert(Util.TABLE_NAME, null, values);
    }

    public Todo getTodo(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_TITLE,
                        Util.KEY_COMPLETED}, Util.KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Todo todo= new Todo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Boolean.getBoolean(cursor.getString(2)));

        return todo;
    }

    public List<Todo> getAllTodos() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Todo> todos = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = database.rawQuery(selectAll, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setTitle(cursor.getString(1));
                todo.setCompleted(Boolean.getBoolean(cursor.getString(2)));

                todos.add(todo);
            } while (cursor.moveToNext());
        }
        return todos;
    }
}
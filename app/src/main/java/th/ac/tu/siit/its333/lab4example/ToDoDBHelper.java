package th.ac.tu.siit.its333.lab4example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    // Database file name
    public static final String name = "todo.sqlite3";
    // Database version
    public static final int version = 1;

    public ToDoDBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This is called when the database is still NOT available
        String sql = "CREATE TABLE todo (" +
                "_id integer primary key autoincrement, " +
                "title text not null," +
                "priority integer default 0);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is called when the version defined in this class is different from
        // the version in the database file
        String sql = "DROP TABLE IF EXISTS todo;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}

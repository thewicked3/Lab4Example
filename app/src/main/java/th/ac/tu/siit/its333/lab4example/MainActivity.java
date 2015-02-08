package th.ac.tu.siit.its333.lab4example;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener,
                   AdapterView.OnItemLongClickListener {

    ToDoDBHelper helper;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new ToDoDBHelper(this);

        // Add temporary data
        SQLiteDatabase dbw = helper.getWritableDatabase();
        ContentValues ri = new ContentValues();
        ri.put("title", "AI homework");
        ri.put("priority", 3);
        long new_id = dbw.insert("todo", null, ri);
        dbw.close();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM todo ORDER BY priority DESC;",
                null);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "priority"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        // Example on how to manipulate records

//        SQLiteDatabase dbw = helper.getWritableDatabase();
//        ContentValues ri = new ContentValues();
//        ri.put("title", "AI homework");
//        ri.put("priority", 3);
//        long new_id = dbw.insert("todo", null, ri);
//        if (new_id == -1) {
//            Log.d("todo", "unable to insert a new record");
//        }
//        else {
//            Log.d("todo", "created a record with id=" + new_id);
//            int n_rows;
//            ContentValues ru = new ContentValues();
//            ru.put("priority", 2);
//            n_rows = dbw.update("todo", // table name
//                    ru,                     // a record
//                    "_id = ?",              // conditions
//                    new String[]{ Long.toString(new_id) }    // values for the conditions
//            );
//            Log.d("todo", "updated " + n_rows + " records");
//
//            n_rows = dbw.delete("todo", "_id = ?", new String[]{Long.toString(new_id)});
//            Log.d("todo", "deleted " + n_rows + " records");
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Log.d("todo", id + " is clicked");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int n = db.delete("todo",
                "_id = ?",
                new String[]{Long.toString(id)});

        if (n == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Successfully deleted the selected item.",
                    Toast.LENGTH_SHORT);
            t.show();

            // retrieve a new collection of records
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM todo ORDER BY priority DESC;",
                    null);

            // update the adapter
            adapter.changeCursor(cursor);
        }
        db.close();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

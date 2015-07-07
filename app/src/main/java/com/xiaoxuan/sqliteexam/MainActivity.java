package com.xiaoxuan.sqliteexam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaoxuan.sqliteexam.models.Books;
import com.xiaoxuan.sqliteexam.utils.GsonTools;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.create_database)
    Button createBtn;
    @InjectView(R.id.add_database)
    Button addBtn;
    @InjectView(R.id.delete_database)
    Button DeleteBtn;
    @InjectView(R.id.query_database)
    Button QueryBtn;

    private MyDataBaseHelper dbHelper;

    private Books books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        dbHelper = new MyDataBaseHelper(this, "BookStore.db", null, 2);
        books=new Books();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        //插入数据
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 500);
                values.put("price", 16.96);
                db.insert("Book", null, values);//插入第一条数据到表Book中
                //组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author","Leo");
                values.put("pages", 510);
                values.put("price", 19.00);
                db.insert("Book", null, values);
            }
        });
        //删除数据
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //查询数据
        QueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询Book表中所有数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        books.setName(name);
                        books.setAuthor(author);
                        books.setPages(pages);
                        books.setPrice(price);
                        String str = GsonTools.creatGsonString(books);
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
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

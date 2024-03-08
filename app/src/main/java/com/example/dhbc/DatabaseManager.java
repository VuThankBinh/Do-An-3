package com.example.dhbc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void addQuestion(String question, String image, String answer, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_QUESTION, question);
        contentValues.put(DatabaseHelper.COLUMN_IMAGE, image);
        contentValues.put(DatabaseHelper.COLUMN_ANSWER, answer);
        contentValues.put(DatabaseHelper.COLUMN_STATUS, status);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }


    // Các phương thức khác để thực hiện các hoạt động trên cơ sở dữ liệu như cập nhật, xóa, truy vấn, v.v.
}

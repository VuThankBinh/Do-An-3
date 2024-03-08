package com.example.dhbc;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cauhoi.db";
    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    public static final String TABLE_NAME = "CauHoiHDBC";
    // Các cột trong bảng
    public static final String COLUMN_ID = "MaCauHoi";
    public static final String COLUMN_QUESTION = "CauHoi";
    public static final String COLUMN_IMAGE = "HinhAnh";
    public static final String COLUMN_ANSWER = "DapAn";
    public static final String COLUMN_STATUS = "TinhTrangTraLoi";

    // Câu lệnh tạo bảng
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QUESTION + " TEXT, " +
            COLUMN_IMAGE + " TEXT, " +
            COLUMN_ANSWER + " TEXT, " +
            COLUMN_STATUS + " INTEGER" +
            ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void updateStatus(int maCauHoi, int newValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, newValue);
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(maCauHoi)};
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
    }
}

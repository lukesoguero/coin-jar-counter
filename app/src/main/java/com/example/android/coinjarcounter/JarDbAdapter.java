package com.example.android.coinjarcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by lukes_000 on 8/6/2016.
 */
public class JarDbAdapter {

    private static final String DATABASE_NAME = "jar.db";
    private static final int DATABASE_VERSION = 1;

    public static final String JAR_TABLE = "jar";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_NAME = "name";

    private String[] allColumns = { COLUMN_ID, COLUMN_TOTAL, COLUMN_NAME };

    public static final String CREATE_TABLE_JAR = "create table " + JAR_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TOTAL + " double not null, "
            + COLUMN_NAME + " text not null " + ")";

    private SQLiteDatabase sqlDB;
    private Context context;
    private JarDbHelper jarDbHelper;

    public JarDbAdapter(Context ctx){
        context = ctx;
    }

    public JarDbAdapter open() throws android.database.SQLException {
        jarDbHelper = new JarDbHelper(context);
        sqlDB = jarDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        jarDbHelper.close();
    }

    public Jar createJar(String name, double total) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TOTAL, total);

        long insertId = sqlDB.insert(JAR_TABLE, null, values);

        Cursor cursor = sqlDB.query(JAR_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Jar newJar = cursorToJar(cursor);
        cursor.close();
        return newJar;
    }

    public long updateJar(long idToUpdate, double newTotal) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL, newTotal);

        return sqlDB.update(JAR_TABLE, values, COLUMN_ID + " = " + idToUpdate, null);
    }

    public long deleteJar(long idToDelete) {
        return sqlDB.delete(JAR_TABLE, COLUMN_ID + " = " + idToDelete, null);
    }


    public ArrayList<Jar> getAllJars(){
        ArrayList<Jar> jars = new ArrayList<Jar>();

        Cursor cursor = sqlDB.query(JAR_TABLE, allColumns, null, null, null, null, null );

        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Jar jar = cursorToJar(cursor);
            jars.add(jar);
        }

        cursor.close();

        return jars;
    }

    private Jar cursorToJar(Cursor cursor) {
        Jar newJar = new Jar( cursor.getString(2), cursor.getDouble(1), cursor.getLong(0));
        return newJar;
    }

    private static class JarDbHelper extends SQLiteOpenHelper{

        JarDbHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_JAR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + JAR_TABLE);
            onCreate(db);
        }

    }

}

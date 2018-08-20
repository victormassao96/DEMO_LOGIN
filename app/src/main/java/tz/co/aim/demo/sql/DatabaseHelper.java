package tz.co.aim.demo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tz.co.aim.demo.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "login";
    private final static String TABLE_USERS = "users";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_USERNAME = "username";
    private final static String COLUMN_EMAIL = "email";
    private final static String COLUMN_PASSWORD = "password";


    private final static String CREATE_TABLE_USERS= "CREATE TABLE " + TABLE_USERS +"("
            +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMAIL + " TEXT, "
            +COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT"
            + ")";







    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

    }



    public void addUser(User user){


        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, user.email);
        values.put(COLUMN_USERNAME, user.password);
        values.put(COLUMN_PASSWORD, user.username);

        long id = sqLiteDatabase.insert(TABLE_USERS, null, values);

    }

    public User Authenticate(User user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{COLUMN_ID,COLUMN_USERNAME,COLUMN_EMAIL,COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",
                new String[]{user.email},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()&&cursor.getCount()>0){
            User user1 = new User(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));

            if (user.password.equalsIgnoreCase(user1.password)){
                return user1;
            }
        }

        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{COLUMN_ID,COLUMN_USERNAME,COLUMN_EMAIL,COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",new String[]{email}, null, null, null);

        if (cursor != null&&cursor.moveToFirst()&&cursor.getCount()>0){
            return true;
        }
        return false;



    }
}

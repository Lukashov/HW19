package com.example.den.hw19.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.den.hw19.models.NotificationsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Den on 11.09.15.
 */
public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "notifications";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_TICKET_TEXT = "ticketText";

    private static final String DATABASE_CREATE = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_SUBTITLE + " text not null, "
            + COLUMN_TICKET_TEXT + " text not null);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void addNewNotification(String message, String title,
                                   String subtitle, String ticketText) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SUBTITLE, subtitle);
        values.put(COLUMN_TICKET_TEXT, ticketText);

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public List<NotificationsModel> getAllNotifications() {
        List<NotificationsModel> notificationsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationsModel notificationsModel = new NotificationsModel();

                notificationsModel.setId(cursor.getString(0));
                notificationsModel.setMessage(cursor.getString(1));
                notificationsModel.setTitle(cursor.getString(2));
                notificationsModel.setSubtitle(cursor.getString(3));
                notificationsModel.setTicketText(cursor.getString(4));

                notificationsList.add(notificationsModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notificationsList;
    }

}

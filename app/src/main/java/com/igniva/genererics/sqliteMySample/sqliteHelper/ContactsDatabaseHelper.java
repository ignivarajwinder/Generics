package com.igniva.genererics.sqliteMySample.sqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.igniva.genererics.sqliteMySample.model.ContactsPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ContactsDatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = ContactsDatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MySampleContactsManager";

    // Table Names
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_INTERESTS = "interests";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // CONTACTS Table - column nmaes
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTACT = "contact";

    // INTERESTS Table - column names
    private static final String KEY_INTEREST_1 = "interest1";
    private static final String KEY_INTEREST_2 = "interest2";
    private static final String KEY_INTEREST_3 = "interest3";
    private static final String KEY_CONTACT_ID = "contact_id";

//    // NOTE_TAGS Table - column names
//    private static final String KEY_TODO_ID = "todo_id";
//    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_CONTACT + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_INTERESTS = "CREATE TABLE " + TABLE_INTERESTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INTEREST_1 + " TEXT," + KEY_INTEREST_2 + " TEXT," + KEY_INTEREST_3 + " TEXT," +  KEY_CONTACT_ID+ " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

//    // todo_tag table create statement
//    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
//            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
//            + KEY_CREATED_AT + " DATETIME" + ")";

    public ContactsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_INTERESTS);
//        db.execSQL(CREATE_TABLE_TODO_TAG);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_INTERESTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);
 
        // create new tables
        onCreate(db);
    }
 
    // ------------------------ "todos" table methods ----------------//
 
     /**
     * Creating a todo
     */
    public long createContact(ContactsPojo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, todo.getName());
        values.put(KEY_CONTACT, todo.getContact());
        values.put(KEY_CREATED_AT, getDateTime());
 
        // insert row
        long todo_id = db.insert(TABLE_CONTACTS, null, values);
 
        // insert tag_ids
//        for (long tag_id : tag_ids) {
            createInterests(todo_id,todo);
//        }
 
        return todo_id;
    }
 
    /**
     * get single todo
     */
    public ContactsPojo getContact(long contact_id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE "
                + KEY_ID + " = " + contact_id;
 
        Log.e(LOG, selectQuery);
 
        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c != null)
            c.moveToFirst();
 
        ContactsPojo td = new ContactsPojo();
        td.setId(String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
        td.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        td.setContact((c.getString(c.getColumnIndex(KEY_CONTACT))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        td=getInterests( Long.parseLong(td.getId()),td);

        return td;
    }
 
    /**
     * getting all todos
     * */
    public ArrayList<ContactsPojo> getAllContacs() {
        ArrayList<ContactsPojo> todos = new ArrayList<ContactsPojo>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ContactsPojo td = new ContactsPojo();
                td.setId(String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                td.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                td.setContact((c.getString(c.getColumnIndex(KEY_CONTACT))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                td=getInterests( Long.parseLong(td.getId()),td);

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }
 
        return todos;
    }
 
    /**
     * getting all todos under single tag
     * */
    public List<ContactsPojo> getAllToDosByTag(String tag_name) {
        List<ContactsPojo> todos = new ArrayList<ContactsPojo>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " tc, "
                + TABLE_INTERESTS + " ti WHERE tc."
                + KEY_NAME + " = '" + tag_name + "'" + " AND tc." + KEY_ID
                + " = " + "tI." + KEY_CONTACT_ID ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ContactsPojo td = new ContactsPojo();
                td.setId(String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                td.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                td.setContact((c.getString(c.getColumnIndex(KEY_NAME))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }
 
    /**
     * getting todo count
     */
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
 
    /**
     * Updating a todo
     */
    public int updateContact(ContactsPojo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, todo.getName());
        values.put(KEY_CONTACT, todo.getContact());

        updateInterest(todo);

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }




    /**
     * Deleting a todo
     */
    public void deleteToDo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
    }
 
    // ------------------------ "tags" table methods ----------------//
 
    /**
     * Creating tag
     */
    public long createInterests(long todo_id,ContactsPojo tag) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_INTEREST_1, tag.getInterest1());
        values.put(KEY_INTEREST_2, tag.getInterest2());
        values.put(KEY_INTEREST_3, tag.getInterest3());
        values.put(KEY_CONTACT_ID, String.valueOf(todo_id));
        values.put(KEY_CREATED_AT, getDateTime());
 
        // insert row
        long tag_id = db.insert(TABLE_INTERESTS, null, values);
 
        return tag_id;
    }


    /**
     * get single interest
     */
    public ContactsPojo getInterests(long contact_id,ContactsPojo td ) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_INTERESTS + " WHERE "
                + KEY_CONTACT_ID + " = " + contact_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();


        td.setInterest1((c.getString(c.getColumnIndex(KEY_INTEREST_1))));
        td.setInterest2((c.getString(c.getColumnIndex(KEY_INTEREST_2))));
        td.setInterest3((c.getString(c.getColumnIndex(KEY_INTEREST_3))));
//        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return td;
    }

    /**
     * getting all tags
     * */
    public List<ContactsPojo> getAllInterests() {
        List<ContactsPojo> tags = new ArrayList<ContactsPojo>();
        String selectQuery = "SELECT  * FROM " + TABLE_INTERESTS;
 
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ContactsPojo t = new ContactsPojo();
                t.setId(String.valueOf(c.getInt((c.getColumnIndex(KEY_ID)))));
                t.setInterest1(c.getString(c.getColumnIndex(KEY_INTEREST_1)));
                t.setInterest2(c.getString(c.getColumnIndex(KEY_INTEREST_2)));
                t.setInterest3(c.getString(c.getColumnIndex(KEY_INTEREST_3)));

                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }
 
    /**
     * Updating a tag
     */
    public int updateInterest(ContactsPojo tag) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_INTEREST_1, tag.getInterest1());
        values.put(KEY_INTEREST_2, tag.getInterest2());
        values.put(KEY_INTEREST_3, tag.getInterest3());

        // updating row
        return db.update(TABLE_INTERESTS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }
 
    /**
     * Deleting a tag
     */
    public void deleteInterests(ContactsPojo tag, boolean should_delete_all_tag_todos) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_todos) {
            // get all todos under this tag
            List<ContactsPojo> allTagToDos = getAllToDosByTag(tag.getInterest1());
 
            // delete all todos
            for (ContactsPojo todo : allTagToDos) {
                // delete todo
                deleteToDo(Integer.parseInt(todo.getId()));
            }
        }
 
        // now delete the tag
        db.delete(TABLE_INTERESTS, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }
 
    // ------------------------ "todo_tags" table methods ----------------//
 
    /**
     * Creating todo_tag
     */
//    public long createTodoTag(long todo_id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO_ID, todo_id);
//        values.put(KEY_TAG_ID, tag_id);
//        values.put(KEY_CREATED_AT, getDateTime());
//
//        long id = db.insert(TABLE_TODO_TAG, null, values);
//
//        return id;
//    }
 
    /**
     * Updating a todo tag
     */
//    public int updateNoteTag(long id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_ID, tag_id);
//
//        // updating row
//        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
 
    /**
     * Deleting a todo tag
     */
//    public void deleteToDoTag(long id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TODO, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
 
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
 
    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

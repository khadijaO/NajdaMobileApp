package com.example.najdaapp.DBSqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.najdaapp.Message.MessageModule;
import com.example.najdaapp.contact.ContactModel;
import com.example.najdaapp.emergency.EmergencyModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactdata";

    // Country table name
    private static final String TABLE_NAME_CONTACT= "contacts";

    // Contact Table Columns names
    private static final String KEY_ID = "id";
    private static final String NAME = "Name";
    private static final String H = "Phone";
    private static final String PH = "PhoneNo";
    private static final String RELATION = "Relation";


    private static final String SQL_DELETE_TABLE_CONTACT =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CONTACT;




    // EMERGENCY table name
    private static final String TABLE_EMERGENCY_NAME= "emergency";

    // EMERGENCY Table Columns names
    private static final String KEY_ID_E = "id";
    private static final String NAME_E = "Name";
    private static final String PH_E = "PhoneNo";
    private static final String SQL_DELETE_TABLE_E =
            "DROP TABLE IF EXISTS " + TABLE_EMERGENCY_NAME;


    // MESSAGE  table name
    private static final String TABLE_NAME_MSG= "messagesGPS";
    //create table(id,salutation:hi, hello...,body:le messages principale
//    receiver:if u want to call the receiver by hu=is name
//     GPS boolean integrate GPS location or not
    // Country Table Columns names
    private static final String KEY_ID_M = "id";
    private static final String SALUTATION = "Salutation";
    private static final String BODY = "Body";
    private static final String RECEIVER = "Receiver";
    private static final String GPS = "GPS";

    private static final String SQL_DELETE_TABLE_M =
            "DROP TABLE IF EXISTS " + TABLE_NAME_MSG;



    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the table for the first time CONTACT
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_NAME_CONTACT + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "+ PH + " TEXT, "+ NAME + " TEXT, "
                + RELATION + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);


        // create the table for the first time EMERGENCY

        String CREATE_EMERGENCY_TABLE = "CREATE TABLE " + TABLE_EMERGENCY_NAME + "("
                + KEY_ID_E + " INTEGER PRIMARY KEY, "+ PH_E + " TEXT, "+ NAME_E + " TEXT)";
//        Log.d("h i", CREATE_CONTACT_TABLE);
        sqLiteDatabase.execSQL(CREATE_EMERGENCY_TABLE);

        // create the table for the first time MESSAGE
        String CREATE_TABLE_M = "CREATE TABLE " + TABLE_NAME_MSG + "("
                + KEY_ID_M + " INTEGER PRIMARY KEY, "+ SALUTATION + " TEXT, "
                + BODY + " TEXT, "+
                GPS + " BOOLEAN, "+
                RECEIVER + " BOOLEAN )";
//        Log.d("h i", CREATE_CONTACT_TABLE);
        Log.d("hi", "3333333333333333333333333333333333333");

        sqLiteDatabase.execSQL(CREATE_TABLE_M);


//        set default values
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(PH_E, "119");
//        values.put(NAME_E,"Police2" );
//        long newRowId = db.insert (TABLE_EMERGENCY_NAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE_CONTACT);

        sqLiteDatabase.execSQL(SQL_DELETE_TABLE_E);
        onCreate(sqLiteDatabase);
    }


//    EMERGENCUY_________________________________________________
public EmergencyModel getEmergency(String phone) {
    SQLiteDatabase db = this.getReadableDatabase();
    EmergencyModel emergency=null;
    Cursor cursor = db.query(TABLE_EMERGENCY_NAME, new String[] {
                    NAME_E,PH_E }, null,
            null, null, null, null,"1");
    if(cursor.getCount() <= 0) {
        emergency = new EmergencyModel("119", "help me");

    }

    else{
        cursor.moveToFirst();
        emergency = new EmergencyModel(cursor.getString(1),
                cursor.getString(0));
    }

    return emergency;
}

    public void addEmergency(EmergencyModel contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PH_E, contact.getPhoneNo());
        values.put(NAME_E, contact.getName());
        db.delete(TABLE_EMERGENCY_NAME,null,null);
        long newRowId = db.insert (TABLE_EMERGENCY_NAME, null, values);


//        Toast.makeText(get, "", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllE() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMERGENCY_NAME,null,null);
        if (db != null && db.isOpen()){
            db.close();}

    }

    //    CONTACT __________________________________________________

    public ContactModel getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_CONTACT, new String[] {
                        NAME,PH,RELATION  }, PH + "=?",
                new String[] { id }, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();


        ContactModel contact = new ContactModel(cursor.getString(1),
                cursor.getString(0), cursor.getString(2));
        // return country
        return contact;
    }

    public void deleteContact(ContactModel c){


        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CONTACT, PH + " = ?",
                new String[] { String.valueOf(c.getPhoneNo()) });
        db.close();

    }

    // method to add the contact
    public void addContact(ContactModel contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PH, contact.getPhoneNo());
        values.put(NAME, contact.getName());
        values.put(RELATION, contact.getRelation());
        long newRowId = db.insert (TABLE_NAME_CONTACT, null, values);

        Log.e("hi", "####################################");

//        Toast.makeText(get, "", Toast.LENGTH_SHORT).show();
    }

    // method to retrieve all the contacts in List
    public List<ContactModel> getAllContacts(){
        List<ContactModel> list=new ArrayList<>();
        String query="SELECT * FROM "+TABLE_NAME_CONTACT;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(query,null);
        if(c.moveToFirst()) {
            do {

                list.add(new ContactModel(c.getString(1),c.getString(2),c.getString(3)));

            } while (c.moveToNext());
        }
        return list;
    }

    // get the count of data, this will allow user
    // to not add more that five contacts in database
    public int count(){
        int count=0;
        String query="SELECT COUNT(*) FROM "+TABLE_NAME_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery(query,null);
        if(c.getCount()>0){
            c.moveToFirst();
            count=c.getInt(0);
        }
        c.close();
        return count;
    }

    public void updateContact(ContactModel contact,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(PH, contact.getPhoneNo());
        values.put(RELATION, contact.getRelation());


        // updating row
        db.update(TABLE_NAME_CONTACT, values, PH + " = ?",
                new String[] { phone});
    }


//    ___________________________MESSAGESE__________________
//    // method to add the contact
public void addMessageGPS(MessageModule messageModule){

    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    db.delete(TABLE_NAME_MSG,null,null);

    values.put(SALUTATION, messageModule.getSalutation());
    values.put(RECEIVER,messageModule.isReceiver());
    values.put(BODY, messageModule.getBody());
    values.put(GPS, messageModule.isGps());

    long newRowId = db.insert (TABLE_NAME_MSG, null, values);
}

    public MessageModule getMessage(boolean GPS) {
        MessageModule contact = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_MSG,null, null,
                null, null, null, null,"1");

        if(cursor.getCount() <= 0) {
            contact = new MessageModule("hi", "help me", true,true);

        }

      else{
            cursor.moveToFirst();
//    public MessageModule(String salutation, String body, boolean receiver, boolean gps) //

            contact = new MessageModule(cursor.getString(1), cursor.getString(2), Boolean.parseBoolean(cursor.getString(3)), Boolean.parseBoolean(cursor.getString(4)));
        }
        // return Emergency number
        return contact;
    }

    public void deleteAllMsg() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_MSG,null,null);
        if (db != null && db.isOpen()){
            db.close();}

    }


}

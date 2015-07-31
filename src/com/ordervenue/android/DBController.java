package com.ordervenue.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController extends SQLiteOpenHelper {
	public DBController(Context applicationcontext) {
		super(applicationcontext, "androidsqlite.db", null, 1);
	}

	// int radioButtonSelectedId = 0, radioButtonParrenmtId = 0,
	// chkBoxParrentId = 0;
	@Override
	public void onCreate(SQLiteDatabase db) {
		String query;
		query = "CREATE TABLE Prodcut ( ProdcutID INTEGER , quantity INTEGER, radioButtonSelectedId TEXT,  radioButtonParrenmtId TEXT, chkBoxParrentId TEXT, chkBoxSelectedID TEXT )";
		db.execSQL(query);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String query;
		query = "DROP TABLE IF EXISTS Prodcut";
		db.execSQL(query);
		onCreate(db);

	}

	public void insertUser(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ProdcutID", queryValues.get("ProdcutID"));
		values.put("quantity", queryValues.get("quantity"));
		values.put("radioButtonSelectedId",
				queryValues.get("radioButtonSelectedId"));
		values.put("radioButtonParrenmtId",
				queryValues.get("radioButtonParrenmtId"));
		values.put("chkBoxParrentId", queryValues.get("chkBoxParrentId"));
		values.put("chkBoxSelectedID", queryValues.get("chkBoxSelectedID"));
		Log.e("valuse inserting", values.toString());
		database.insert("Prodcut", null, values);
		database.close();
	}

	public void deleteAll() {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete("Prodcut", null, null);
	}

	public void update_byID(String id, String v1) {
		SQLiteDatabase database = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("quantity", v1);
		database.update("Prodcut", values, "ProdcutID" + "=" + id, null);
	}
	public void updateProduct(String id, String v1,String radioButtonSelectedId) {
		SQLiteDatabase database = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("quantity", v1);		 
		String[] args = new String[]{id, radioButtonSelectedId};
		database.update("Prodcut", values, "ProdcutID=? AND radioButtonSelectedId=?", args);
		
 	
//		db.update(TABLE_LATLONG, values, "Loc_lati=? AND Loc_longi=?", args);
	}
	public void delete_byID(int id) {
		SQLiteDatabase database = this.getWritableDatabase();

		database.delete("Prodcut", "ProdcutID" + "=" + id, null);
	}

	public ArrayList<HashMap<String, String>> getAllProdcut() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM Prodcut";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ProdcutID", cursor.getString(0));
				map.put("quantity", cursor.getString(1));
				map.put("radioButtonSelectedId", cursor.getString(2));
				map.put("radioButtonParrenmtId", cursor.getString(3));
				map.put("chkBoxParrentId", cursor.getString(4));
				map.put("chkBoxSelectedID", cursor.getString(5));
				wordList.add(map);
				
			} while (cursor.moveToNext());
		}
		database.close();
		Log.e("getAllProducts", wordList.toString());
		return wordList;
	}

	public ArrayList<HashMap<String, String>> getColuamData() {
		ArrayList<HashMap<String, String>> ColuamList;
		ColuamList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT ProdcutID ,quantity ,radioButtonSelectedId  ,radioButtonParrenmtId  ,chkBoxParrentId ,chkBoxSelectedID FROM Prodcut";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ProdcutID", cursor.getString(0));
				map.put("quantity", cursor.getString(1));
				map.put("radioButtonSelectedId", cursor.getString(2));
				map.put("radioButtonParrenmtId", cursor.getString(3));
				map.put("chkBoxParrentId", cursor.getString(4));
				map.put("chkBoxSelectedID", cursor.getString(5));
				ColuamList.add(map);
			} while (cursor.moveToNext());
		}
		database.close();
		return ColuamList;

	}
}

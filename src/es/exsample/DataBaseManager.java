package es.exsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseManager extends SQLiteOpenHelper {
	public final static String DATA_BASE_NAME = "gururingou";
	public final static String DATA_NAME = "bus_nagano";
	public final static String TABLE_NAME = "gururi";
	public final static int VERSION = 1;
	private Context context;
	
	public DataBaseManager(Context context) {
		super(context, DATA_BASE_NAME, null, VERSION);
		
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "create table " + TABLE_NAME + " ( "
				+ "id integer primary key, "
				+ "name text not null, "
				+ "yomigana text not null";
		
		for(int i=0; i<44; i++){
			query += ", gottime" + i + " integer not null";
		}
		query += " )";
				
		db.execSQL(query);
		
		inputData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		inputData(db);
	}
	
	public void inputData(SQLiteDatabase db){
		InputStream file = null;
		try {
			file = context.getAssets().open(DATA_NAME + ".dat");
		
			BufferedReader br = new BufferedReader(new InputStreamReader(file, "SHIFT-JIS"));
			String str;
			while((str = br.readLine()) != null){
				String[] data = str.split(",");
				String query = "insert into " + TABLE_NAME + " values( ";
				
				for(int i=0; i<data.length - 1;  i++){
					query += "'" + data[i] + "', ";
				}
				query += "'" + data[data.length-1] + "')";
				
				db.execSQL(query);
			}
		} catch (IOException e) {}
	}
	
	public void showData(SQLiteDatabase db){

		Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
		
		if(c.moveToFirst()){
		do{
			String str = "";
			
			for(int i=0; i<47; i++)
				str += c.getString(i) + ",";
			
			Log.d("DataBase", str);
		}while(c.moveToNext());}
	}

	
	
}

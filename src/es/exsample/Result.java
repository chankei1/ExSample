package es.exsample;

import java.util.ArrayList;

import es.exsample.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Result extends Activity{
	private SQLiteDatabase db;
	private DataBaseManager mDB;
	private String bus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		
		mDB = new DataBaseManager(this);
		db =  mDB.getReadableDatabase();
		
		Intent intent = getIntent();
		
		bus = intent.getStringExtra("selectBus");
		String text = bus + " éûçèï\"; 
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(text);
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getItemList());
		listView.setAdapter(adapter);
		
		db.close();
	}
	
	private ArrayList<String> getItemList() {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "'" + bus + "'";
        Cursor c = db.rawQuery("select * from " + mDB.TABLE_NAME + " where name = " + sql, null);
        
        
        if(c.moveToFirst()){
    	        for(int i=3;i<47;i++){
    	        	String str = c.getString(i);
    	        
    				list.add(str);
    			}
    		}
        
        return list;
    }
}

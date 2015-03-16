package es.exsample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.exsample.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
//import android.graphics.Color;

public class Gururingo extends Activity{
	private SimpleAdapter adapter;
	private ListView listView;
	private SQLiteDatabase db;
	private DataBaseManager mDB;
	private List<HashMap<String,String>> dbList;
	private int selectPosition=0;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_bus);

		mDB = new DataBaseManager(this);
		db =  mDB.getWritableDatabase();

		//mDB.showData(db);

		dbList = getItemList();
		adapter = new SimpleAdapter(getApplicationContext(),
                dbList, R.layout.list_view_item,
                new String[] { "title" }, new int[] { R.id.text_view1 });

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);
        listView.setOnItemClickListener(listenClickItem);

        Button bt = (Button)findViewById(R.id.button1);
        bt.setOnClickListener(listenClick);

		db.close();

	}

	//gitの練習で入れただけの１行

	private ArrayList<HashMap<String, String>> getItemList() {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("select name from " + mDB.TABLE_NAME, null);

        if(c.moveToFirst()){
    		do{
    	        HashMap<String, String> item = new HashMap<String, String>();
    	        String str = c.getString(0);

    	        item.put("title", str);
    			list.add(item);
    		}while(c.moveToNext());}

        return list;
    }

	public OnClickListener listenClick = new OnClickListener(){
		public void onClick(View v) {
			String str = dbList.get(selectPosition).get("title");
			Intent intent = new Intent();
			intent.setClassName("es.exsample","es.exsample.Result");
	        intent.putExtra("selectBus", str);

			startActivity(intent);
		}

	};

	public OnItemClickListener listenClickItem = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			selectPosition = position;
		}

	};
}

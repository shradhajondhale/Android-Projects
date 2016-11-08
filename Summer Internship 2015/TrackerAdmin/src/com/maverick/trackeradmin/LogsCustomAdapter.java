package com.maverick.trackeradmin;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LogsCustomAdapter extends BaseAdapter implements ListAdapter {
	
		
		private ArrayList<String> list = new ArrayList<String>(); 
		private Context context; 



		public LogsCustomAdapter(ArrayList<String> list, Context context) { 
		    this.list = list; 
		    this.context = context; 
		} 

		@Override
		public int getCount() { 
		    return list.size(); 
		} 

		@Override
		public Object getItem(int pos) { 
		    return list.get(pos); 
		} 

		@Override
		public long getItemId(int pos) { 
		    return 0;
		    //just return 0 if your list items do not have an Id variable.
		} 

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
		    
			View view = convertView;
		    if (view == null) {
		        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		        view = inflater.inflate(R.layout.logslist, null);
		    } 
		    
		    TextView logtime = (TextView)view.findViewById(R.id.logtime); 
		    
		    TextView logdate = (TextView)view.findViewById(R.id.logdate); 
		   
		    TextView status = (TextView)view.findViewById(R.id.onoff); 
		    
		   
		     
		    SQLiteDatabase db;
		    
		    db = context.openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
		  
		    Cursor c = db.rawQuery("SELECT * FROM log WHERE Id='"+list.get(position)+"'", null);
		    
		    //c = db.rawQuery("SELECT * FROM student WHERE name = '"+list.get(position) + "'", null);
		   
		    String logtime_s = "";
		    String logdate_s="";
		    String status_s = "";
		    
			   if( c.getCount() == 0 )
				{
					Toast.makeText(context.getApplicationContext(), "The user is not using this app.", Toast.LENGTH_SHORT).show();
			      // return;
				}
				StringBuffer buffer = new StringBuffer();
				
				while( c.moveToNext() )
				{
					//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
					logtime_s = c.getString(2);
					status_s = c.getString(6);
					logdate_s=c.getString(3)+"/"+c.getString(4)+"/"+c.getString(5);
				}
		    
				logtime.setText(logtime_s);
				logdate.setText(logdate_s);
				status.setText(status_s);
	
		    return view; 
		}
	}
		    
		  

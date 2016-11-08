package com.maverick.trackeradmin;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {  
	
	private ArrayList<String> list = new ArrayList<String>(); 
	private Context context; 



	public MyCustomAdapter(ArrayList<String> list, Context context) { 
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
	        view = inflater.inflate(R.layout.list, null);
	    } 
	    
	    TextView time = (TextView)view.findViewById(R.id.time); 
	   
	    TextView address = (TextView)view.findViewById(R.id.address); 
	     
	    SQLiteDatabase db;
	    
	    int  st = 0, et = 0, act = 0;
	   
	    int i=0;
	   
	    db = context.openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
	   Cursor c=db.rawQuery("SELECT * FROM tracks WHERE Id='"+list.get(position)+"'", null);
	    
	    //c=db.rawQuery("SELECT * FROM student WHERE name='"+list.get(position)+"'", null);
	    String Time="";
	    String Address="";
		   if(c.getCount()==0)
			{
				Toast.makeText(context.getApplicationContext(), "The user is not using this app", Toast.LENGTH_SHORT).show();
		//    	return;
			}
			StringBuffer buffer=new StringBuffer();
			while(c.moveToNext())
			{//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
				Time=c.getString(4);
				Address=c.getString(6);
			}
	    
			
			address.setText(Address);
			time.setText(Time);
	   //     time.setText(text)
	    return view; 
	}
}
	    
	  

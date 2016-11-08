package com.maverick.abdurrashidmandal;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.maverick.abdurrashidmandal.R;


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
	    
	    TextView listItemText = (TextView)view.findViewById(R.id.item); 
	   
	    TextView time = (TextView)view.findViewById(R.id.time); 
	    
	  //  ImageView check = (ImageView)view.findViewById(R.id.imageView1);
	    
	    TextView title = (TextView)view.findViewById(R.id.title); 
	    TextView subtitle = (TextView)view.findViewById(R.id.subtitle); 
	   
	  //  title.setText("TITLE");
 
	  
	
		       //listItemText.setText();
		  //     listItemText.setText( Html.fromHtml("<font color =#A9A9A9>" +list.get(position) + "</font>") );
		     //  check.setImageResource(R.drawable.btn_check_buttonless_off);
		   //    time.setText( Html.fromHtml("<font color =#A9A9A9>"   + "  time "  + "</font>"));   
		  
		    
	  	     
	    return view; 
	}
}
	    
	    
	

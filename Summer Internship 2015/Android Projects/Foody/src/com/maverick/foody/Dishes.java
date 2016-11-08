package com.maverick.foody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.maverick.foody.R;
import com.maverick.foody.FadeOnScroll.OnScrollViewListener;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Dishes extends ActionBarActivity implements OnClickListener {

	//-----------FOR UI ONLY
	
	int i;
	TextView dishname;
	TextView dishcost;
	
	String[] dishname_ar;// = {"Dal Tadka", "Dal Makhani", "Dum Aloo", "Mix Veg", "Palak Paneer", "Matar Paneer", "Paneer Butter Masala", "Paneer Makhani", "Tandoori Chicken", "Chicken Tikka", "Chicken Curry", "Tandoori Roti", "Naan", "Plain Rice", "Fried Rice"};
	String[] dishcost_ar;// = {"90", "110", "140", "140", "150", "150", "160", "160", "220", "160", "220", "15", "25", "50", "100"};
	
	
	//-----------FOR UI ONLY//
	
	String[] dish_name_final;
	String[] dish_cost_final;
	
	String[] dish_name_s;
	String[] dish_cost_s; 
	
	String category;
	
	
	ListView dish_list;
	SimpleAdapter adapter;
	
	
	SQLiteDatabase db;
	
	ActionBar mActionBar;
	
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		

		//Active Overlay for ActionBar before set my content in the activity
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.activity_dishes);
				
       category = getIntent().getStringExtra("category");
      //Toast.makeText(getApplicationContext(), ""+category, Toast.LENGTH_SHORT).show();
        
      //mahit ni-> dish_list = (ListView)findViewById(R.id.disheslist);
    

		final ColorDrawable cd = new ColorDrawable(Color.parseColor("#85153B"));
		getActionBar().setBackgroundDrawable(cd);
		getActionBar().setDisplayShowHomeEnabled(false);
		// AFTER INTERNET CONNECTION--------------------------
       db = openOrCreateDatabase("Hotel", Context.MODE_PRIVATE, null);
       
       int i = 0;
       
		Cursor c = db.rawQuery("SELECT * FROM dishes ", null);
		dish_name_s = new String[c.getCount()];
		dish_cost_s = new String[c.getCount()];
		
		if( c.getCount() == 0 )
		{
			Toast.makeText(getApplicationContext(), "The user is not using this app.", Toast.LENGTH_SHORT).show();
		      
		}
		   
			StringBuffer buffer = new StringBuffer();
			
			while(c.moveToNext())
			{
				if(c.getString(3).equals(category)){
				//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
				dish_name_s[i] = c.getString(2);
				dish_cost_s[i] = c.getString(4);
				 
				 //list.add(c.getString(0)) ;
		         i++;
			}}
        
			dish_name_final= new String[i];
			dish_cost_final= new String[i];
		
			//Toast.makeText(getApplicationContext(), "count is"+i, Toast.LENGTH_SHORT).show();
			
			for(int j=0;j<i;j++){
				
				dish_name_final[j]=dish_name_s[j];
				dish_cost_final[j]=dish_cost_s[j];
				
			}
			
			
       // AFTER INTERNET CONNECTION----------------------------*/
        
       /*MAHIT NI------------------ 
        
    	List<HashMap<String,String>> dishList = new ArrayList<HashMap<String,String>>(); 
    	
	        for(int j = 0 ; j < i ; j++){
	        	
	            HashMap<String, String> hmap = new HashMap<String,String>();
	            
	            hmap.put("dishname", "" + dish_name[j]);
	            hmap.put("dishcost", "₹" + dish_cost[j]);	            
	            dishList.add(hmap);
	        }
	 
	        // Keys used in Hashmap
	        String[] from = {"dishname", "dishcost" };
	 
	        // Ids of views in listview_layout
	        int[] to = { R.id.dishname, R.id.dishcost};
	 
		 
		 
	        adapter = new SimpleAdapter(getBaseContext(), dishList, R.layout.disheslist, from, to);	  
	     
	        dish_list.setAdapter(adapter);
	        
	        MAHIT NI---------------------------------*/
		
		 LinearLayout disheslistview = (LinearLayout) findViewById(R.id.disheslist);

	      //Toast.makeText(getApplicationContext(), ""+dish_name_final.length,Toast.LENGTH_SHORT).show();
	        for ( i = 0; i < dish_name_final.length; i++) {
	        	
	        	LinearLayout.LayoutParams nameparams = new LinearLayout.LayoutParams(
		                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        	LinearLayout.LayoutParams costparams = new LinearLayout.LayoutParams(
		                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        	
	        	LinearLayout dishlayout = new LinearLayout(this);
	        	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		        dishlayout.setLayoutParams(params);
		        dishlayout.setId(i);
		        dishlayout.setOrientation(LinearLayout.HORIZONTAL);
	            dishlayout.setGravity(Gravity.CENTER);

	            //Toast.
	        	
	            dishname = new TextView(this);
	            dishname.setText("" + dish_name_final[i]);
	            dishname.setTextSize(16);
	            dishname.setAllCaps(true);
	            dishname.setTextColor(Color.parseColor("#6e6e6e"));
	            dishname.setLayoutParams(nameparams);
	            dishname.setPadding(20, 20, 20, 20);
	            
	            
	            dishcost = new TextView(this);
	            dishcost.setText("₹ " + dish_cost_final[i]);
	            dishcost.setTextSize(14);
	            dishcost.setTextColor(Color.parseColor("#6e6e6e"));
	            dishcost.setLayoutParams(costparams);
	            dishcost.setPadding(20, 20, 20, 20);


	            dishlayout.addView(dishname);
	            dishlayout.addView(dishcost);

	            disheslistview.addView(dishlayout);
	          
	            dishlayout.setOnClickListener(this);
	        }
			   
	        
	}
	
	
	
	public void textonclick(){
		
		Intent intent = new Intent( Dishes.this, DishDetails.class);
    	//intent.putExtra("dish", dishnametosend);
    	startActivity(intent);
    	overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dishes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		
		
		 
		    	view.startAnimation(buttonClick);
		    	Intent i = new Intent( Dishes.this,DishDetails.class);
		    	i.putExtra("dish",dish_name_final[view.getId()]);
		    	i.putExtra("cost",dish_cost_final[view.getId()]);
		    	startActivity(i);
		    	//textonclick();
		   
		
	}
}

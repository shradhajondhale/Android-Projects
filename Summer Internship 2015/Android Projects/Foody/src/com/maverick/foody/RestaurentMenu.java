package com.maverick.foody;

import java.util.HashSet;
import java.util.Set;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.maverick.foody.R;
import com.maverick.foody.FadeOnScroll.OnScrollViewListener;

public class RestaurentMenu extends ActionBarActivity implements OnClickListener {

	int j;
String res_name;

String[] Rest;
String[] Dish;
String[] Category;
String[] Rate;
String[] Desc;
String[] category_ar;

SQLiteDatabase db;

ListView categorymenu_list;
ArrayAdapter<String> adapter;


AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);

ActionBar mActionBar;


//------------FOR UI ONLY

String categorys[] = {"Appetizers", "Soups", "Vegetarian", "Non-vegetarian", "Desserts", "Beverages"};

//------------FOR UI ONLY//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//Active Overlay for ActionBar before set my content in the activity
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.activity_restaurent_menu);
		
	   String rest_name = getIntent().getStringExtra("restname");
	
	   //   TextView res_heading = (TextView)findViewById(R.id.resnametext);
	 //  res_heading.setText("" + rest_name);
	   
		StrictMode.enableDefaults();
	//	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FA5882")));
		
		final ColorDrawable cd = new ColorDrawable(Color.parseColor("#85153B"));
		
		mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(cd);
		
		cd.setAlpha(0);

	
		mActionBar.setDisplayShowHomeEnabled(false); //
	//	mActionBar.setTitle(Html.fromHtml("<b><font color='#1e6f8c'>Action Bar Scroll</font></b>"));
		
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		

				
		FadeOnScroll scrollView = (FadeOnScroll) findViewById(R.id.scroll_view);
		scrollView.setOnScrollViewListener(new OnScrollViewListener() {
			
			@Override
			public void onScrollChanged( FadeOnScroll v, int l, int t, int oldl, int oldt ) {
				
				cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
			}

			private int getAlphaforActionBar( int scrollY ) {
				
				int minDist = 0, maxDist = 650;
				
				if(scrollY > maxDist){ 
					return 255;
					}
				else if(scrollY < minDist){
					return 0;
					}
				else {
					int alpha = 0;
					alpha = (int)  ( ( 255.0 / maxDist )*scrollY );
					return alpha;
				}
			}
		});
		
	
		//res_name = getIntent().getStringExtra("ResName");
		//getActionBar().setTitle("" + res_name + "'s Menu");
		
		
	//  After internet connection-------------------------------------------
		db = openOrCreateDatabase("Hotel", Context.MODE_PRIVATE, null);
		
	//	categorymenu_list = (ListView)findViewById(R.id.listView1);
     
		
		int i = 0;
		 
		Cursor c = db.rawQuery("SELECT * FROM dishes", null);
		
		Rest = new String[c.getCount()];
		Dish = new String[c.getCount()];
		Category = new String[c.getCount()];
		Rate = new String[c.getCount()];
		Desc = new String[c.getCount()];
		
		if( c.getCount() == 0 )
		{
			Toast.makeText(getApplicationContext(), "not in db", Toast.LENGTH_SHORT).show();
		      
		}
		   
			StringBuffer buffer = new StringBuffer();
			
			while(c.moveToNext())
			{
				//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
				 Rest[i] = c.getString(1);
				 Dish[i] = c.getString(2);
				 Category[i] = c.getString(3);
				 Rate[i] = c.getString(4);
				 Desc[i] = c.getString(5);
				 
				 //list.add(c.getString(0)) ;
		         i++;
			}	 
	
			
			//for removing double entries from array
			
			Set<String> set = new HashSet<String>();
			
	        for(int j = 0; j < Category.length ; j++)
	        {
	            if(set.add(Category[j]) == false)
	            {
	            }
	        }
	       
	        category_ar = new String[set.size() + 1];
	        
	        category_ar = set.toArray(new String[set.size()]);
	        
	        addTextViews(ll);
			
	        //Toast.makeText(getApplicationContext(), "" + Category_ar.length, Toast.LENGTH_SHORT).show();
	        	
	        	/* 	
	      	 adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.menulist, R.id.categorymenu, categorys);
	      
	------------------------------      after internet connection	*/        
	      	        
	      	/* to cancel-------------------------------
	      	
	      	 categorymenu_list.setAdapter(adapter);
	      	 categorymenu_list.setOnItemClickListener(new OnItemClickListener(){
	      	      		

	     	@Override
	     	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	     		
	     			view.startAnimation(buttonClick);
	     			String category = (String) categorymenu_list.getItemAtPosition(position);
	     			Intent intent = new Intent(RestaurentMenu.this, Dishes.class);
	     			intent.putExtra("category", category);
	     			startActivity(intent);
	     		    overridePendingTransition(R.anim.abc_fade_out, R.anim.abc_fade_in);

	     		}
	      	});
			
	----------------------------to cancel*/
}

	
	
	
     private void addTextViews(LinearLayout ll) {
    
    	 
		 for (int i = 0; i < category_ar.length; i++) {
			 j=i;
			TextView tv = new TextView(this);
			tv.setText(category_ar[i]);
			tv.setId(i);
			tv.setTextSize(18);
			tv.setPadding(50, 50, 50, 50);
			tv.setTextColor(Color.parseColor("#6e6e6e"));
			tv.setAllCaps(true);
			tv.setBackgroundColor(Color.rgb(255-10*i, 255-10*i, 255-10*i)); //just for fun , varying back grounds
			tv.setGravity(Gravity.CENTER);
			tv.setOnClickListener(this);
			/*
			tv.setOnClickListener(new View.OnClickListener() {

				    @Override
				    public void onClick(View v) {

				    	//v.startAnimation(buttonClick);
				    	//Intent i = new Intent( RestaurentMenu.this, Dishes.class);
				    	Toast.makeText(getApplicationContext(), ""+categorys[j], Toast.LENGTH_SHORT).show();
				    	//startActivity(i);
				    	//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
				    }
				  });
			*/
			ll.addView(tv);
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurent_menu, menu);
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
		
		//Toast.makeText(getApplicationContext(), ""+category_ar[view.getId()], Toast.LENGTH_SHORT).show();	
		
		view.startAnimation(buttonClick);
    	Intent i = new Intent( RestaurentMenu.this, Dishes.class);
    	i.putExtra("category",category_ar[view.getId()]);
    	startActivity(i);
    	overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		
		
		
		
	}
}

package com.maverick.foody;

import com.maverick.foody.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class DishDetails extends ActionBarActivity {
	String dish;
	String cost; 
	
	TextView dishname_t, dishcost_t, dishqty_t;
	
	int dishqty_n = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_details);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#85153B")));

		 dish = getIntent().getStringExtra("dish");
		 cost = getIntent().getStringExtra("cost");
		 
		 
		 dishname_t = (TextView)findViewById(R.id.dishitemname);
		 dishcost_t = (TextView)findViewById(R.id.itemprice);
		 dishqty_t = (TextView)findViewById(R.id.itemqty);
		 
		 dishname_t.setText("" + dish);
		 dishcost_t.setText("₹ " + cost);
		 
	//	Toast.makeText(getApplicationContext(), "" + cost, Toast.LENGTH_SHORT).show();
		
		
	}
	
	
	public void addqty(View v){
		
		dishqty_n = dishqty_n + 1;
		dishqty_t.setText("" + dishqty_n);
		
	}
	
	public void subtractqty(View v){
		
		if(dishqty_n != 0){
		    dishqty_n = dishqty_n - 1;
		}
		else{
			dishqty_n = 0;
		}
		dishqty_t.setText("" + dishqty_n);
	}
	
	public void enterqty(View v){
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dish_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent i = new Intent(DishDetails.this, Cart.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.maverick.foody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.maverick.foody.R;

public class FoodAroundMe extends ActionBarActivity {
	
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	ListView restaurents_list;
	
	RatingBar resratingbar;
	
	SimpleAdapter adapter;
 	ProgressDialog dialog1;

 
	/*------------------------FOR UI ONLY
	
	// Array of integers points to images stored in /res/drawable-ldpi/
 
	int[] res_photo = new int[]{  R.drawable.a,  R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};
   
	String[] res_names = {"Shanghai Salsa", "Chung Fa", "Red", "KFC", "Juicy Hut", "Rolls Mania", "Café Coffee Day", "Dominos Pizza", "Baskin Robbins", "Slice Of Heaven"};
    String[] res_type = {"Mexican", "Chinese", "Continental", "American", "Juices", "Fast Food", "Cafe", "Pizza", "Desserts", "North Indian"};
    String[] res_ratings = {"4.2", "4.4", "3.4", "2.2", "4.1", "4.1", "2.4", "3.6", "3.8", "3.6"};
    String[] opentime = {"12 Noon", "11:30 AM", "11:30 AM", "9:00 AM", "10:00 AM", "12 Noon", "9:00 AM", "11:00 AM", "11:00 AM", "10:00 AM"};
    String[] closetime = {"10:30 PM", "10:00 PM", "11:30 PM", "11:00 PM", "10:00 PM", "11 PM", "11 PM", "11:00 PM", "11:00 PM", "10:00 PM"};
    String[] del_charges = {"120", "130", "300", "50", "0", "0", "15", "40", "0", "50"};
    String[] min_del_cost = {"500", "600", "1000", "500", "50", "75", "80", "100", "120", "80"};
    String[] del_time = {"20 min", "1 hr", "1 hr", "20 min", "15 min", "20 min", "10 min", "20 min", "15 min", "30 min"};
	
 ------------------------FOR UI ONLY)*/
	
	
	// After Internet Connection
	 
	String[] res_names;
    String[] res_type;
    String[] res_ratings;
    String[] opentime;
    String[] closetime;
    String[] del_charges;
    String[] min_del_cost;
    String[] del_time;
    String[] PhotoId;
    String[] Lon_res;
    String[] Lat_res;
    String[] res_photo_str;
    
    
  int[] res_photo={R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b};
    
   // After Internet Connection
    String[] Rest;
    String[] Dish;
    String[] Category;
    String[] Rate;
    String[] Desc;
    SQLiteDatabase db;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_around_me);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#85153B")));
		getActionBar().setDisplayShowHomeEnabled(false);
		
		StrictMode.enableDefaults();
		
		restaurents_list=(ListView)findViewById(R.id.restaurentslist);
		 resratingbar = (RatingBar) findViewById(R.id.ratingbar);
		
		 db = openOrCreateDatabase("Hotel", Context.MODE_PRIVATE, null);
         db.execSQL("CREATE TABLE IF NOT EXISTS dishes(Id VARCHAR,Rest VARCHAR,Dish VARCHAR,Category VARCHAR,Rate VARCHAR,Desc VARCHAR);"); 	
		 
		 
	   //String rating = String.valueOf(resratingbar.getRating());  
		
/*-----------FOR UI ONLY (Temporary)
         
         
     	List<HashMap<String,String>> resList = new ArrayList<HashMap<String,String>>();  
	        for(int j = 0 ; j < 10 ; j++){
	        	
	            HashMap<String, String> hmap = new HashMap<String,String>();
	            
	            hmap.put("resname", "" + res_names[j]);
	            hmap.put("restype", "" + res_type[j]);
	            hmap.put("resratings", "" + res_ratings[j]);
	            hmap.put("restimings", "" + opentime[j] + " to " + closetime[j]);
	            hmap.put("deltime", " ( " + del_time[j] + " )");
	            hmap.put("delcharges", "Delivery: ₹" + del_charges[j]);
	            hmap.put("mindelcost", "Min. ₹" + min_del_cost[j]);
	            hmap.put("resphoto", Integer.toString( res_photo[j]) );
	            
	            resList.add(hmap);
	        }
	 
	        // Keys used in Hashmap
	        String[] from = {"resname", "restype", "resratings", "restimings", "deltime", "delcharges", "mindelcost", "resphoto"};
	 
	        // Ids of views in listview_layout
	        int[] to = { R.id.resname, R.id.restype, R.id.resratings, R.id.restimings, R.id.deliverytime, R.id.deliverycharges, R.id.mindeliverycost, R.id.resphoto};
	 
		 
		 
	       adapter = new SimpleAdapter(getBaseContext(), resList, R.layout.restaurentslist, from, to);	  
	     
	       restaurents_list.addHeaderView(new View(getApplicationContext()));
	       restaurents_list.addFooterView(new View(getApplicationContext()));
	        
	        
	       restaurents_list.setAdapter(adapter);
      	
	       restaurents_list.setOnItemClickListener(new OnItemClickListener() {

	    	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    		  
	    		  Intent i = new Intent(FoodAroundMe.this, RestaurentMenu.class);
	    		  String rest_name = res_names[position];
	    		//  Toast.makeText(getApplicationContext(), ""+rest_name, Toast.LENGTH_SHORT).show();
	    		  i.putExtra("restname", rest_name);
	    		//  Toast.makeText(getApplicationContext(), "" + rest_name, Toast.LENGTH_SHORT).show();
				  startActivity(i);
				  overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);	   	
	    	  }
	    	}); 	 			  
         
         
         
-----------FOR UI ONLY (Temporary)// */ 
         
         
        
	     new res_details().execute();
	     
         
        
	     
}

	public class res_details extends AsyncTask<Void, Void, Void>{
	   	 
		 String result = "";
	     InputStream isr = null;
	     
	   	 ProgressDialog dialog = new ProgressDialog(FoodAroundMe.this);

	   	  @Override
	   	  protected void onPreExecute() {
	   		  
	   		dialog.setMessage("Please wait..."); 
	   		dialog.setCanceledOnTouchOutside(false);
			dialog.show();

	   	  }

	   	  @Override
	   	  protected Void doInBackground(Void... params) {
	   		  
	   		try{
	         	 
	          	HttpClient httpclient = new DefaultHttpClient();
	          	HttpPost httppost = new HttpPost("http://www.spmaverick.com/FOODONLINE/restaurants.php"); //YOUR PHP SCRIPT ADDRESS
	          	
	          	  
	          	//for response
	          	HttpResponse response = httpclient.execute(httppost);
	          	HttpEntity entity = response.getEntity();
	          	isr = entity.getContent();
	          	 
	          	}catch(Exception e){
	          	//Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
	          	}
	   		return null;
	}
	   	  protected void onPostExecute(Void unused) {

	   		  dialog.dismiss();
	   		 
	   		 try{
		          	
	          	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
	          	 StringBuilder sb = new StringBuilder();
	          	 String line = null;
	          	 
	          	 while ((line = reader.readLine()) != null) {
	          	 
	          		 sb.append(line + "\n");
	          	}
	          	 isr.close();
	          	 result = sb.toString();
	          //	 Toast.makeText(getApplicationContext(), ""+result,Toast.LENGTH_SHORT).show();
	          	}catch(Exception e){
	          		Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
	          	}
	      
	    //  Toast.makeText(getApplicationContext(), " " + result, Toast.LENGTH_LONG).show();
	      
	     
	          try {
	          	JSONArray jArray = new JSONArray(result);
	   
	          	res_names = new String[jArray.length( )];
	          	res_type = new String[jArray.length( )];
	          	opentime = new String[jArray.length( )];
	          	closetime = new String[jArray.length( )];
	          	del_charges = new String[jArray.length( )];
	          	min_del_cost = new String[jArray.length( )];
	          	del_time = new String[jArray.length( )];
	            
	          	PhotoId = new String[jArray.length( )];
	            Lon_res= new String[jArray.length( )];
	            Lat_res= new String[jArray.length( )];
	          	res_photo_str=new String[jArray.length( )];
	          	
	          	for( int i = 0 ; i < jArray.length( ); i++ ){
	          		
	          		JSONObject json = jArray.getJSONObject(i);
	 	      	 	 res_names[i] = json.getString("Name");
	 	      	 	 res_type[i] = json.getString("Type");
	 	      	   //res_ratings[i] = json.getString("Id");
	 	      	     opentime[i] = json.getString("Opening");
	 	      	     closetime[i] = json.getString("Closing");
	 	           	 del_charges[i] = json.getString("DelCharge");
	 	      	     min_del_cost[i] = json.getString("MinCost");
	 	      	     del_time[i] = json.getString("DelTime");
	 	      	     
	 	      	     PhotoId[i]=json.getString("PhotoId");
	 	      	     Lon_res[i]=json.getString("Longitude");
	 	      	     Lat_res[i]=json.getString("Latitude");
	 	      	     
	          	}
	          	
	           
	        
	          	List<HashMap<String,String>> resList = new ArrayList<HashMap<String,String>>();  
 		        for(int j = 0 ; j < jArray.length( ) ; j++){
 		        	
 		            HashMap<String, String> hmap = new HashMap<String,String>();
 		            
 		            hmap.put("resname", "" + res_names[j]);
 		            hmap.put("restype", "" + res_type[j]);
 		         // hmap.put("resratings", "" + res_ratings[i]);
 		            hmap.put("restimings", "" + opentime[j] + " to " + closetime[j]);
 		            hmap.put("deltime", " ( " + del_time[j] + " )");
 		            hmap.put("delcharges", "Delivery: ₹" + del_charges[j]);
 		            hmap.put("mindelcost", "Min. ₹" + min_del_cost[j]);
 		            hmap.put("resphoto", Integer.toString( res_photo[j]) );
 		            
 		            resList.add(hmap);
 		        }
 		 
 		        // Keys used in Hashmap
 		        String[] from = {"resname", "restype", "restimings", "deltime", "delcharges", "mindelcost","resphoto"};
 		 
 		        // Ids of views in listview_layout
 		        int[] to = { R.id.resname, R.id.restype, R.id.restimings, R.id.deliverytime, R.id.deliverycharges ,R.id.mindeliverycost,R.id.resphoto};
 		 
 			 
 			 
 		        adapter = new SimpleAdapter(getBaseContext(), resList, R.layout.restaurentslist, from, to);	  
 		     
 		       restaurents_list.addHeaderView(new View(getApplicationContext()));
 		       restaurents_list.addFooterView(new View(getApplicationContext()));
 		        
 		        
 		        restaurents_list.setAdapter(adapter);
	          	
 		        restaurents_list.setOnItemClickListener(new OnItemClickListener() {

 		    	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 		    		  
 		    		   // Toast.makeText(getApplicationContext(), ""+(position-1), Toast.LENGTH_SHORT).show();
 		    	       	// String rest_name = (String) restaurents_list.getItemAtPosition(position);
 		    		   String rest_name = res_names[position-1];
 		    	     //  Toast.makeText(getApplicationContext(), "" + rest_name, Toast.LENGTH_SHORT).show();
 		    	       new get_res_details(rest_name).execute();

 		    	  }
 		    	}); 	 			  
	         
 		        
 		       for(int i=0;i<jArray.length( );i++){

	                ImageLoaderTask imageLoaderTask = new ImageLoaderTask();

	                HashMap<String, Object> hm = new HashMap<String, Object>();
	                hm.put("flag_path","http://www.spmaverick.com/FOODONLINE/images/"+PhotoId[i]);
	                hm.put("position", i);
	                
	                // Starting ImageLoaderTask to download and populate image in the listview
	                imageLoaderTask.execute(hm);
	            }
 		     
	          	} catch (Exception e) {
	          		Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_SHORT).show();
	         	 }
	         
	         
	         
	         	          
	          
	    // Toast.makeText(getApplicationContext(), "CON:"+congress_s+"BJP:"+bjp_s+"APP:"+aap_s, Toast.LENGTH_SHORT).show();    
	   	  
	   	  }
	}
	
	
	
	 public class get_res_details extends AsyncTask <Void, Void, Void>{
		 
	   	  InputStream isr = null;
	   	  String result = "";	
	   	  String res_name_to_send;
	   	  
	     	ProgressDialog dialog = new ProgressDialog(FoodAroundMe.this);

	   	    public get_res_details(String res_name_to_send){ 
	   	    	
	   	    	this.res_name_to_send = res_name_to_send;
	   	    	
	   	    }

	   	    @Override
	   	    protected void onPreExecute() {
	   	    	
       	      dialog.setMessage("Loading...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/FOODONLINE/getmenu.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("Rest", res_name_to_send));
				  
				  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				  HttpResponse response = httpclient.execute(httppost);
				  HttpEntity entity = response.getEntity();
				  isr = entity.getContent(); 	
				  
				}catch(Exception e){
				}  	
			return null;
	   }
	   	 protected void onPostExecute(Void unused) {
	   		 
	   	 
	     dialog.dismiss();
		 try{
				 
			 
	      	 BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
	      	 StringBuilder sb = new StringBuilder();
	      	 String line = null;
	      	 
	      	 while ((line = reader.readLine()) != null) {
	      	 
	      		 sb.append(line + "\n");
	      	}
	      	 isr.close();
	      	 result = sb.toString();
	      	 
	      	}catch(Exception e){
	      	 
	      	}
	       // Toast.makeText(getApplicationContext(), "" + UserName, Toast.LENGTH_SHORT).show();
	       //Toast.makeText(getApplicationContext(), "Result" + result, Toast.LENGTH_LONG).show();
		
	       
		 
		 char check_char = result.charAt(0);
		 int result_n = Character.getNumericValue(check_char);
	       if(result_n == 0){
		 		 
	    	   
		 		  Toast.makeText(getApplicationContext(), "No Data Present.", Toast.LENGTH_SHORT).show();
		 	  }
		 	  else{
		 		  //Toast.makeText(getApplicationContext(), "User daata is present", Toast.LENGTH_SHORT).show();

		 		  
	  
	      try {
	      	JSONArray jArray = new JSONArray(result);

	      	Rest = new String[jArray.length( )];
	    	Dish = new String[jArray.length( )];
	    	Category = new String[jArray.length( )];
	    	Rate = new String[jArray.length( )];
	    	Desc = new String[jArray.length( )];
	    	
	    	 db.delete("dishes", null, null);
	    	   
	    	 db = openOrCreateDatabase("Hotel", Context.MODE_PRIVATE, null);
	         db.execSQL("CREATE TABLE IF NOT EXISTS dishes(Id VARCHAR,Rest VARCHAR,Dish VARCHAR,Category VARCHAR,Rate VARCHAR,Desc VARCHAR);"); 	
	    	
	         if( jArray.length( ) == 0){
	    		 
	    		 Toast.makeText(getApplicationContext(), "No data sent.", Toast.LENGTH_SHORT).show();
	    	 }
	    	 else{
	            for( int i = 0 ; i < jArray.length( ); i++ ){
	      	 
	      		JSONObject json = jArray.getJSONObject(i);
	      		 db.execSQL("INSERT INTO dishes VALUES('"+ i +"','"+json.getString("Restaurant")+"','"+json.getString("Dish")+"','"+json.getString("Category")+"','"+json.getString("Rate")+"','"+json.getString("Describtion")+"');"); //no initial phone no and not registered   
	      		 //Toast.makeText(getApplicationContext(), "Res-"+json.getString("Restaurant")+"Dish - "+json.getString("Dish")+"Ctegory-"+json.getString("Category")+" Rate-"+json.getString("Rate")+"Desc-"+json.getString("Describtion"), Toast.LENGTH_SHORT).show();
	      		
	      	}	
	    	 }
	
	      }catch(Exception e){
			Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
			
		}
	          Intent i = new Intent(FoodAroundMe.this, RestaurentMenu.class);
			  i.putExtra("restname",res_name_to_send );
	          startActivity(i);
	       
		  }
 }
    }
	

	 
	 private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{

		    @Override
		    protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {

		        InputStream iStream=null;
		        String imgUrl = (String) hm[0].get("flag_path");
		        int position = (Integer) hm[0].get("position");

		        URL url;
		        try {
		        	//Toast.makeText(getApplicationContext(), ""+imgUrl,Toast.LENGTH_SHORT).show();
		            url = new URL(imgUrl);

		            // Creating an http connection to communicate with url
		            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		            // Connecting to url
		            urlConnection.connect();

		            // Reading data from url
		            iStream = urlConnection.getInputStream();

		            // Getting Caching directory
		            File cacheDirectory = getBaseContext().getCacheDir();

		            // Temporary file to store the downloaded image
		            File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+position+".png");

		            // The FileOutputStream to the temporary file
		            FileOutputStream fOutStream = new FileOutputStream(tmpFile);

		            // Creating a bitmap from the downloaded inputstream
		            Bitmap b = BitmapFactory.decodeStream(iStream);

		            // Writing the bitmap to the temporary file as png file
		            b.compress(Bitmap.CompressFormat.PNG,100, fOutStream);

		            // Flush the FileOutputStream
		            fOutStream.flush();

		           //Close the FileOutputStream
		           fOutStream.close();

		            // Create a hashmap object to store image path and its position in the listview
		            HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

		            // Storing the path to the temporary image file
		            hmBitmap.put("flag",tmpFile.getPath());

		            // Storing the position of the image in the listview
		            hmBitmap.put("position",position);

		            // Returning the HashMap object containing the image path and position
		            return hmBitmap;

		        }catch (Exception e) {
		            e.printStackTrace();
		        }
		        return null;
		    }

		    @Override
		    protected void onPostExecute(HashMap<String, Object> result) {
		        // Getting the path to the downloaded image
		        String path = (String) result.get("flag");

		        // Getting the position of the downloaded image
		        int position = (Integer) result.get("position");
          
		      //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
          
		        
		        try{
		        // Getting adapter of the listview
		        	res_photo_str[position]=path;
		        	
		      //  	ListAdapter adapter1 = restaurents_list.getAdapter();

		        // Getting the hashmap object at the specified position of the listview
		      // HashMap<String, String> hm = (HashMap<String, String>) adapter1.getItem(position);

		        //Toast.makeText(getApplicationContext(), ""+hm.get("resname"),Toast.LENGTH_SHORT).show();
		        
		        // Overwriting the existing path in the adapter
		       // hm.put("flag",path);
		       // hm.put("resphoto",""+path);
			  //  Toast.makeText(getApplicationContext(), "type -"+hm.get("restype")+" path -"+path, Toast.LENGTH_SHORT).show();

		        // Noticing listview about the dataset changes
		        //((SimpleAdapter) adapter1).notifyDataSetChanged();
		        
		        
		        if(position==(res_names.length-1)){
		        	
		        	
		         	dialog1 = new ProgressDialog(FoodAroundMe.this);
	    	     	 dialog1.setMessage("Loading...");
                    dialog1.setCanceledOnTouchOutside(false);
                    dialog1.show();
                    
		        	new CountDownTimer(2000, 1000) {
		    	    

		       			public void onTick(long millisUntilFinished) {
	                        
		       			}

		         	    public void onFinish() {
		         	    	
		         	    	dialog1.dismiss();   
		         	   	ReloadList();
		         		    	
		         		  }
		          }.start();
		        
		        	
		        	
		        	
		        	
		        	//((SimpleAdapter) adapter1).notifyDataSetChanged(
		        
		        	}
		        	
		        	
		   
		        
		        
		        }catch(Exception e){
		        	Toast.makeText(getApplicationContext(), "error while downloading image", Toast.LENGTH_SHORT).show();
		        	
		        }
		    }
		}
	 
	 void ReloadList(){
		 
		 	List<HashMap<String,String>> resList = new ArrayList<HashMap<String,String>>();  
		        for(int j = 0 ; j < res_names.length ; j++){
		        	
		            HashMap<String, String> hmap = new HashMap<String,String>();
		            
		            hmap.put("resname", "" + res_names[j]);
		            hmap.put("restype", "" + res_type[j]);
		         // hmap.put("resratings", "" + res_ratings[i]);
		            hmap.put("restimings", "" + opentime[j] + " to " + closetime[j]);
		            hmap.put("deltime", " ( " + del_time[j] + " )");
		            hmap.put("delcharges", "Delivery: ₹" + del_charges[j]);
		            hmap.put("mindelcost", "Min. ₹" + min_del_cost[j]);
		            hmap.put("resphoto", res_photo_str[j] );
		            
		            resList.add(hmap);
		        }
		 
		        // Keys used in Hashmap
		        String[] from = {"resname", "restype", "restimings", "deltime", "delcharges", "mindelcost","resphoto"};
		 
		        // Ids of views in listview_layout
		        int[] to = { R.id.resname, R.id.restype, R.id.restimings, R.id.deliverytime, R.id.deliverycharges ,R.id.mindeliverycost,R.id.resphoto};
		 
		        adapter = new SimpleAdapter(getBaseContext(), resList, R.layout.restaurentslist, from, to);	  
		     
		       restaurents_list.addHeaderView(new View(getApplicationContext()));
		       restaurents_list.addFooterView(new View(getApplicationContext()));
		        
		        
		        restaurents_list.setAdapter(adapter);
          	
		        restaurents_list.setOnItemClickListener(new OnItemClickListener() {

		    	  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    		  
		    		  //  Toast.makeText(getApplicationContext(), ""+(position-2), Toast.LENGTH_SHORT).show();

		    	       	// String rest_name = (String) restaurents_list.getItemAtPosition(position);
		    		   String rest_name = res_names[position-2];
		    	     //  Toast.makeText(getApplicationContext(), "" + rest_name, Toast.LENGTH_SHORT).show();
		    	       new get_res_details(rest_name).execute();

		    	  }
		    	}); 	 			  
           
		 
	 }
	 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_around_me, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.slidingdrawer) {
			
        	Intent intent = new Intent(this, SlidingDrawer.class);
	    	startActivity(intent);
	    	
         //	getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        	
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

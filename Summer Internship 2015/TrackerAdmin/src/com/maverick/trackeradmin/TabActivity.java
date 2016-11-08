package com.maverick.trackeradmin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TabActivity extends ActionBarActivity implements OnMarkerClickListener, LocationListener, OnItemSelectedListener {
TabHost th;
private GoogleMap mMap;         
Marker marker;
Polyline line;
Context context;
String[] Lon;
String[] Lat;
String[] Time;
String[] Address;
String[] Id;
String[] Date;
String Date_today;
SQLiteDatabase db; 

String[] Timelog;
String[] Datelog;
String[] Monthlog;
String[] Yearlog;
String[] Statuslog;

int leave=0; 


//net connection
Boolean isInternetPresent;
ConnectionDetector cd;


// Static LatLng
LatLng startLatLng = new LatLng(30.707104, 76.690749);
LatLng endLatLng = new LatLng(30.721419, 76.730017);
String UserName;
LocationManager locationManager;

ListView listView;
ListAdapter adapter;  
ArrayList<String> dataItems = new ArrayList<String>();	
Spinner dates_dropdown, dates_dropdown_list;


@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
		
		
		 listView = (ListView) findViewById(R.id.list);
		
         UserName = getIntent().getStringExtra("UserName");
		 
		 getActionBar().setTitle("" + UserName + "'s Tracked Data");

		 db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
		
		 int i = 0;
		 
		 Cursor c = db.rawQuery("SELECT * FROM tracks", null);
		 
		 Lon = new String[c.getCount()];
		 Lat = new String[c.getCount()];
		 Time = new String[c.getCount()];
		 Address = new String[c.getCount()];
		 Id = new String[c.getCount()];
		 Date = new String[c.getCount()];
		 
		   if( c.getCount() == 0 )
			{
				Toast.makeText(getApplicationContext(), "The user is not using this app.", Toast.LENGTH_SHORT).show();
		      // 	return;
			}
		   
			StringBuffer buffer = new StringBuffer();
			
			while(c.moveToNext())
			{
				//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
				 Lon[i] = c.getString(2);
				 Lat[i] = c.getString(3);
				 Time[i] = c.getString(4);
				 Address[i] = c.getString(6);
				 Id[i] = c.getString(0);
				 Date[i] = c.getString(5);
				 //list.add(c.getString(0)) ;
		         i++;
			}
			
			
	         
		        Set<String> set = new HashSet<String>();
		         
		        for(int j = 0; j < Date.length ; j++)
		        {
		            //If same integer is already present then add method will return FALSE
		            if(set.add(Date[j]) == false)
		            {
		               // System.out.println("Duplicate element found : " + array[i]);
		            }
		        }
		        
		        
		        
		        String[] Date_ar = new String[set.size()+1];
		        
		        		Date_ar = set.toArray(new String[set.size()]);
		        	    int[] a = new int[Date_ar.length];
		        	    for(int q = 0; q < a.length; q++)
		        	    {
		        	        a[q] = Integer.parseInt(Date_ar[q]);
		        	    }
		        	    Arrays.sort(a);
		        	    
		        	    int len = 0;
		        	    for(int l = (a.length - 1);l >= 0; l--)
		        	    {
		        	        Date_ar[len] = String.valueOf(a[l]);
		        	    len = len + 1;
		        	    }
		        		
		        		
		        		
		        
			
		
		th = (TabHost)findViewById(R.id.tabhost);
        th.setup();
        
              
        dates_dropdown = (Spinner)findViewById(R.id.dates);
        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, Date_ar);
		dates_dropdown.setAdapter(datesAdapter);
		dates_dropdown.setOnItemSelectedListener(this);
		
		   
        dates_dropdown_list = (Spinner)findViewById(R.id.dateslist);
        ArrayAdapter<String> datesAdapterlist = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, Date_ar);
		dates_dropdown_list.setAdapter(datesAdapterlist);
		dates_dropdown_list.setOnItemSelectedListener(this);
	
        
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();         
    	
    	mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    
    	
    	mMap.setMyLocationEnabled(true);	
    	mMap.getUiSettings().setMapToolbarEnabled(false);
    	mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
    	mMap.setOnMarkerClickListener(this);
       
    	Calendar cal = Calendar.getInstance(); 
  	 	Date_today = Integer.toString(cal.get(Calendar.DATE));

    	
    	PlotMap(Date_today);
        
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this); 

	    
        TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Map Plotting");
        th.addTab(specs);
        
        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("List");
        th.addTab(specs);
        
        db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
        // db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Address VARCHAR);");
        // db.execSQL("INSERT INTO tracks VALUES('arpit','123','456','time','address');");
        db.execSQL("CREATE TABLE IF NOT EXISTS log(Id VARCHAR,Username VARCHAR, Time VARCHAR,Date VARCHAR,Month VARCHAR,Year VARCHAR,Status VARCHAR);");
 		// db.execSQL("INSERT INTO log VALUES('123','"+UserName+"','12:12','19','6','2015','on');"); //no initial phone no and not registered   		


	
     // add an onclicklistener to see point on the map
    	listView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView parent, View view, int position, long id) {
    		 
    			final LatLng Loc1 = new LatLng(Float.parseFloat(Lat[position+leave]) ,Float.parseFloat(Lon[position]));
    			marker = mMap.addMarker(new MarkerOptions()
    			.position(Loc1)
    			.title("" + Time[position+leave])
    			.snippet("" + Address[position+leave])
    			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    			marker.showInfoWindow();
    			
    				th.setCurrentTab(0);
    			//Toast.makeText(getApplicationContext(), "Tab opened and the pos is "+position, Toast.LENGTH_SHORT).show();
    		}
    	});

}


      //on selecting from the dropdown
     public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
       
    	 // An item was selected. You can retrieve the selected item using
    	 Spinner spinner = (Spinner) parent;
         if(spinner.getId() == R.id.dates)
         {
        	 String date_s = (String) parent.getItemAtPosition(pos);
        	 //Toast.makeText(getApplicationContext(), "" + date_s, Toast.LENGTH_SHORT).show();
        	 PlotMap(date_s);
        	 dates_dropdown_list.setSelection(pos);
         }
         else if(spinner.getId() == R.id.dateslist)
         {
        	 String datelist_s = (String) parent.getItemAtPosition(pos);
        	 //Toast.makeText(getApplicationContext(), "" + datelist_s, Toast.LENGTH_SHORT).show();
        	 PlotMap(datelist_s);
             dates_dropdown.setSelection(pos);
         }
         
    	 
	
	
	 
     }

     public void onNothingSelected(AdapterView<?> parent) {
       // Another interface callback
     }
	
	
	
	
	
	void PlotMap(String Date_to){
		
		if(Lon.length==0){
			Toast.makeText(getApplicationContext(),"NO DATA FOUND TODAY", Toast.LENGTH_SHORT).show();
		}
		else{
			mMap.clear();
			ArrayList<String> list = new ArrayList<String>();
			int count = 0;
			LatLng LLold = null ;
			leave=0;
			int firstfound=0;
			
		for( int j = 0; j < Lon.length; j++ ){
			if(Date[j].equals(Date_to)){
			firstfound=1;
				final LatLng Loc1 = new LatLng(Float.parseFloat(Lat[j]) ,Float.parseFloat(Lon[j]));
			marker = mMap.addMarker(new MarkerOptions()
			.position(Loc1)
			.title("" + Time[j])
			.snippet("" + Address[j])
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			list.add(Id[j]);
			//marker.showInfoWindow();
			
			if(count > 0){
				Polyline line = mMap.addPolyline(new PolylineOptions()
				.add( LLold,Loc1 )
				.width(5)
				.color(Color.BLUE));
				
			}
			LLold = Loc1;
			count++;
			}
			else{
				if(firstfound==0){
				leave=leave+1;
				}
			}
			 MyCustomAdapter adapter = new MyCustomAdapter(list, this);
	         listView.setAdapter(adapter);  
		}
		
		
	/*	for(int k=1;k<L.length;k++){
			Polyline line = mMap.addPolyline(new PolylineOptions()
			.add( L[k-1],L[k] )
			.width(5)
			.color(Color.BLUE));
		*/
	
		
		}
		}
		
	
	
		
		
	


	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			
			 if( checknetforref() == 1 ){
					
			      
			         
			    new GetLogs(UserName).execute();
			 }else{
		    	  
		    	  Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
		      }
			
			
			return true;
		}
        if (id == R.id.action_userdetails) {
			
        	
			Intent i = new Intent(TabActivity.this, UserDetails.class);
			i.putExtra("UserName", UserName);
			startActivity(i);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	//check net connection
	
	 int checknetforref(){
		  
		  cd = new ConnectionDetector(getApplicationContext());
		  isInternetPresent = cd.isConnectingToInternet(); 
		 
		  if( isInternetPresent == false ){
			
				return 0;
		  }
		  else{
			  return 1;
			  
		  }
	 }


	@Override
	public boolean onMarkerClick(Marker myMarker) {
		// TODO Auto-generated method stub

		if (marker.equals(myMarker)) 
        {
			Toast.makeText(this,marker.getTitle(),Toast.LENGTH_LONG).show();
        }
		return false;
	}
	@Override
	public void onLocationChanged(Location location) {
		
		// TODO Auto-generated method stub
		 LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		 CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		 mMap.animateCamera(cameraUpdate);
		 locationManager.removeUpdates(this);
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	int getweekno(int Day,int Month,int Year) {
		 
		if((Year%4)==0){
		 
		 if(Month==1){
		return (Day/7)+1;
		}
		else if(Month==2){
		return ((31+Day)/7)+1;
		} 
		else if(Month==3){
		return ((60+Day)/7)+1;
		} 
		else if(Month==4){
		return ((91+Day)/7)+1;
		} 
		else if(Month==5){
		return ((121+Day)/7)+1;
		} 
		else if(Month==6){
		return ((152+Day)/7)+1;
		} 
		else if(Month==7){
		return ((182+Day)/7)+1;
		} 
		else if(Month==8){
		return ((213+Day)/7)+1;
		} 
		else if(Month==9){
		return ((244+Day)/7)+1;
		} 
		else if(Month==10){
		return ((274+Day)/7)+1;
		} 
		else if(Month==11){
		return ((305+Day)/7)+1;
		} 
		else if(Month==12){
		return ((335+Day)/7)+1;
		} 

		}
		else{

		  if(Month==1){
		return (Day/7)+1;
		}

		else if(Month==2){
		return ((31+Day)/7)+1;
		} 
		else if(Month==3){
		return ((59+Day)/7)+1;
		} 
		else if(Month==4){
		return ((90+Day)/7)+1;
		} 
		else if(Month==5){
		return ((120+Day)/7)+1;
		} 
		else if(Month==6){
		return ((151+Day)/7)+1;
		} 
		else if(Month==7){
		return ((181+Day)/7)+1;
		} 
		else if(Month==8){
		return ((212+Day)/7)+1;
		} 
		else if(Month==9){
		return ((243+Day)/7)+1;
		} 
		else if(Month==10){
		return ((273+Day)/7)+1;
		} 
		else if(Month==11){
		return ((304+Day)/7)+1;
		} 
		else if(Month==12){
		return ((334+Day)/7)+1;
		} 

		}
		return 0;
		}




	public class GetLogs extends AsyncTask <Void, Void, Void>{
	   	 
		InputStream isr = null;
	   	String result = "";	
	    String UserName;
	   	  
	   	String Date ;
	 	String Month;
	 	String Year;
	    String Week;	
	   	  
	     	ProgressDialog dialog = new ProgressDialog(TabActivity.this);

	   	    public GetLogs(String UserName){ 
	   	    	this.UserName=UserName;
	   	    }

	   	    @Override
	   	    protected void onPreExecute() {
	   	    	
	   	    	
        	Calendar cal = Calendar.getInstance(); 
      	 
     	 	 Date = Integer.toString(cal.get(Calendar.DATE));
     	    Month = Integer.toString( cal.get(Calendar.MONTH)+1);
     	 	Year = Integer.toString(cal.get(Calendar.YEAR));
	   	  //  Week = Integer.toString(cal.get(Calendar.WEEK_OF_YEAR));
     	 	Week=Integer.toString(getweekno(cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)));	
	   	      dialog.setMessage("Loading...");
	   		  dialog.setCanceledOnTouchOutside(false);
			  dialog.show();

	   	  }

	   	    @Override
	   	    protected Void doInBackground(Void... params) {
	   		   //-------------------
			
				    	
				try{
				    	 
				  HttpClient httpclient = new DefaultHttpClient();
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GETLOG.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("User",UserName));
				  nameValuePairs.add(new BasicNameValuePair("Year",Year));
				  nameValuePairs.add(new BasicNameValuePair("Month",Month));
				  nameValuePairs.add(new BasicNameValuePair("Day",Date));
				  nameValuePairs.add(new BasicNameValuePair("Week",Week));
				  
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
	       // Toast.makeText(getApplicationContext(), ""+UserName, Toast.LENGTH_SHORT).show();
	    //   Toast.makeText(getApplicationContext(), "Result"+result, Toast.LENGTH_LONG).show();
		
	 
	  	 char check_char = result.charAt(0);
		 int result_n = Character.getNumericValue(check_char);
	       if(result_n==0){
		 		  
		 		  Toast.makeText(getApplicationContext(), "Log data of this user is absent.", Toast.LENGTH_SHORT).show();
		 	  }
		 	  else{
		 		  Toast.makeText(getApplicationContext(), "User data is present", Toast.LENGTH_SHORT).show();

		 		  
	  
	      try {
	      
	    	  JSONArray jArray = new JSONArray(result);
	      	
	      	Timelog=new String[jArray.length( )];
	      	Datelog=new String[jArray.length( )];
	      	Monthlog=new String[jArray.length( )];
	      	Yearlog=new String[jArray.length( )];
	      	Statuslog=new String[jArray.length( )];
	      	
	    	 db.delete("log", null, null);
	    	   
	    	 db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
	         db.execSQL("CREATE TABLE IF NOT EXISTS log(Id VARCHAR,Username VARCHAR, Time VARCHAR,Date VARCHAR,Month VARCHAR,Year VARCHAR,Status VARCHAR);");
         // db.execSQL("CREATE TABLE IF NOT EXISTS tracks(Id VARCHAR,Username VARCHAR, Lon VARCHAR,Lat VARCHAR,Time VARCHAR,Address VARCHAR);");	      	
	    	 if( jArray.length( )==0){
	    		 
	    		 Toast.makeText(getApplicationContext(), "No data sent.", Toast.LENGTH_SHORT).show();
	    	 }
	    	 else{
	            for( int i = 0 ; i < jArray.length( ); i++ ){
	      	 
	      		JSONObject json = jArray.getJSONObject(i);
	     		db.execSQL("INSERT INTO log VALUES('"+i+"','"+UserName+"','"+json.getString("TIME")+"','"+json.getString("DATE")+"','"+json.getString("MONTH")+"','"+json.getString("YEAR")+"','"+json.getString("STATUS")+"');"); //no initial phone no and not registered   		

	      		 //db.execSQL("INSERT INTO  log('"+i+"','"+UserName+"','"+json.getString("TIME")+"','"+json.getString("DATE")+"','"+json.getString("MONTH")+"','"+json.getString("YEAR")+"','"+json.getString("STATUS")+"');"); //no initial phone no and not registered   		
	      	}	
	    	 }
	      	
	     
	      	
			  
	      	
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_SHORT).show();
			
		}
	      
	     
	      
	      Intent i = new Intent(TabActivity.this,Logs.class);
			i.putExtra("UserName", UserName);
			startActivity(i);      
	      
	      
		  }
}
  }
}





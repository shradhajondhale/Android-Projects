package com.maverick.trackeradmin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.maverick.trackeradmin.R;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MapPlot extends FragmentActivity implements OnMarkerClickListener, LocationListener {
	private GoogleMap mMap;         
	Marker marker;
    Polyline line;
    Context context;
    
    String[] Lon;
    String[] Lat;
    String[] Time;
    String[] Address;
    SQLiteDatabase db; 
    // Static LatLng
    LatLng startLatLng = new LatLng(30.707104, 76.690749);
    LatLng endLatLng = new LatLng(30.721419, 76.730017);
    String UserName;
    LocationManager locationManager;
    @Override     
	protected void onCreate(Bundle savedInstanceState) {         
	super.onCreate(savedInstanceState);         
	setContentView(R.layout.activity_map_plot);    
	
	UserName= getIntent().getStringExtra("UserName");
	
	db = openOrCreateDatabase("membertrack", Context.MODE_PRIVATE, null);
	
	
	
	int i=0;
	 Cursor c=db.rawQuery("SELECT * FROM tracks", null);
	 Lon=new String[c.getCount()];
	 Lat=new String[c.getCount()];
	 Time=new String[c.getCount()];
	 Address=new String[c.getCount()];
	 
	   if(c.getCount()==0)
		{
			Toast.makeText(getApplicationContext(), "The user is not using this app.", Toast.LENGTH_SHORT).show();
	//    	return;
		}
		StringBuffer buffer=new StringBuffer();
		while(c.moveToNext())
		{//Toast.makeText(getApplicationContext(), ""+c.getString(2), Toast.LENGTH_SHORT).show();
			 Lon[i]=c.getString(1);
			 Lat[i]=c.getString(2);
			 Time[i]=c.getString(3);
			 Address[i]=c.getString(4);
	 
	  i++;
		}
	
	
	//new Plots().execute();
	
	mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();         
	
	mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    
	
	mMap.setMyLocationEnabled(true);	
	mMap.getUiSettings().setMapToolbarEnabled(false);
	mMap.getUiSettings().setZoomGesturesEnabled(true);
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.getUiSettings().setTiltGesturesEnabled(true);
	mMap.setOnMarkerClickListener(this);
	
	final LatLng Loc = new LatLng(26.1760086 ,  91.77518280000004);
	//new Plots(UserName).execute();
	PlotMap();
	
	
	/*Marker TP = mMap.addMarker(new
	MarkerOptions().position(TutorialsPoint).title("TutorialsPoint"));
*/
	
	/*CameraPosition cameraPosition = new CameraPosition.Builder().target(
            new LatLng(17.385044, 78.486671)).zoom(12).build();
*/
 //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
 
	
	// mMap.getUiSettings().setMyLocationButtonEnabled(true);
	
/*
//latitude and longitude
double latitude = 17.385044;
double longitude = 78.486671;

//create marker
 marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");

//Changing marker icon
marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

//adding marker
mMap.addMarker(marker);

*/
/*
marker = mMap.addMarker(new MarkerOptions()
.position(Loc)
.title("KFC")
.snippet("Shop No. 3, Parnil Palace, 1st Floor, RG Baruah Road, Zoo Road, Tiniali, Guwahati, Assam 781024, India!")
.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	*/
	
/*
Polyline line = mMap.addPolyline(new PolylineOptions()
.add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
.width(5)
.color(Color.RED));
	
	*/
	
 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this); 


	}

	   

	
	
	
	@Override
	public boolean onMarkerClick(Marker myMarker) {
		// TODO Auto-generated method stub
		
		if (marker.equals(myMarker)) 
        {
			Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
           //Toast.makeText(getApplicationContext(), "clicked marker", Toast.LENGTH_SHORT).show();
			//handle click here
        }
		return false;
	}     

void PlotMap(){
	
	if(Lon.length==0){
		Toast.makeText(getApplicationContext(),"NO DATA FOUND", Toast.LENGTH_SHORT).show();
	}
	for(int j=0;j<Lon.length;j++){
		final LatLng Loc1 = new LatLng(Float.parseFloat(Lat[j]) ,Float.parseFloat(Lon[j]));

		marker = mMap.addMarker(new MarkerOptions()
		.position(Loc1)
		.title(""+Time[j])
		.snippet(""+Address[j])
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	
	}
}


	 public class Plots extends AsyncTask <Void, Void, Void>{
	   	  InputStream isr = null;
	   	  String result = "";	
	   	  String UserName;
	     	ProgressDialog dialog = new ProgressDialog(MapPlot.this);

	   	    public Plots(String UserName){ 
	   	    	this.UserName=UserName;
	   	    	
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
				  HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/GetLocationsOfUser.php"); //YOUR PHP SCRIPT ADDRESS
				  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				  nameValuePairs.add(new BasicNameValuePair("User",UserName));
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
	  
	       //  Toast.makeText(getApplicationContext(), "Result " + result, Toast.LENGTH_LONG).show();
	  
	  
	      try {
	      	JSONArray jArray = new JSONArray(result);

	      	Lon=new String[jArray.length( )];
	    	Lat=new String[jArray.length( )];
	    	//Time=new String[jArray.lent]
	    	
	    	
	    	
	      	for( int i = 0 ; i < jArray.length( ); i++ ){
	      	 
	      		JSONObject json = jArray.getJSONObject(i);
	      		
	      		
	      		
	      		
	      		final LatLng Loc1 = new LatLng(Float.parseFloat(json.getString("LAT")) ,Float.parseFloat( json.getString("LON")));
	      		//Log.d("ex123" , Loc + " ");
	      		
	      		marker = mMap.addMarker(new MarkerOptions()
	      		.position(Loc1)
	      		.title(""+json.getString("TIME"))
	      		.snippet(""+json.getString("ADDRESS"))
	      		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	      		
	      	}	
	      	
	      	
	      	
	  
			  
	      	
		}catch(Exception e){
			
			
		}
	      
	     
	      
		  }
   }


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		 LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		    mMap.animateCamera(cameraUpdate);
		    locationManager.removeUpdates(this);
		    
		    /*
		    CameraPosition cameraPosition = new CameraPosition.Builder()
		    .zoom(17)                   // Sets the zoom
		    .bearing(90)                // Sets the orientation of the camera to east
		    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
		    .build();                   // Creates a CameraPosition from the builder
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		*/
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
  
  
}


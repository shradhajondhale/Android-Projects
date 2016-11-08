package com.maverick.assampoll2016;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Html;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends ActionBarActivity implements OnItemSelectedListener {
	
	SQLiteDatabase db;
	
	int check_agree_tnc;
	
	CheckBox checkbox;
	EditText name, phone_number;
	Spinner location;
	TextView agree;
	String name_s, phone_number_s, location_s, gps_s, week_s, year_s;
	int done;
	Intent intent1;
	
	AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_up);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		 //open or create database
	      db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
	      
		done = 0;
		//color to action bar
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B863")));
		
		StrictMode.enableDefaults();
		
				
		//findviewbyid
				name = (EditText)findViewById(R.id.editText1);		
				phone_number = (EditText)findViewById(R.id.editText2);		
				location = (Spinner)findViewById(R.id.location);
				checkbox = (CheckBox)findViewById(R.id.check);
				agree = (TextView)findViewById(R.id.terms);
				agree.setText(Html.fromHtml("I agree to the <u>Terms and Conditions</u> of Assam Poll 2016."));
		

				//adapt districts
//String[] districtList = {"Baksa", "Barpeta", "Bongaigaon", "Cachar", "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Golaghat", "Goalpara", "Hailakandi", "Jorhat", "Kamrup", "Kamrup Metropolitan", "Karbi Anglong", "Karimganj", "Kokrajhar", "Lakhimpur", "Marigaon", "Nagaon", "Nalbari", "North Cachar Hills", "Sonitpur", "Sibsagar", "Tinsukhia", "Udalguri"};
//String[] districtList = {"Aftab , Karimaganj " ,  "Patharkandi , Karimaganj" , "Karimganj North , Karimaganj " , "Karimganj South , Karimaganj " , " Badarpur , Karimaganj " ,"Hailakandi , Hailakandi" , "Katlicherra , Hailakandi"," Algapur , Hailakandi " ,"Silchar , Cachar " ," Sonai , Cachar "," Dholai , Cachar "," Udharbond , Cachar "," Lakhipur , Cachar "," Barkhola , Cachar "," Katigora , Cachar "," Haflong , Dima Hasao "," Bokajan , Karbi Anglong "," Howraghat , Karbi Anglong "," Diphu , Karbi Anglong "," Baithalangso , Karbi Anglong "," Mankachar , Dhubri "," Salmara South , Dhubri "," Dhubri , Dhubri "," Gauripur , Dhubri "," Golakganj , Dhubri "," Bilasipara West , Dhubri "," Bilasipara East , Dhubri "," Gossaigaon , Kokrajhar "," Kokrajhar West , Kokrajhar "," Kokrajhar East , Kokrajhar "," Bongaigaon , Bongaigaon "," Bijni , Bongaigaon "," Abhayapuri North , Bongaigaon "," Abhayapuri South , Bongaigaon "," Dudhnai , Goalpara "," Goalpara East , Goalpara "," Goalpara West , Goalpara "," Jaleswar , Goalpara ", " Sorbhog , Barpeta "," Bhabanipur , Barpeta "," Patacharkuchi , Barpeta "," Barpeta , Barpeta "," Jania , Barpeta "," Baghbar , Barpeta "," Sarukhetri , Barpeta "," Chenga , Barpeta "," Boko , Kamrup "," Chaygaon , Kamrup "," Palasbari , Kamrup "," Jalukbari , Kamrup Metro "," Dispur , Kamrup Metro "," Gauhati East , Kamrup Metro "," Gauhati West , Kamrup Metro "," Hajo , Kamrup "," Kamalpur , Kamrup "," Rangiya , Kamrup "," Tamulpur , Baksa "," Nalbari , Nalbari "," Barkhetry , Nalbari "," Dharmapur , Nalbari "," Barama , Baksa "," Chapaguri , Baksa "," Panery , Udalguri "," Kalaigaon , Darrang "," Sipajhar , Darrang "," Mangaldoi , Darrang "," Dalgaon , Darrang "," Udalguri , Udalguri "," Majbat , Udalguri "," Dhekiajuli , Sonitpur "," Barchalla , Sonitpur "," Rangapara , Sonitpur "," Sootea , Sonitpur "," Biswanath , Sonitpur "," Behali , Sonitpur "," Gohpur , Sonitpur "," Jagiroad , Marigaon "," Marigaon , Marigaon "," Laharighat , Marigaon "," Raha , Nagaon "," Dhing , Nagaon ",				" Batadroba , Nagaon "," Rupohihat , Nagaon "," Nowgong , Nagaon "," Barhampur , Nagaon "," Samaguri , Nagaon "," Kaliabor , Nagaon "," Jamunamukh , Nagaon "," Hojai , Nagaon "," Lumding , Nagaon "," Bokakhat , Golaghat "," Sarupathar , Golaghat "," Golaghat , Golaghat "," Khumtai , Golaghat "," Dergaon , Jorhat "," Jorhat , Jorhat "," Majuli , Jorhat "," Titabar , Jorhat "," Mariani , Jorhat "," Teok , Jorhat "," Amguri , Sibsagar "," Nazira , Sibsagar "," Mahmara , Sibsagar "," Sonari , Sibsagar "," Thowra , Sibsagar "," Sibsagar , Sibsagar "," Bihpuria , Lakhimpur "," Naoboicha , Lakhimpur "," Lakhimpur , Lakhimpur "," Dhakuakhana , Lakhimpur "," Dhemaji , Dhemaji "," Jonai , Dhemaji "," Moran , Dibrugarh "," Dibrugarh , Dibrugarh "," Lahowal , Dibrugarh "," Duliajan , Dibrugarh "," Tingkhong , Dibrugarh "," Naharkatia , Dibrugarh "," Chabua , Dibrugarh "," Tinsukia , Tinsukia "," Digboi , Tinsukia "," Margherita , Tinsukia "," Doomdooma , Tinsukia "," Sadiya , Tinsukia" };
//String[] districtList = {"Aftab","Algapur", "Badarpur","Hailakandi"  , "Karimganj North", "Karimganj South" , "Katlicherra", "Patharkandi" ,"Silchar" ,"Sonai", "Dholai", "Udharbond", "Lakhipur", "Barkhola","Katigora","Haflong","Bokajan","Howraghat","Diphu","Baithalangso","Mankachar","Salmara South","Dhubri","Gauripur","Golakganj","Bilasipara West","Bilasipara East","Gossaigaon","Kokrajhar West","Kokrajhar East","Bongaigaon","Bijni","Abhayapuri North","Abhayapuri South","Dudhnai","Goalpara East","Goalpara West","Jaleswar", "Sorbhog","Bhabanipur","Patacharkuchi","Barpeta","Jania","Baghbar","Sarukhetri","Chenga","Boko","Chaygaon","Palasbari","Jalukbari","Dispur","Gauhati East","Gauhati West","Hajo","Kamalpur","Rangiya","Tamulpur","Nalbari","Barkhetry","Dharmapur","Barama","Chapaguri","Panery","Kalaigaon","Sipajhar","Mangaldoi","Dalgaon","Udalguri","Majbat","Dhekiajuli","Barchalla","Rangapara","Sootea","Biswanath","Behali","Gohpur","Jagiroad","Marigaon","Laharighat","Raha","Dhing","Batadroba","Rupohihat","Nowgong","Barhampur","Samaguri","Kaliabor","Jamunamukh","Hojai","Lumding","Bokakhat","Sarupathar","Golaghat","Khumtai","Dergaon","Jorhat","Majuli","Titabar","Mariani","Teok","Amguri","Nazira","Mahmara","Sonari","Thowra","Sibsagar","Bihpuria","Naoboicha","Lakhimpur","Dhakuakhana","Dhemaji","Jonai","Moran","Dibrugarh","Lahowal","Duliajan","Tingkhong","Naharkatia","Chabua","Tinsukia","Digboi","Margherita","Doomdooma","Sadiya" };

//String[] districtList =	{"Abhayapuri North","Abhayapuri South","Aftab","Algapur","Amguri","Badarpur","Baghbar","Baithalangso","Barama","Barchalla","Barhampur","Barkhetry","Barkhola","Barpeta","Batadroba","Behali","Bhabanipur","Bihpuria","Bijni","Bilasipara East","Bilasipara West","Biswanath","Bokajan","Bokakhat","Boko","Bongaigaon","Chabua","Chapaguri","Chaygaon","Chenga","Dalgaon","Dergaon","Dhakuakhana","Dharmapur","Dhekiajuli","Dhemaji","Dhing","Dholai","Dhubri","Dibrugarh","Digboi","Diphu","Dispur","Doomdooma","Dudhnai","Duliajan","Gauhati East","Gauhati West","Gauripur","Goalpara East","Goalpara West","Gohpur","Golaghat","Golakganj","Gossaigaon","Haflong","Hailakandi","Hajo","Hojai","Howraghat","Jagiroad","Jaleswar","Jalukbari","Jamunamukh","Jania","Jonai","Jorhat","Kalaigaon","Kaliabor","Kamalpur","Karimganj North","Karimganj South","Katigora","Katlicherra","Khumtai","Kokrajhar East","Kokrajhar West","Laharighat","Lahowal","Lakhimpur","Lakhipur","Lumding","Mahmara","Majbat","Majuli","Mangaldoi","Mankachar","Margherita","Mariani","Marigaon","Moran","Naharkatia","Nalbari","Naoboicha","Nazira","Nowgong","Palasbari","Panery","Patacharkuchi","Patharkandi","Raha","Rangapara","Rangiya","Rupohihat","Sadiya","Salmara South","Samaguri","Sarukhetri","Sarupathar","Sibsagar","Silchar","Sipajhar","Sonai","Sonari","Sootea","Sorbhog","Tamulpur","Teok","Thowra","Tingkhong","Tinsukia","Titabar","Udalguri","Udharbond"};	


String[] districtList =	{"Abhayapuri North","Abhayapuri South","Algapur","Amguri","Badarpur","Baghbar","Baithalangso","Barama","Barchalla","Barhampur","Barkhetry","Barkhola","Barpeta","Batadroba","Behali","Bhabanipur","Bihpuria","Bijni","Bilasipara East","Bilasipara West","Biswanath","Bokajan","Bokakhat","Boko","Bongaigaon","Chabua","Chapaguri","Chaygaon","Chenga","Dalgaon","Dergaon","Dhakuakhana","Dharmapur","Dhekiajuli","Dhemaji","Dhing","Dholai","Dhubri","Dibrugarh","Digboi","Diphu","Dispur","Doom Dooma","Dudhnai","Duliajan","Gauhati East","Gauhati West","Gauripur","Goalpara East","Goalpara West","Gohpur","Golaghat","Golakganj","Gossaigaon","Haflong","Hailakandi","Hajo","Hojai","Howraghat","Jagiroad","Jaleswar","Jalukbari","Jamunamukh","Jania","Jonai","Jorhat","Kalaigaon","Kaliabor","Kamalpur","Karimganj North","Karimganj South","Katigora","Katlichera","Khumtai","Kokrajhar East","Kokrajhar West","Laharighat","Lahowal","Lakhimpur","Lakhipur","Lumding","Mahmara","Majbat","Majuli","Mangaldoi","Mankachar","Margherita","Mariani","Marigaon","Moran","Naharkatia","Nalbari","Naoboicha","Nazira","Nowgong","Palasbari","Panery","Patachar Kuchi","Patharkandi","Raha","Rangapara","Rangiya","Ratabari","Rupohihat","Sadiya","Salmara South","Samaguri","Sarukhetri","Sarupathar","Sibsagar","Sidli","Silchar","Sipajhar","Sonai","Sonari","Sootea","Sorbhog","Tamulpur","Teok","Tezpur","Thowra","Tingkhong","Tinsukia","Titabar","Udalguri","Udharbond"};



				ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, districtList);
				//districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
				location.setAdapter(districtAdapter);
				location.setOnItemSelectedListener(this);
			
				gps_s = "None";
				
				
				
	}

		//on selecting a location from the dropdown
		 public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		       
			   location_s = (String) parent.getItemAtPosition(pos);
			//   Toast.makeText(getApplicationContext(), ""+location_s, Toast.LENGTH_SHORT).show();
			 
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		    
		    public void itemClicked(View v) {
		    	//code to check if this checkbox is checked!
		    	checkbox = (CheckBox)v;
		     		        
		        if ((checkbox).isChecked()) {
		        	
		           // Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_LONG).show();
		            check_agree_tnc = 1;
		            
		        }
		        else{
		        	
		        	//Toast.makeText(this, "Not Checked", Toast.LENGTH_LONG).show();
		           check_agree_tnc = 0;
		        }
		      }
		/*
	//function to get sign up details 
	public void insert( String name_s, String phone_number_s, String location_s ){
		
		//Toast.makeText(getApplicationContext(), "name " + name_s + "phone_number" + phone_number_s + "location" + location_s + "password" + password_s, Toast.LENGTH_SHORT).show();
		    	
		String result = "";
		    	 
		InputStream isr = null;
		    	
		try{
		    	 
		   HttpClient httpclient = new DefaultHttpClient();
		    	 
		   HttpPost httppost = new HttpPost("http://ambedkarstudentsassoc.netau.net/newvotingapp/login.php"); //YOUR PHP SCRIPT ADDRESS
		    	 
		   // HttpPost httppost = new HttpPost("http://172.23.193.32/elift-test/myfile.php"); //YOUR PHP SCRIPT ADDRESS
		    	 
		    	
		  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    	
		  nameValuePairs.add(new BasicNameValuePair("Name", name_s));
		  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s));
		  nameValuePairs.add(new BasicNameValuePair("Location", location_s));
		  nameValuePairs.add(new BasicNameValuePair("Vote","None"));
		  nameValuePairs.add(new BasicNameValuePair("Gps_track","NONE"));


		  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    	
		    	
		 ResponseHandler<String> responseHandler = new BasicResponseHandler();
		 String response = httpclient.execute(httppost, responseHandler);
		 char check_char = response.charAt(0);
		 done= Character.getNumericValue(check_char);
		  //HttpResponse response = httpclient.execute(httppost);
		    	 
		//  HttpEntity entity = response.getEntity();
		    	 
		 // isr = entity.getContent();
		   	
		  Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
		    	 
		}
		    	 
		catch(Exception e){
		    Toast.makeText(getApplicationContext(), "Not done", Toast.LENGTH_SHORT).show();
		}  	
	}
		   	 	
*/
	public void signup(View v){	 
	//	Toast.makeText(getApplicationContext(), ""+location_s, Toast.LENGTH_SHORT).show();
		
		//for getting location
	/*
		LocationManager mlocManager=null;  
        LocationListener mlocListener;  
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
        mlocListener = new MyLocationListener();  
       mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
 
       if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
           if(MyLocationListener.latitude>0)  
           {  
           	Toast.makeText(getApplicationContext(), "Latitude:- " + MyLocationListener.latitude + "Longitude:- " + MyLocationListener.longitude, Toast.LENGTH_SHORT).show();
              
                
            }  
            else  
            {  Toast.makeText(getApplicationContext(), "wait", Toast.LENGTH_SHORT).show(); 
             }  
         } else {  
       	  Toast.makeText(getApplicationContext(), "GPS is not turned on...", Toast.LENGTH_SHORT).show();   
         }  
       */
		//location ended
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet(); 
		         
		if(isInternetPresent==false){
			
			  LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("No Internet Connection!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();
						
		}
		else{
		
		
		  //get the details filled by user
	    	name_s = name.getText().toString();

	    	phone_number_s = phone_number.getText().toString();
	    	
    		Calendar c = Calendar.getInstance();
    		
    		int weekofyear = c.get(Calendar.WEEK_OF_YEAR);
    		int year = c.get(Calendar.YEAR);
    		week_s = Integer.toString(weekofyear);
    		year_s = Integer.toString(year);
    		
           // Toast.makeText(getApplicationContext(), "Name " + name_s + " PN " + phone_number_s, Toast.LENGTH_SHORT).show();

	    	if (name_s.matches("")) {
	    		  LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Please enter your name!");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();
					    	    return;
	    	}
	    	else if(phone_number_s.length() != 10){
	        	
	    		  LayoutInflater inflater = getLayoutInflater();

			        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

			        //Set text
			        TextView text = (TextView) layout.findViewById(R.id.tv);
			        text.setText("Please enter a 10 digit phone number!");

			        //Toast
			        Toast toast = new Toast(getApplicationContext());
			        toast.setGravity(Gravity.CENTER, 0, 0);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        toast.setView(layout);
			        toast.show();
					          
	        }
           else if(check_agree_tnc == 0){
	        	
        	   LayoutInflater inflater = getLayoutInflater();

		        View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_layout));

		        //Set text
		        TextView text = (TextView) layout.findViewById(R.id.tv);
		        text.setText("Agree Terms and Conditions!");

		        //Toast
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.CENTER, 0, 0);
		        toast.setDuration(Toast.LENGTH_SHORT);
		        toast.setView(layout);
		        toast.show();
				          
	        }
	    	else{
	    		
		    		
	    		new UpdateInfoAsyncTask(name_s,phone_number_s, location_s, week_s, year_s).execute();
	    		
		    	//call insert function to give details to online database
		    	/*insert(name_s, phone_number_s, location_s);
		    	if(done==1){
		    		Random random = new Random();
		    		int r = random.nextInt(9000)+1001;
		    		String otp_s = Integer.toString(r);

		    		//int r=1234;
		    		// sendSMS(phone_number_s, "Welcome to ASSAM-POLL-2016 . Your confirmation code is  "+ r);
		    		Toast.makeText(getApplicationContext(), "" + r, Toast.LENGTH_SHORT).show();
		    		
		    		// save number in database
		    		
		    		Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
		    		if(c.moveToFirst())
		    		{
		    			db.execSQL("UPDATE student SET phone='"+ phone_number.getText() +"' WHERE id='"+ 1 +"'");
		    			//Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
		    		}
		    		else
		    		{
		    			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
		    		}
		    		
		    		
		    
		    	
	        	 //calling intent ( activity1 )
	        	 Intent intent = new Intent(this, Verification.class);
	             
	        	 //Create the bundle
	             Bundle bun = new Bundle();

	             //Add your data to bundle
                 bun.putString(" ",otp_s);
	
	             
	             //Add the bundle to the intent
	             intent.putExtras(bun);
	             
	             startActivity(intent);
	             
		    	 finish();
		    	}
		    	else
		    	{
		    		Toast.makeText(getApplicationContext(), "Not registered try again",Toast.LENGTH_SHORT).show();
		    		
		    	}//--------------------------------
*/	    		
	    	}
		}
	
	}
		
	
	
	public void termsandcon(View v){
		
		
		 v.startAnimation(buttonClick);
		 //Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
		 AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
       builder.setMessage("\nPlease read these terms and conditions carefully before using Assam Poll 2016.\n\nThe Application is currently made available to you free of charge for non-commercial use. You will not, nor allow third parties on your behalf to (i) make and distribute copies of the Application (ii) attempt to copy, reproduce, alter, modify, reverse engineer, disassemble, decompile, transfer, exchange or translate the Application; or (iii) create derivative works of the Application of any kind whatsoever. Please acknowledge the Application is provided over the internet and mobile networks and so the quality and availability of the Application may be affected by factors outside our reasonable control.\n");
       builder.setTitle("Terms and Conditions");
       builder.setPositiveButton("OK",
  			
				new DialogInterface.OnClickListener() {
		
			public void onClick(DialogInterface dialog,int which) {
		
			
		   }
		});
		
		builder.show();
      
	}
	
	
	
	//sending otp thru sms
	private void sendSMS(String phoneNumber, String message)
    {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(phoneNumber, null, message, null, null);
    } 
	
	
	//asytask to update details of user
	public class UpdateInfoAsyncTask extends AsyncTask<Void, Void, Void>{
    	 
		  String name_s1;
    	  String phone_no_s1;
    	  String location_s1;
    	  String week_s1;
    	  String year_s1;
    	  
    	  char check_char;
    	  int done;
    	  
    	  Random random = new Random();
 		  int r = random.nextInt(9000)+1001;
  		  String otp_s = Integer.toString(r);
    	  
          ProgressDialog dialog = new ProgressDialog(SignUp.this);
    	   
    	  public UpdateInfoAsyncTask(String name_s1,String phone_no_s1,String location_s1, String week_s1, String year_s1){
    	      this.name_s1 = name_s1;
    	      this.phone_no_s1 = phone_no_s1;
    	      this.location_s1 = location_s1;
    	      this.week_s1 = week_s1;
    	      this.year_s1 = year_s1;
    	  }

    	  @Override
    	  protected void onPreExecute() {
    	   
    		  // TODO Auto-generated method stub
    		  
    		 dialog.setMessage("Please wait..."); 
    		 dialog.setCanceledOnTouchOutside(false);
    		 dialog.show();
    		  
           done = 0;
           
           
    	  }

     @Override
    	  protected Void doInBackground(Void... params) {
    	   
    		  
    		  //-------------------
    		//  String result = "";
		    	 
    		//  InputStream isr = null;
    			    	
    			try{
    			    	 
    			   HttpClient httpclient = new DefaultHttpClient();
    			    	 
    			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/AssamPoll/LOGIN.php"); //YOUR PHP SCRIPT ADDRESS
    			    	 
    			   // HttpPost httppost = new HttpPost("http://172.23.193.32/elift-test/myfile.php"); //YOUR PHP SCRIPT ADDRESS
    			    	 
    			    	
    			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
    			    	
    			  nameValuePairs.add(new BasicNameValuePair("Name", name_s));
    			  nameValuePairs.add(new BasicNameValuePair("PhoneNo", phone_number_s));
    			  nameValuePairs.add(new BasicNameValuePair("Location",location_s));
    			  nameValuePairs.add(new BasicNameValuePair("Vote","None"));
    			  nameValuePairs.add(new BasicNameValuePair("Gps_track","NONE"));
    			  nameValuePairs.add(new BasicNameValuePair("Year",year_s1));
    			  nameValuePairs.add(new BasicNameValuePair("Week",week_s1));
    			  nameValuePairs.add(new BasicNameValuePair("OTP",otp_s));

    			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			    	
    			    	
    			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
    			 String response = httpclient.execute(httppost, responseHandler);
    			 check_char = response.charAt(0);
    			 done = Character.getNumericValue(check_char);
    			  //HttpResponse response = httpclient.execute(httppost);
    			    	 
    			//  HttpEntity entity = response.getEntity();
    			    	 
    			 // isr = entity.getContent();
    			   	
    		 }catch(Exception e){
    			   
    			}  	
    		
    	    
    	       return null;
    	  }
    	  protected void onPostExecute(Void unused) {
    		 
    		  dialog.dismiss();
    		  
    		  if(done == 1){
		    		/*
		    		 Random random = new Random();
		    		 
		    		int r = random.nextInt(9000)+1001;
		    		String otp_s = Integer.toString(r);
                      */
		    		//int r=1234;
		    		// sendSMS(phone_number_s, "Welcome to ASSAM-POLL-2016 . Your confirmation code is  "+ r);
		    		//Toast.makeText(getApplicationContext(), "" + r, Toast.LENGTH_SHORT).show();
		    		
		    		// save number in database
		    		
		    		Cursor c = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
		    		if(c.moveToFirst())
		    		{
		    			db.execSQL("UPDATE student SET phone='"+ phone_number.getText() +"' WHERE id='"+ 1 +"'");
		    			//Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
		    		}
		    		else
		    		{
		    			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
		    		}
		    		
		    		
            
		    		
		    		// save constituency in database
		    		
		    		Cursor c1 = db.rawQuery("SELECT * FROM student WHERE id='"+1+"'", null);
		    		if(c1.moveToFirst())
		    		{
		    			db.execSQL("UPDATE student SET constituency='"+ location_s +"' WHERE id='"+ 1 +"'");
		    			//Toast.makeText(getApplicationContext(), "Data modified", Toast.LENGTH_SHORT).show();
		    		}
		    		else
		    		{
		    			//Toast.makeText(getApplicationContext(), "Data Not modified", Toast.LENGTH_SHORT).show();
		    		}
		    	//	Toast.makeText(getApplicationContext(), " My constituency is " + c.getString(3), Toast.LENGTH_SHORT).show();
	        	
		    		//calling intent ( activity1 )
	        	
		    		intent1 = new Intent(SignUp.this, Verification.class);
	             
	        	 
		    		//Create the bundle
	                Bundle bun = new Bundle();

	             
		    		//Add your data to bundle
             	bun.putString(" ",otp_s);
	
	             
	             //Add the bundle to the intent
	            intent1.putExtras(bun);
	             
	            startActivity(intent1);
	             
		   	    finish();
    		  
    		  
    		  
    		  
    	  }
    	  
    	}
	}
	
	


}

package com.maverick.trackeradmin;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddUser extends ActionBarActivity implements OnValueChangeListener {

	EditText username_e, contactno_e, password_e, conpassword_e, designation_e, empID_e, place_e;
    String username_s, contactno_s, password_s, conpassword_s, designation_s, empID_s, place_s, starttime_s, endtime_s;

    int starthourvalue, endhourvalue;
    
    TextView starttime, endtime;
    
    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
    
    

  //net connection
  Boolean isInternetPresent;
  ConnectionDetector cd;

    
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C0DE")));
		
		prgDialog = new ProgressDialog(this);
		
        // Set Cancelable as False
        prgDialog.setCancelable(false);
		
		username_e = (EditText)findViewById(R.id.editTextusername);
		contactno_e = (EditText)findViewById(R.id.editTextcontactno);
		password_e = (EditText)findViewById(R.id.editTextpassword);		
		conpassword_e = (EditText)findViewById(R.id.editTextconpassword);
		designation_e = (EditText)findViewById(R.id.editTextdesignation);		
		empID_e = (EditText)findViewById(R.id.editTextempid);
		place_e = (EditText)findViewById(R.id.editTextplace);	
		
		starttime = (TextView)findViewById(R.id.starttime);	
		endtime = (TextView)findViewById(R.id.endtime);	
	}

	
	public void ok(View v){
		
		 if( checknetforref() == 1 ){
				
		      
		//get the details filled by user
    	username_s = username_e.getText().toString();
    	
    	contactno_s = contactno_e.getText().toString();

    	password_s = password_e.getText().toString();
    	
    	conpassword_s = conpassword_e.getText().toString();
    	
    	designation_s = designation_e.getText().toString();
    	
    	empID_s = empID_e.getText().toString();
    	
    	place_s = place_e.getText().toString();
    	
    	starttime_s = "" + starthourvalue;
    	
    	endtime_s = "" + endhourvalue;
		
		if ( username_s.matches("") ) {
  		 
		    Toast.makeText(getApplicationContext(), "enter username", Toast.LENGTH_SHORT).show();
  	   }
		else if( contactno_s.matches("") ){
	      	
	        Toast.makeText(getApplicationContext(), "enter contact no", Toast.LENGTH_SHORT).show();
					          
	    }
  	   else if( password_s.matches("") ){
      	
        Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
				          
       }
       else if( conpassword_s.matches("") ){
      	

        Toast.makeText(getApplicationContext(), "confirm password", Toast.LENGTH_SHORT).show();
				          
      }
      else if(!( password_s.matches(conpassword_s)) ){
  	

       Toast.makeText(getApplicationContext(), "password and confirm password are difrnt", Toast.LENGTH_SHORT).show();
			          
      }
      else if( designation_s.matches("") ){
        	
          Toast.makeText(getApplicationContext(), "enter designation", Toast.LENGTH_SHORT).show();
  				          
      }
      else if( empID_s.matches("") ){
        	
          Toast.makeText(getApplicationContext(), "enter employer ID", Toast.LENGTH_SHORT).show();
  				          
      }
      else if( place_s.matches("") ){
        	
          Toast.makeText(getApplicationContext(), "enter place", Toast.LENGTH_SHORT).show();
  				          
         }
	  else{
		
		  
			//for uploading data
		
            new ADD(username_s, contactno_s, password_s, conpassword_s, designation_s, empID_s, place_s, starttime_s, endtime_s, fileName).execute();
  
		
		
	  }
		
		 }else{
			 
			 Toast.makeText(getApplicationContext(), "connect to internet", Toast.LENGTH_SHORT).show();
		 }
		
	}
	
	
	
	public class ADD extends AsyncTask<Void, Void, Void>{
   	 
		String username_s;
		String contactno_s;
		String password_s;
		String conpassword_s;
		String designation_s;
		String empID_s;
		String place_s;
		String starttime_s;
		String endtime_s;
		String imagename_s;
  	      
  	    char check_char;
  	    int done;
  	  
      ProgressDialog dialog = new ProgressDialog(AddUser.this);
  	   
  	  public ADD(String username_s, String contactno_s, String password_s, String conpassword_s, String designation_s, String empID_s, String place_s, String starttime_s, String endtime_s, String imagename_s ){
  	      
  		this.username_s = username_s;
		this.contactno_s = contactno_s;
	    this.password_s = password_s;
		this.conpassword_s = conpassword_s;
		this.designation_s = designation_s;
		this.empID_s = empID_s;
		this.place_s = place_s;
		this.starttime_s = starttime_s;
		this.endtime_s = endtime_s;
		this.imagename_s = imagename_s;
  		  
  		
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
  	   
  		    	
  			try{
  			    	 
  			   HttpClient httpclient = new DefaultHttpClient();
  			    	 
  			   HttpPost httppost = new HttpPost("http://www.spmaverick.com/TrackMe/ADDUSER.php"); //YOUR PHP SCRIPT ADDRESS
  						    	 
  			    	
  			  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
  			    	
  			  nameValuePairs.add(new BasicNameValuePair("UserName", username_s));
  			  nameValuePairs.add(new BasicNameValuePair("ContactNo", contactno_s));
 			  nameValuePairs.add(new BasicNameValuePair("Password", password_s));
 			  nameValuePairs.add(new BasicNameValuePair("Designation", designation_s));
 			  nameValuePairs.add(new BasicNameValuePair("EmpID", empID_s));
 			  nameValuePairs.add(new BasicNameValuePair("Place", place_s));
 			  nameValuePairs.add(new BasicNameValuePair("StartTime", starttime_s));
 			  nameValuePairs.add(new BasicNameValuePair("EndTime", endtime_s));
 			  nameValuePairs.add(new BasicNameValuePair("ImageName", imagename_s));
  			 
  			  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			    	
  			    	
  			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			 String response = httpclient.execute(httppost, responseHandler);
  			 check_char = response.charAt(0);
  			 done = Character.getNumericValue(check_char);
  			 
  			 //HttpResponse response = httpclient.execute(httppost);
  			    	 
  			 //HttpEntity entity = response.getEntity();
  			    	 
  			 //isr = entity.getContent();
  			   	
  		 }catch(Exception e){
  			   
  			}  	
  		
  	    
  	       return null;
  	  }
  	  protected void onPostExecute(Void unused) {
  		 
  		  dialog.dismiss();
  		  
  		  if(done == 1){
  			  
		    		Toast.makeText(getApplicationContext(), "Added successfully!",Toast.LENGTH_SHORT).show();
		    	
		    		
		    		//for uploading image
		  		  
					
		  		  // When Image is selected from Gallery
		          if (imgPath != null && !imgPath.isEmpty()) {
		              prgDialog.setMessage("Converting Image to Binary Data");
		              prgDialog.show();
		              // Convert image to String using Base64
		              encodeImagetoString();
		          // When Image is not selected from Gallery
		             // new ADD(username_s, contactno_s, password_s, conpassword_s, designation_s, empID_s, place_s, starttime_s, endtime_s, imagename_s).execute();
		          } else {
		              Toast.makeText(
		                      getApplicationContext(),
		                      "You must select image from gallery before you try to upload",
		                      Toast.LENGTH_LONG).show();
		          }
		  		
		  		
		  		
		    		
		    		//Intent intent1 = new Intent(AddUser.this, MainActivity.class);
	            			    	
	                //startActivity(intent1);
	             
		   	    //finish();
  		  
  		  
  	  }else{
  		
  		  Toast.makeText(getApplicationContext(), "Could not add. Try again",Toast.LENGTH_SHORT).show();
  		  
  		  
  	   }
  	  
  	  }
	}
	
	
	
	
	public void starttimepicker(View v){
		
		 final Dialog d = new Dialog(AddUser.this);
         d.setTitle("Start Time Picker");
         
         d.setContentView(R.layout.hourpickerdialog);
         Button b1 = (Button) d.findViewById(R.id.bset);
         Button b2 = (Button) d.findViewById(R.id.bcancel);
         final NumberPicker nps = (NumberPicker) d.findViewById(R.id.numberPicker1);
        
         nps.setMaxValue(23);
         nps.setMinValue(0);
         nps.setValue(10);
         nps.setWrapSelectorWheel(false);
         nps.setOnValueChangedListener(this);
         
         
         b1.setOnClickListener(new OnClickListener()
         {
         
        	 @Override
          public void onClick(View v) {
        	  
              starthourvalue = Integer.parseInt(String.valueOf(nps.getValue()));
              starttime.setText( "" + starthourvalue + " hrs" );

        	         	  
        	 // Toast.makeText(getApplicationContext(), "" + String.valueOf(np.getValue()), Toast.LENGTH_SHORT).show();
              d.dismiss();
           }    
          });
         b2.setOnClickListener(new OnClickListener()
         {
          @Override
          public void onClick(View v) {
              d.dismiss();
           }    
          });
       d.show();
		
	}
	
	

	public void endtimepicker(View v){
	
	final Dialog d = new Dialog(AddUser.this);
    d.setTitle("End Time Picker");
    d.setContentView(R.layout.hourpickerdialog);
    Button b1 = (Button) d.findViewById(R.id.bset);
    Button b2 = (Button) d.findViewById(R.id.bcancel);
    final NumberPicker npe = (NumberPicker) d.findViewById(R.id.numberPicker1);
    
    npe.setMaxValue(23);
    npe.setMinValue(0);
    npe.setValue(17);
    npe.setWrapSelectorWheel(false);
    npe.setOnValueChangedListener(this);
    
    b1.setOnClickListener(new OnClickListener()
    {
     @Override
     public void onClick(View v) {
    	 
    	    endhourvalue = Integer.parseInt(String.valueOf(npe.getValue()));

             endtime.setText( "" + endhourvalue + " hrs" );

    	 
   	   // Toast.makeText(getApplicationContext(), "" + String.valueOf(np.getValue()), Toast.LENGTH_SHORT).show();
         d.dismiss();
      }    
     });
    b2.setOnClickListener(new OnClickListener()
    {
     @Override
     public void onClick(View v) {
         d.dismiss();
      }    
     });
  d.show();
	
	}
	
	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	Intent pictureActionIntent;
	private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");


        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                Intent.ACTION_GET_CONTENT, null);
                        pictureActionIntent.setType("image/*");
                        pictureActionIntent.putExtra("return-data", true);
                        startActivityForResult(pictureActionIntent, 10
                        );
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        pictureActionIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(pictureActionIntent, 20);

                    }
                });
        myAlertDialog.show();
    }
	
	
	
	
	
	
	
	
	//here is image's code

    public void loadImagefromGallery(View view) {
      
    	 view.startAnimation(buttonClick);
    	startDialog();
    	/*
    	// Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    */
    }
 
    /*
    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
 
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
 
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.addphoto);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);
 
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
 
    }
     
    
    
    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {
 
            protected void onPreExecute() {
 
            };
 
            @Override
            protected String doInBackground(Void... params) {
            	BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
           
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream); 
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }
 
            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }
     
    public void triggerImageUpload() {
        makeHTTPCall();
    }
 
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking Php");        
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://www.spmaverick.com/TrackMe/upload.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                    }
 
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                            String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
	
	*/
    
    // When Image is selected from Gallery
  
    //Second Code
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 10) {

            try {
                // When an Image is picked
                if (requestCode == 10 && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imgView = (ImageView) findViewById(R.id.addphoto);
                    // Set the Image in ImageView
                    imgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                    // Get the Image's file name
                    String fileNameSegments[] = imgPath.split("/");
                    fileName = fileNameSegments[fileNameSegments.length - 1];
                    // Put file name in Async Http Post Param which will used in Php web app
                    params.put("filename", fileName);

                } else {
                    /*
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                            */
                }
            } catch (Exception e) {
                //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
            else if (requestCode == 20) {
            try {
               // Log.d("uri123 ", " " + resultCode + " " + RESULT_OK + " " + data);
                // When an Image is picked
                if (requestCode == 20 && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imgView = (ImageView) findViewById(R.id.addphoto);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    imgPath = getRealPathFromURI(tempUri);

                   // Log.d("uri123 ", " " + imgPath);

                    /*
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imgView = (ImageView) findViewById(R.id.btnImage);
                    imgView.setImageBitmap(photo);

                    Uri selectedImageUri = data.getData();
                    imgPath = getRealPathFromURI(AddMosque.this , selectedImageUri);
                    Log.d("uri123 ", selectedImageUri + " " + imgPath);
                    */

                    /*
                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added ASC");
                    if(cursor != null && cursor.moveToFirst())
                    {
                        do {
                            Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                            imgPath = uri.toString();
                            Log.d("imgpath12 " , " " + imgPath);
                        }while(cursor.moveToNext());
                        cursor.close();
                    }

                    */
                    // Set the Image in ImageView
                    imgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                    // Get the Image's file name
                    String fileNameSegments[] = imgPath.split("/");
                    fileName = fileNameSegments[fileNameSegments.length - 1];
                    // Put file name in Async Http Post Param which will used in Php web app
                    params.put("filename", fileName);

                } else {
                    /*
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                            */
                }
            } catch (Exception e) {
                //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }

    }
    
 // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {
 
            protected void onPreExecute() {
 
            };
 
            @Override
            protected String doInBackground(Void... params) {
            	BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
           
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream); 
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }
 
            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }
     
    public void triggerImageUpload() {
        makeHTTPCall();
    }
 
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking Php");        
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://www.spmaverick.com/TrackMe/upload.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                    }
 
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                            String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
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
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			  // When Image is selected from Gallery
	        if (imgPath != null && !imgPath.isEmpty()) {
	            prgDialog.setMessage("Converting Image to Binary Data");
	            prgDialog.show();
	            // Convert image to String using Base64
	            encodeImagetoString();
	        // When Image is not selected from Gallery
	        } else {
	            Toast.makeText(
	                    getApplicationContext(),
	                    "You must select image from gallery before you try to upload",
	                    Toast.LENGTH_LONG).show();
	        }
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
}

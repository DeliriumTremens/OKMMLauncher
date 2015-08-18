package com.okmm.alert.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.alert.R;
import com.okmm.alert.constant.Config;
import com.okmm.alert.service.Displayer;
import com.okmm.alert.service.KeepAlive;
import com.okmm.alert.service.Loader;
import com.okmm.alert.util.SettingsHelper;
import com.okmm.alert.util.Utilities;
import com.okmm.alert.util.ws.RestClient;
import com.okmm.alert.util.ws.RestResponseHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Registration {
	
  private EditText etName = null;
  private EditText etLastName = null;
  private EditText etAge = null;
  private EditText etStreet = null;
  private EditText etNeighborhood = null;
  private EditText etZipCode = null;
  private RadioGroup rdgGenre = null;
  private View registrationView = null;
  private Button bnAccept = null;
  private TextView tvErrorMessage = null;
  private ImageView ivError = null;
  private static Registration singleton = null;
  
  private static Context ctx = null;
  private static AlertDialog dialog = null;
  private TelephonyManager telManager = null;
  
  private Registration(){
	bind();
	createDialog();
  }
  
  public static Registration getInstance(Context ctx){
	if(singleton == null){
	  Registration.ctx = ctx;
	  singleton = new Registration();
	} 
	return singleton;
  }
  
  
  
  private View.OnClickListener onClickListener =  new View.OnClickListener(){            
    @Override
    public void onClick(View v){
      int errMessageId = 0;
      if((errMessageId = validate(registrationView))== 0){
    	tvErrorMessage.setVisibility(View.INVISIBLE);
    	ivError.setVisibility(View.INVISIBLE);
        callRegistrationService();
      } else {
    	  tvErrorMessage.setText(errMessageId);
    	  tvErrorMessage.setVisibility(View.VISIBLE);
    	  ivError.setVisibility(View.VISIBLE);
      }
    }
  };
  
  public void show(){
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	dialog.show();
  }
  
  public boolean isShowing(){
	return dialog.isShowing();
  }
  
  private void bind(){
	registrationView = LayoutInflater.from(ctx).inflate(R.layout.registration, null);
	telManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
	etName = (EditText)registrationView.findViewById(R.id.etName);
	etLastName = (EditText)registrationView.findViewById(R.id.etLastName);
	etAge = (EditText)registrationView.findViewById(R.id.etAge);
	etStreet = (EditText)registrationView.findViewById(R.id.etStreet);
	etNeighborhood = (EditText)registrationView.findViewById(R.id.etNeighborhood);
	etZipCode = (EditText)registrationView.findViewById(R.id.etZipCode);
	rdgGenre = (RadioGroup)registrationView.findViewById(R.id.rdgGenre);  
	bnAccept = (Button)registrationView.findViewById(R.id.bnAccept);
	tvErrorMessage = (TextView) registrationView.findViewById(R.id.tvErrorMessage);
	ivError = (ImageView)registrationView.findViewById(R.id.ivError);
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                  .setCancelable(false);
    dialog = alertBuilder.setView(registrationView).create(); 
    bnAccept.setOnClickListener(onClickListener);
  }
  
  private void callRegistrationService(){
	RequestParams params = new RequestParams();
	final String userToken = Utilities.getUserToken();
	//TODO
	final String simNumber =  telManager.getSimSerialNumber();
	final String imeiNumber =  telManager.getDeviceId();
	//final String simNumber =  new Date().getTime()  + "";
	//final String imeiNumber =  new Date().getTime() + "";
	Log.i(Config.LOG_TAG, "simNumber => " + simNumber);
	Log.i(Config.LOG_TAG, "imeiNumber => " + imeiNumber);
	params.put("nombre", etName.getText().toString());
    params.put("apellidos", etLastName.getText().toString());
    params.put("edad", etAge.getText().toString());        		   
    params.put("genero", rdgGenre.getCheckedRadioButtonId() == R.id.rbMale ? 1 : 2);
    params.put("calle", etStreet.getText().toString());
    params.put("colonia", etNeighborhood.getText().toString());
    params.put("cp", etZipCode.getText().toString());
    params.put("no_sim",  simNumber);
    params.put("imei", imeiNumber);
    params.put("userToken", userToken);
    RestClient.post("registro", params, new RestResponseHandler(ctx, false) {
  	  @Override
  	  public void onSuccess(JSONObject response) throws JSONException {
  		String code = response.getString("errorcode");
  		if(code.equals(Config.WS_STATUS_OK)){
  		  //TODO
  	  	  SettingsHelper.setUserId(ctx, response.getInt("id_user"));
  	  	  //SettingsHelper.setUserId(ctx, 1);
  	  	  SettingsHelper.setImeiNumber(ctx, imeiNumber);
  	  	  SettingsHelper.setSimNumber(ctx, simNumber);
  	  	  SettingsHelper.setUserToken(ctx, userToken);
  	  	  new Loader().start(ctx);
  	  	  new Displayer().start(ctx);
  	  	  new KeepAlive().start(ctx);
  	  	  dialog.dismiss();
  		} else {
  			tvErrorMessage.setText(getErrorMessage(code));
      	    tvErrorMessage.setVisibility(View.VISIBLE);
      	    ivError.setVisibility(View.VISIBLE);
  		}
  		
  	  }    
  	});
  }
	
  private static int validate(View registrationView){
	int errMessageId = 0;
	EditText etName = (EditText)registrationView.findViewById(R.id.etName);
	EditText etLastName = (EditText)registrationView.findViewById(R.id.etLastName);
	EditText etAge = (EditText)registrationView.findViewById(R.id.etAge);
	EditText etStreet = (EditText)registrationView.findViewById(R.id.etStreet);
	EditText etNeighborhood = (EditText)registrationView.findViewById(R.id.etNeighborhood);
	EditText etZipCode = (EditText)registrationView.findViewById(R.id.etZipCode);
	RadioGroup rdgGenre = (RadioGroup)registrationView.findViewById(R.id.rdgGenre);
	if(etName.getText().toString().isEmpty()){
	  errMessageId = R.string.errNameRequired;
	} else if (etLastName.getText().toString().isEmpty()){
	  errMessageId = R.string.errLastNameRequired;
	} else if (etAge.getText().toString().isEmpty()){
	  errMessageId = R.string.errAgeRequired;
	} else if (rdgGenre.getCheckedRadioButtonId() == -1){
	  errMessageId = R.string.errGenreRequired;
	} else if (etStreet.getText().toString().isEmpty()){
	  errMessageId = R.string.errStreetRequired;
	} else if (etNeighborhood.getText().toString().isEmpty()){
	  errMessageId = R.string.errNeighborhoodRequired;
	} else if (etZipCode.getText().toString().isEmpty()){
	  errMessageId = R.string.errZipCodeRequired;
	} else if (etZipCode.getText().toString().length() != 5){
	   errMessageId = R.string.errZipCodeWrong;
	}
	return errMessageId;
  }
  
  private String getErrorMessage(String errorCode){
	String message = "unknown";
	if(errorCode.equals("1")){
		message = ctx.getResources().getString(R.string.errDataIncomplete);
	} else if(errorCode.equals("2")){
		message = ctx.getResources().getString(R.string.errRejected);
	} else if(errorCode.equals("3")){
		message = ctx.getResources().getString(R.string.errDuplicate);
	}
	return message;
  }

}

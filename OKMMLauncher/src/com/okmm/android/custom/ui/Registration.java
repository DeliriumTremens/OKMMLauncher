package com.okmm.android.custom.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.loopj.android.http.RequestParams;
import com.okmm.android.R;
import com.okmm.android.custom.util.ToastBuilder;
import com.okmm.android.custom.ws.RestClient;
import com.okmm.android.custom.ws.RestResponseHandler;

public class Registration {
	
  private static String TAG = "Registration";
	
  private EditText etName = null;
  private EditText etLastName = null;
  private EditText etAge = null;
  private EditText etStreet = null;
  private EditText etNeighborhood = null;
  private EditText etZipCode = null;
  private RadioGroup rdgGenre = null;
  private View registrationView = null;
  
  private Context ctx = null;
  private AlertDialog dialog = null;
  private TelephonyManager telManager = null;
  
  private View.OnClickListener onClickListener =  new View.OnClickListener(){            
    @Override
    public void onClick(View v){
      int errMessageId = 0;
      if((errMessageId = validate(registrationView))== 0){
        callRegistrationService();
      } else {
        ToastBuilder.show(errMessageId, ctx);
      }
    }
  };
  
  public Registration(Context ctx){
	this.ctx = ctx;
	init();
  }
  
  public void show(){
	dialog.show();
	dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(onClickListener);
  }
  
  private void init(){
	bind();
	createDialog();
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
  }
  
  private void createDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
                  .setTitle(ctx.getResources().getString(R.string.registration))
                  .setCancelable(false)
                  .setPositiveButton(ctx.getString(android.R.string.ok), null);
    dialog = alertBuilder.setView(registrationView).create(); 
  }
  
  private void callRegistrationService(){
	RequestParams params = new RequestParams();
	params.put("nombre", etName.getText().toString());
    params.put("apellidos", etLastName.getText().toString());
    params.put("edad", etAge.getText().toString());        		   
    params.put("genero", rdgGenre.getCheckedRadioButtonId() == R.id.rbMale ? 1 : 2);
    params.put("calle", etStreet.getText().toString());
    params.put("colonia", etNeighborhood.getText().toString());
    params.put("cp", etZipCode.getText().toString());
    params.put("no_sim ",  telManager.getSimSerialNumber());
    params.put("imei ", telManager.getDeviceId());
    ToastBuilder.show(params.toString(), ctx);
    RestClient.post("registro", params, new RestResponseHandler(ctx) {
  	  @Override
  	  public void onSuccess(JSONObject response) throws JSONException {
  		  dialog.dismiss(); 
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
	} else if (etStreet.getText().toString().isEmpty()){
	  errMessageId = R.string.errStreetRequired;
	} else if (etNeighborhood.getText().toString().isEmpty()){
	  errMessageId = R.string.errNeighborhoodRequired;
	} else if (etZipCode.getText().toString().isEmpty()){
	  errMessageId = R.string.errZipCodeRequired;
	} else if (etAge.getText().toString().isEmpty()){
	  errMessageId = R.string.errAgeRequired;
	} else if (rdgGenre.getCheckedRadioButtonId() == -1){
	  errMessageId = R.string.errGenreRequired;
	}
	return errMessageId;
  }

}

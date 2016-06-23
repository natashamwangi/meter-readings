package com.nancy.loginregister.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nancy.loginregister.R;
import com.nancy.loginregister.database.SQLiteHandler1;
import com.nancy.loginregister.models.SessionManager;
import com.nancy.loginregister.models.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class MakeReading extends AppCompatActivity implements View.OnClickListener {



    private static final String TAG = MakeReading.class.getSimpleName();

    private ImageView imageView;

    private Button btnSelectPic;
    private Button buttonUpload;
    private EditText editTextAccNo;
    private EditText editTextMeterNo;
    private EditText editTextReading;
    private EditText etxtUpload;
    private ProgressDialog pDialog;
    private SQLiteHandler1 db;
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;
    private TextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reading);

        btnSelectPic = (Button) findViewById(R.id.button_selectpic);
        buttonUpload = (Button) findViewById(R.id.uploadButton);

        editTextAccNo = (EditText) findViewById(R.id.editText_accno);
        editTextReading = (EditText) findViewById(R.id.editText_reading);
        editTextMeterNo = (EditText) findViewById(R.id.editText_meterno);
        messageText  = (TextView)findViewById(R.id.messageText);
        etxtUpload = (EditText)findViewById(R.id.etxtUpload);

        imageView  = (ImageView) findViewById(R.id.imageView_pic);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // SQLite database handler
        db = new SQLiteHandler1(getApplicationContext());

        btnSelectPic.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();


    }

        public void onClick(View v) {


            switch (v.getId()){
                case R.id.button_selectpic:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, AppConfig.REQCODE);
                    break;
                case R.id.uploadButton:
                    Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    dialog.show();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    try {
                        jsonObject.put(AppConfig.imageName, etxtUpload.getText().toString().trim());
                        Log.e("Image name", etxtUpload.getText().toString().trim());
                        jsonObject.put(AppConfig.image, encodedImage);
                    } catch (JSONException e) {
                        Log.e("JSONObject Here", e.toString());
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.urlUpload, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.e("Message from server", jsonObject.toString());
                                    dialog.dismiss();
                                    messageText.setText("Image Uploaded Successfully");
                                    Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("Message from server", volleyError.toString());
                            dialog.dismiss();
                        }
                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    Volley.newRequestQueue(this).add(jsonObjectRequest);
                    break;

            }





    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQCODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }




}


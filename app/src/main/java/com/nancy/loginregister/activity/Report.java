package com.nancy.loginregister.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nancy.loginregister.R;

import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity implements View.OnClickListener {
    private TextView VMessage;
    private Button buttonSubmit;
    private Spinner accno;

    private String message;
    private String acc;


    public static final String REPORT_URL = "http://192.168.0.20/php/report.php";
    public static final String KEY_MESSAGE="message";
    public static final String KEY_ACCNO="account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        VMessage=(TextView) findViewById(R.id.sv_message);
        buttonSubmit=(Button) findViewById(R.id.buttonSubmit);
        accno=(Spinner) findViewById(R.id.spinner1);

    }
    @Override
    public void onClick(View v) {
        userReport();
    }

    private void userReport() {
        VMessage.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = VMessage.getScrollX();
               // Log.d("Response Error",+ scrollX);
            }
        });
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        accno.setAdapter(adapter);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){

                            Toast.makeText(Report.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Report.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_MESSAGE,message);
                map.put(KEY_ACCNO, acc);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

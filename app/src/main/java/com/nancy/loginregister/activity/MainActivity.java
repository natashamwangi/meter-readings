package com.nancy.loginregister.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nancy.loginregister.R;
import com.nancy.loginregister.database.SQLiteHandler;
import com.nancy.loginregister.models.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private Button txtMakeReadings;
    private Button txtBalanceInquiry;
    private Button txtReportComplaints;
    private Button txtEditReadings;
    private Button txtComplaints;
    private TextView tvLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtMakeReadings = (Button) findViewById(R.id.make_readings);
        txtBalanceInquiry = (Button) findViewById(R.id.balance_inquiry);
        txtReportComplaints = (Button) findViewById(R.id.report_complaint);
        txtEditReadings = (Button) findViewById(R.id.edit_readings);
        txtComplaints = (Button) findViewById(R.id.complaints);
        tvLogout = (TextView) findViewById(R.id.tvLogout);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");

        // Displaying the user details on the screen
        txtName.setText(name);

        // Logout button click event
        tvLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Complaints button click event
        txtComplaints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Complaints.class);
                startActivity(i);
                finish();
            }
        });

        // EditReadings button click event
        txtEditReadings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditReadings.class);
                startActivity(i);
                finish();
            }
        });

        // ReportComplaints button click event
        txtReportComplaints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Report.class);
                startActivity(i);
                finish();
            }
        });

        // BalanceEnquiry button click event
        txtBalanceInquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BalanceInquiry.class);
                startActivity(i);
                finish();
            }
        });

        // MakeReadings button click event
        txtMakeReadings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MakeReading.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }


}

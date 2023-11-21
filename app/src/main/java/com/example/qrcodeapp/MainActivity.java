package com.example.qrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Initialise UI elements
    private Button buttonScan;
    private EditText etName, etAddress;

    //Initialise qr code scanner object
    private IntentIntegrator qrScan = new IntentIntegrator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link UI elements
        buttonScan = findViewById(R.id.buttonScan);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);

    }

    //Scan function
    public void doScan(View view) {
        qrScan.initiateScan();
    }

    //Get the scan results
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            //If QR scan returns a null entry
            if(result.getContents() == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show();
            } else {
                //If QR scan contains data
                try {
                    //Convert the data to JSON
                    JSONObject obj = new JSONObject(result.getContents());
                    //Populate textviews with data
                    etName.setText(obj.getString("title"));
                    etAddress.setText(obj.getString("website"));
                    etAddress.setMovementMethod(LinkMovementMethod.getInstance());
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Encoded format doesn't match
                    //So display data available in a Toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
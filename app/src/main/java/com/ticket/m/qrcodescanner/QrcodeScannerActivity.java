package com.ticket.m.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

public class QrcodeScannerActivity extends AppCompatActivity {
private Button scan;
private TextView name,address;
private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        scan=findViewById(R.id.scan);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        qrScan=new IntentIntegrator(this);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null)
        {
            //if the result is empty
            if(result.getContents()==null)
            {
                Toast.makeText(this,"Result not found",Toast.LENGTH_LONG).show();
            }//if result contains data
            else
            {
                try
                {
                    JSONObject obj=new JSONObject(result.getContents());
                    name.setText(obj.getString("name"));
                    address.setText(obj.getString("address"));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    //if control comes here that means there is some content which is not supported
                    //by our application in this situation we will print what ever it contains
                    Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                }
            }

        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}

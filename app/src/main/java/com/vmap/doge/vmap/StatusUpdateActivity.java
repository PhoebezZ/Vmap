package com.vmap.doge.vmap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//CMPS121 project, UCSC vending machine map (Vmap)
//Authors:
//Jiaming Zhao, 1416678, jzhao26@ucsc.edu
//Li Jiang, 1411197, ljiang2@ucsc.edu
//Yilin Xu, 1441665, yxu48@ucsc.edu

public class StatusUpdateActivity extends AppCompatActivity {
    String vending_name;
    String status;//status, 0 = no info, 1 = working, 2 = not working, 3 = no stock
    Button updateBut;
    //radio button
    RadioButton workBut;
    RadioButton notWorkBut;
    RadioButton noStockBut;
    RadioGroup StatusGroup;
    FirebaseDatabase db;
    // database;

    //machine status class for uploading file
    public class machineStat{
        public String mName;
        public String mStat;
        public String mDate;

        public machineStat() {
            // Default constructor required for calls to DataSnapshot.getValue()
        }

        public machineStat(String mName, String mStat, String mDate) {
            this.mName = mName;
            this.mStat = mStat;
            this.mDate = mDate;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_update);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#56768f"));

        StatusGroup = (RadioGroup) findViewById(R.id.StatusGroup);
        workBut = (RadioButton) findViewById(R.id.workBut);
        notWorkBut = (RadioButton) findViewById(R.id.notWorkBut);
        noStockBut = (RadioButton) findViewById(R.id.noStockBut);
        updateBut = (Button) findViewById(R.id.updateBut);
        status = "0";
        //init database ref
        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = db.getReference();

        //get machine name
        Bundle tmp = getIntent().getExtras();
        if(tmp != null){
            vending_name = tmp.getString("VendingName");
        }else{vending_name = "error";}

        //get and set machine status
        StatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                //RadioButton checked =(RadioButton)findViewById(checkedId);
                switch (checkedId){//check which button is checked, 1 = working, 2 = not working, 3 = no stock
                    case (R.id.workBut):
                        status = "1";
                        break;
                    case R.id.notWorkBut :
                        status ="2";
                        break;
                    case R.id.noStockBut :
                        status = "3";
                        break;
                }
            }
        });

        updateBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//Button listener
                //crate machine status object, write to db

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = df.format(c.getTime());
                // add last update date to status
                status = status + "," + date;

                machineStat m = new machineStat(vending_name,status, date);
                dbRef.child("machines").child(vending_name).setValue(status);
                Toast.makeText(getApplicationContext(),"Update Successful!", Toast.LENGTH_LONG).show();
            }
        });




    }
}

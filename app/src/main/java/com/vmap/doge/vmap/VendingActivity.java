package com.vmap.doge.vmap;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//CMPS121 project, UCSC vending machine map (Vmap)
//Authors:
//Jiaming Zhao, 1416678, jzhao26@ucsc.edu
//Li Jiang, 1411197, ljiang2@ucsc.edu
//Yilin Xu, 1441665, yxu48@ucsc.edu

public class VendingActivity extends AppCompatActivity {

    //initialize necessary variables
    TextView vending_text;
    TextView stock_class;
    TextView LastUpdate;
    TextView machine_status;
    TextView machine_address;
    TextView pay_method;
    ImageView vending_image;
    Button status_update;
    Button navigate_button;
    String vending_name;
    FirebaseDatabase db;
    private String statData;
    private boolean loadCmp;
    private String navi_string;

    // database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#56768f"));

        //init database ref
        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = db.getReference();


        vending_text = (TextView)findViewById(R.id.Vending_Name_ID);
        stock_class = (TextView)findViewById(R.id.Stock_Class_ID);
        machine_status = (TextView)findViewById(R.id.Machine_Status_ID);
        machine_address = (TextView)findViewById(R.id.Location_ID);
        pay_method = (TextView)findViewById(R.id.Payment_Method_ID);
        vending_image = (ImageView)findViewById(R.id.vending_image_ID);
        status_update = (Button)findViewById(R.id.Status_update_ID);
        LastUpdate = (TextView) findViewById(R.id.LastUpdate);
        navigate_button = (Button)findViewById(R.id.Navigation_button_ID);
        loadCmp = false;

        Bundle tmp = getIntent().getExtras();
        //pass vending machine name
        //from the previous activity
        if(tmp != null){
            vending_name = tmp.getString("VendingName");
            vending_text.setText(vending_name);//set vending machine page name
            //getStatus(dbRef);

            //read info from db for 1 time
            dbRef.child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(VendingActivity.this,"RETRIVING DATA",Toast.LENGTH_SHORT).show();
                    String status = "no info";
                    String date = "no info";
                    //if have info on db
                    if (dataSnapshot.hasChild(vending_name)){
                        statData = dataSnapshot.child(vending_name).getValue().toString();
                        switch(statData.substring(0,1)){
                            case("0"):break;
                            case("1"):
                                status = "working";
                                date = statData.substring(2);
                                break;
                            case("2"):
                                status = "not working";
                                date = statData.substring(2);
                                break;
                            case ("3"):
                                status = "out of stock";
                                date = statData.substring(2);
                                break;
                        }

                    }

                    setInfo(status, date);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }



        status_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//Button listener to update page
                Intent tmpintent = new Intent(VendingActivity.this, StatusUpdateActivity.class);
                tmpintent.putExtra("VendingName",vending_name);
                startActivity(tmpintent);
                }
        });

        navigate_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//Button listener to navigate function
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(navi_string));
                startActivity(intent);
            }
        });

    }

    private void setInfo(String status, String date){
        //set imageview , navigation and information according to vending_name
        if(vending_name.equals("Opers") ){
            navi_string = "http://maps.google.com/maps?daddr=36.99463085,-122.0544858";
            machine_address.setText("On the road next to the tennis court");
            stock_class.append("\nsoda\ngatorade/water");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.vending_image1));
        }else if(vending_name.equals("S&E Library Bottom Floor")){
            navi_string = "http://maps.google.com/maps?daddr=36.99904607927167,-122.0607041940093";
            machine_address.setText("Bottom floor by the maps");
            stock_class.append("\nsnack");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.selibrary));
        }else if(vending_name.equals("Social Science 1")){
            navi_string = "http://maps.google.com/maps?daddr=37.00295247,-122.05825199";
            machine_address.setText("In front of the back door of SS 1");
            stock_class.append("\nsoda");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.img_socialscience1));
        }else if(vending_name.equals("Social Science 2")){
            navi_string = "http://maps.google.com/maps?daddr=37.00151003,-122.05873196";
            machine_address.setText("Across from Media Services \non the ground floor of SS2");
            stock_class.append("\nsnack");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.img_socialscience2));
        }else if(vending_name.equals("Stevenson Recreation")){
            navi_string = "http://maps.google.com/maps?daddr=36.99713734,-122.05238711";
            machine_address.setText("On the second floor\n(the entry floor in house 5)");
            stock_class.append("\nsnack\nsoda and water");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.img_stevensonrecreation));
        }else if(vending_name.equals("Cowell Turner Hall Laundry")){
            navi_string = "http://maps.google.com/maps?daddr=36.996665,-122.053914";
            machine_address.setText("Inside laundry room of \nCowell Turner dorm");
            stock_class.append("\nsoda");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.img_cowelllaundry));
        }else if(vending_name.equals("Engineering 1 first floor")){
            navi_string = "http://maps.google.com/maps?daddr=37.00056510504045,-122.06302866339685";
            machine_address.setText("In the main hallway of the \nfirst floor of J. Baskin Engineering");
            stock_class.append("\nsoda and water\nsnack");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.img_jbaksin1));
        }else if(vending_name.equals("Mchenry Library Media Center")){
            navi_string = "http://maps.google.com/maps?daddr=36.99569219,-122.05933617";
            machine_address.setText("In the main foyer");
            stock_class.append("\nsnack\nsoda");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.mchenry));
        }else if(vending_name.equals("Natural Science 2 2nd Floor")){
            navi_string = "http://maps.google.com/maps?daddr=36.99869262630071,-122.06087216734886";
            machine_address.setText("At the far western end of Natural \nSciences 2, on the second floor");
            stock_class.append("\nsnack\nsoda");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.naturalsci));
        }else if(vending_name.equals("Thimann Lab 1st Floor")){
            navi_string = "http://maps.google.com/maps?daddr=36.99825552,-122.06169363";
            machine_address.setText("In the eastern stairwell of the Thimann \nLaboratories, on the first floor");
            stock_class.append("\nsnack\nsoda");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.thimann));
        }else if(vending_name.equals("Office of registrar")){
            navi_string = "http://maps.google.com/maps?daddr=36.996136,-122.057143";
            machine_address.setText("Walk into the front door and \ntake a right turn");
            stock_class.append("\nsnack\ndrinks");
            machine_status.setText("Machine Status:" + "\n" + status);
            LastUpdate.setText("Last Update:" + "\n" + date);
            pay_method.append("\ncash, card");
            vending_image.setImageDrawable(ContextCompat.getDrawable(
                    VendingActivity.this,R.mipmap.hahn));
        }

    }

    //database helper function
    private void getStatus(DatabaseReference dbRef) {
        dbRef.child("machines").child(vending_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(VendingActivity.this,"RETRIVING DATA",Toast.LENGTH_SHORT).show();
                statData = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}

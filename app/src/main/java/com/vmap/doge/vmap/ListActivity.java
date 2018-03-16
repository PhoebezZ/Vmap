package com.vmap.doge.vmap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

//CMPS121 project, UCSC vending machine map (Vmap)
//Authors:
//Jiaming Zhao, 1416678, jzhao26@ucsc.edu
//Li Jiang, 1411197, ljiang2@ucsc.edu
//Yilin Xu, 1441665, yxu48@ucsc.edu

public class ListActivity extends AppCompatActivity{

    private ListView vendList;
    private Button mapBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#56768f"));

        vendList = (ListView) findViewById(R.id.vendList);//vending machines list
        mapBut = (Button) findViewById(R.id.mapBut);//Button to map

        //string list for list adapter
         String [] machineList = {
                "Opers",
                "S&E Library Bottom Floor",
                "Social Science 1",
                "Social Science 2",
                "Stevenson Recreation",
                "Cowell Turner Hall Laundry",
                "Engineering 1 first floor",
                "Mchenry Library Media Center",
                "Natural Science 2 2nd Floor",
                "Thimann Lab 1st Floor",
                "Office of registrar"
        };


        ListAdapter adapter = new ArrayAdapter(
                ListActivity.this, android.R.layout.simple_list_item_1, machineList);
        //set list adapter
        vendList.setAdapter(adapter);

        mapBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//Button listener
                Intent gotoList = new Intent(ListActivity.this, MapsActivity.class);
                startActivity(gotoList);
                finish();}
        });

        vendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//onlicklistener for listview

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//list listener
                Intent intent = new Intent(ListActivity.this,VendingActivity.class);
                intent.putExtra("VendingName",vendList.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });


    }
}

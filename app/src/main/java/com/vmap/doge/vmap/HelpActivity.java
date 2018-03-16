package com.vmap.doge.vmap;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    TextView helptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#56768f"));

        helptext = (TextView)findViewById(R.id.Helper_text_ID);
        helptext.setText("Welcome to Vmap!\n" +
                "A map for vending machines inside UCSC campus.\n" +
                "\n" +
                "The red marker on Map shows your current position.\n" +
                "Blue markers are vending machines inside campus.\n" +
                "You can access navigation function by clicking blue markers\n" +
                "and clicking the button popped out at the right bottom corner.\n" +
                "\n" +
                "If you want more informaiton, click List Button\n" +
                "Clicking on a specific vending machine gives you more information.\n" +
                "You can view stock class, payment method, machine status from here.\n" +
                "You can also update the machine status if status is incorrect.\n" +
                "Navigation function is also available by clicking the navigation button.\n" +
                "\n" +
                "Enjoy Vmap!");
    }
}

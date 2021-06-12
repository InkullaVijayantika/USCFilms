package com.example.uscfilms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {
    TextView rating;
    TextView heading;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_review);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorSplash));

        rating = findViewById(R.id.reviewrating);
        heading = findViewById(R.id.reviewheading);
        content = findViewById(R.id.reviewcontent);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Integer stars = intent.getIntExtra("Rating",0);
        String author = intent.getStringExtra("Heading");
        String review = intent.getStringExtra("Content");

        rating.setText(stars.toString()+"/5");
        heading.setText(author);
        content.setText(review);



//        initViews();
//        setData();
    }
    private void setData(){
//        textView.setText("In Details Page");
    }

//    private void initViews(){
//        textView = findViewById(R.id.sampleText);
//        textView.setText("Hello");
//
//    }
}
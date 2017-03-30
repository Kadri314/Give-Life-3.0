package com.example.kareemkanaan.cardview2;

import android.content.Intent;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CaseDetails extends AppCompatActivity {

   /* CostumSwipeAdapter adapter;
    ViewPager viewPager;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);
        TextView title= (TextView) findViewById(R.id.textView11);
        TextView location= (TextView) findViewById(R.id.textView9);
        TextView description= (TextView) findViewById(R.id.textView);
        TextView needs= (TextView) findViewById(R.id.textView7);
        TextView phone=(TextView) findViewById(R.id.textView5);

        Case caseObject= getIntent().getParcelableExtra("case");

        title.setText(caseObject.getStr());
        location.setText(caseObject.getLocation());
        description.setText(caseObject.getDescription());
        needs.setText(caseObject.getNeeds());
        phone.setText(caseObject.getPhone());

        List<Integer> images= caseObject.getImgs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        CostumSwipeAdapter adapter = new CostumSwipeAdapter(this,images);
        viewPager.setAdapter(adapter);

    }
}
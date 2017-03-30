package com.example.kareemkanaan.cardview2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NavigateByItem extends AppCompatActivity {
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_by_item);


        // getting references to buttons (if not following option of automatically creating buttons)
        final Button all = (Button) findViewById(R.id.allButton);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);
        final Button button6 = (Button) findViewById(R.id.button6);

        // getting the message which was sent from NavigateByType Acitivity
        final String type = getIntent().getExtras().getString("type");
        // connect to dataBase to get the items of type that is passed from NavigateByType activity
            databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> dataHolder= new ArrayList<String>();
                for(DataSnapshot child: dataSnapshot.child("main_Items").child(type).getChildren()){
                    /* if we can create buttons from here then, for each iteration we create button,
                    set his text according to child.getValue(),  add action Listener to it, and finally add it into Layout*/
                    // but i will add it manially since we are lacking the option of creating buttons from here
                    dataHolder.add(child.getKey());
                }
                button2.setText(dataHolder.get(0));button3.setText(dataHolder.get(1));all.setText("All");
                button4.setText(dataHolder.get(2));button5.setText(dataHolder.get(3));button6.setText(dataHolder.get(4));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //preparte to send message  into MainActivity
        final Intent intent = new Intent(NavigateByItem.this, MainActivity.class);
        intent.putExtra("type",type);


        // add action listener to buttons
        all.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByItem.this.startActivity(intent);
            }
        });

    }
 }


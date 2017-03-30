package com.example.kareemkanaan.cardview2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NavigateByType extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_by_type);

        // getting references to buttons (if not following option of automatically creating buttons)
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);
        final Button button6 = (Button) findViewById(R.id.button6);
        final Button signOut = (Button) findViewById(R.id.signOutButton);
        final List<String> dataHolder= new ArrayList<String>();

        // connect to dataBase to get the types(main_items)
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.child("main_Items").getChildren()){
                    /* if we can create buttons from here then, for each iteration we create button,
                    set his text according to child.getValue(),  add action Listener to it, and finally add it into Layout*/
                    // but i will add it manially since we are lacking the option of creating buttons from here
                    dataHolder.add(child.getKey());
                }
                button1.setText(dataHolder.get(0));button2.setText(dataHolder.get(1));button3.setText(dataHolder.get(2));
                button4.setText(dataHolder.get(2));button5.setText(dataHolder.get(3));button6.setText(dataHolder.get(4));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //preparte to send message  into NavigateByItem Activity
        final Intent intent = new Intent(NavigateByType.this, NavigateByItem.class);
        // add action listener to buttons
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                Log.v("message",b.getText().toString());
                intent.putExtra("type",b.getText().toString());
                NavigateByType.this.startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByType.this.startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByType.this.startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByType.this.startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByType.this.startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // passing message to the upcomming activity
                Button b=(Button)v;
                intent.putExtra("type",b.getText());
                NavigateByType.this.startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(NavigateByType.this,NLogIn.class));
            }
        });


    }
}


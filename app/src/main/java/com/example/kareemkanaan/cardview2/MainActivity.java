package com.example.kareemkanaan.cardview2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<Case> myDataset= new ArrayList<Case>();;
    private Spinner typeSpinner,itemSpinner;
    private ArrayAdapter<CharSequence> adp1,adp2;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    //private  List<Uri> imgs;
    private List<Integer> a;
    private Random random;

    private Button fillterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing item and type spinners and
        addValuetoSpinner();
        // adding action listener to filter button :
        fillterButton= (Button) findViewById(R.id.fillterButton);
        fillterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,NavigateByType.class));
//                if(typeSpinner.getVisibility()==View.VISIBLE){
//                    typeSpinner.setVisibility(View.GONE);
//                    itemSpinner.setVisibility(View.GONE);
//                } else typeSpinner.setVisibility(View.VISIBLE);
            }
        });


        // constructing the recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mAdapter = new MyAdapter(this.myDataset);
        mRecyclerView.setAdapter(mAdapter);
        // retreiving data from Database(firebase), insert it into myDataSet then update mAdapter with new Data
        addimgsToList();
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> dataHolder= new ArrayList<String>();
                // data holder will have the data as follwing :
                // dataHolder=[Description,ImgSource,Location,Needs,Phone#,Title]; // according to how data are sorted in Firebase
                for (DataSnapshot parent : dataSnapshot.child("Requests").child("AllRequests").getChildren()) {
                    for (DataSnapshot child : parent.getChildren()) {
                        dataHolder.add((String) child.getValue());
                    }
                    // create new case and add it to myDataSet
                    createNewCase(dataHolder);
                    // clearing data holder for the next iteration: to get another new case
                    dataHolder.clear();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();




        // adding action listener on clicking on of the card view
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(),CaseDetails.class);
                        intent.putExtra("case", myDataset.get(position));
                        startActivity(intent);

                    }
                })
        );
        // adding action lsitener to openCase button
        Button openCaseButton =(Button) findViewById(R.id.openCaseButton);
        openCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), OpenCase.class);
                startActivity(Intent);
            }
        });



    }
    public void createNewCase(List<String> dataHolder){
        // according to how data are sorted in Firebase:
        // dataHolder=[Description,ImgsSource,Location,Needs,Phone#,Title,Description,ImgsSource,Location,Needs,Phone#,Title];
        String des,imgsSource,location,needs,phone,title;
        des=dataHolder.get(0);imgsSource=dataHolder.get(1);location=dataHolder.get(2);needs=dataHolder.get(3);phone=dataHolder.get(4);title=dataHolder.get(5);
        List<Integer> imgs = new ArrayList<>();
        int n1=(int)(Math.random()*a.size());
        int n2=(int)(Math.random()*a.size());
        imgs.add(a.get(n1));
        imgs.add(a.get(n2));
        myDataset.add(new Case(title,a.get(n1),imgs,location,needs,des,phone));
        mAdapter.notifyDataSetChanged();

    }

    public void addValuetoSpinner() {
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        itemSpinner = (Spinner) findViewById(R.id.itemSpinner);

        adp1 = ArrayAdapter.createFromResource(MainActivity.this,R.array.mainitems,android.R.layout.simple_list_item_1);
        adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Appliances,android.R.layout.simple_list_item_1);
        typeSpinner.setAdapter(adp1);
        itemSpinner.setAdapter(adp2);

        // adding action listener to typeSpinner
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Home,android.R.layout.simple_list_item_1);

                }else if(position==1){
                    myDataset.remove(0);
                    if(mAdapter!=null)
                        mAdapter.update(myDataset);
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Clothes,android.R.layout.simple_list_item_1);
                }else if(position==2){
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Appliances,android.R.layout.simple_list_item_1);
                }else if(position==3){
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Books,android.R.layout.simple_list_item_1);
                }else if(position==4){
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Medication,android.R.layout.simple_list_item_1);
                }else{
                    adp2=ArrayAdapter.createFromResource(MainActivity.this,R.array.Toys,android.R.layout.simple_list_item_1);
                }
                itemSpinner.setAdapter(adp2);
                itemSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public Uri getImgUri(String filePath){
        final Uri[] currentURI = {null};
        storageReference.child("images").child(filePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.v("LoveLove","123123");
                currentURI[0] =uri;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.v("321321","321312");
            }
        });
        return currentURI[0];
    }
    public void addimgsToList(){
        random= new Random();
        random.nextInt();
         a = new ArrayList<>();


        a.add(R.drawable.case104);
        a.add(R.drawable.homeless);
        a.add(R.drawable.case103);
        a.add(R.drawable.case8);
        a.add(R.drawable.case101);
        a.add(R.drawable.case9);
        a.add(R.drawable.case106);
        a.add(R.drawable.case10);
        a.add(R.drawable.case12);
        a.add(R.drawable.case11);
        a.add(R.drawable.case9);
        a.add(R.drawable.case101);
        a.add(R.drawable.case102);
        a.add(R.drawable.case12);
        a.add(R.drawable.case8);
        a.add(R.drawable.case102);
        a.add(R.drawable.case105);
        a.add(R.drawable.case103);
        a.add(R.drawable.case101);
    }
}
package com.example.kareemkanaan.cardview2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class OpenCase extends AppCompatActivity {
    DatabaseReference databaseReference;
    Spinner spinType, spinItem;
    Button add,submit,addImg;
    EditText etTitle,etDescription,etNumber,etLocation;
    ArrayList<Spinner> spinners ;
    private StorageReference storageReference;
    static final int MAX_ROWS=3, GALLARY_INTENT=2121;
    int rowsNumber=1;
    Uri imgUrl;
    private ProgressDialog progressDialog;
    ArrayAdapter<CharSequence> adapterType,adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_case);

        // getting references to the objects:
        getReference();

        // setting the adapter for the spinType:
        adapterType = ArrayAdapter.createFromResource(OpenCase.this, R.array.mainitems, android.R.layout.simple_list_item_1);
        spinType.setAdapter(adapterType);

        // adding action listener to spinType Button:
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Home, android.R.layout.simple_list_item_1);
                } else if (position == 1){
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Clothes, android.R.layout.simple_list_item_1);
                }else if (position == 2){
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Appliances, android.R.layout.simple_list_item_1);
                }else if (position == 3){
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Books, android.R.layout.simple_list_item_1);
                }else if (position == 4){
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Medication, android.R.layout.simple_list_item_1);
                }else if (position == 5) {
                    adapterItems = ArrayAdapter.createFromResource(OpenCase.this, R.array.Toys, android.R.layout.simple_list_item_1);
                }
                spinItem.setAdapter(adapterItems);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // adding action listener to submit Button:
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
                //finish();
            }
        });

        // adding action listener to add Button:
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnotherRow();
            }
        });
        // adding action listener to addImg Button
        addImg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLARY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLARY_INTENT && resultCode==RESULT_OK){
            Log.v("sds","hello world");
            imgUrl= data.getData();
            addImg.setText("Image Selected. Re-Select?");
        }
    }


    public void addAnotherRow(){
        rowsNumber++;
        if(rowsNumber>=MAX_ROWS){
            add.setEnabled(false);
        }
        final TableLayout table = (TableLayout)findViewById(R.id.table);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);
        final TableRow tr = new TableRow(OpenCase.this);
        table.setStretchAllColumns(true);
        // creating type spinner and item spinner
        Spinner spType = new Spinner(OpenCase.this);
        final Spinner spItem = new Spinner(OpenCase.this);
        spinners.add(spType);
        spinners.add(spItem);
        // creating remove button
        Button remove= new Button(OpenCase.this);

        remove.setId(rowsNumber);
        remove.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        remove.setBackgroundColor(Color.TRANSPARENT);
        remove.setText("-");


        // adding action listener to remove button
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                table.removeView(tr);
                Log.v("sds",v.getId()+"");
                int index=v.getId()*2;
                spinners.remove(index-1);
                spinners.remove(index-2);
                rowsNumber--;
                if(rowsNumber<MAX_ROWS){
                    add.setEnabled(true);
                }
            }
        });

        // adding adapter to both item and type spinner
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.mainitems, android.R.layout.simple_list_item_1);
        spType.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(OpenCase.this, R.array.Appliances, android.R.layout.simple_list_item_1);
        spItem.setAdapter(adapter2);




        tr.addView(spType);
        tr.addView(spItem);
        tr.addView(remove);
        table.addView(tr);
        // adding action listener to sptype spinner
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter=null;
                if (position == 0) {
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Home, android.R.layout.simple_list_item_1);
                } else if (position == 1){
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Clothes, android.R.layout.simple_list_item_1);
                }else if (position == 2){
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Appliances, android.R.layout.simple_list_item_1);
                }else if (position == 3){
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Books, android.R.layout.simple_list_item_1);
                }else if (position == 4){
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Medication, android.R.layout.simple_list_item_1);
                }else if (position == 5) {
                    adapter = ArrayAdapter.createFromResource(OpenCase.this, R.array.Toys, android.R.layout.simple_list_item_1);
                }
                spItem.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void getReference(){
        submit = (Button)findViewById(R.id.submit);
        spinType = (Spinner)findViewById(R.id.type);
        spinItem = (Spinner) findViewById(R.id.item);
        progressDialog= new ProgressDialog(OpenCase.this);
        add = (Button)findViewById(R.id.add);
        etTitle=(EditText) findViewById(R.id.titletext);
        etDescription=(EditText) findViewById(R.id.description);
        etNumber=(EditText) findViewById(R.id.numbertext);
        etLocation=(EditText) findViewById(R.id.locationtext);
        addImg=(Button)findViewById(R.id.addImg);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        spinners= new ArrayList<>();
        spinners.add(spinType);
        spinners.add(spinItem);
    }

    public void submitData(){
        String title,des,location,needs,phone;
        title=etTitle.getText().toString().trim();
        des=etDescription.getText().toString().trim();
        location=etLocation.getText().toString().trim();
        phone=etNumber.getText().toString().trim();
        // initializing needs
        needs="";
        for(int i=0; i<spinners.size(); i+=2){
            Spinner spItem=spinners.get(i+1);
            String item=spItem.getSelectedItem().toString();
            if(i==0){
                needs+=item;
            }else{
                needs+=", "+item;
            }

        }
        // checking if all info are inserted by the user
        if(TextUtils.isEmpty(title)){
            // email is empty
            Toast.makeText(this,"Please enter Title",Toast.LENGTH_SHORT).show();
            // stoping executing the function
            return;
        }
        if(TextUtils.isEmpty(des)){
            // email is empty
            Toast.makeText(this,"Please enter Description",Toast.LENGTH_SHORT).show();
            // stoping executing the function
            return;
        }
        if(TextUtils.isEmpty(location)){
            // email is empty
            Toast.makeText(this,"Please enter Location",Toast.LENGTH_SHORT).show();
            // stoping executing the function
            return;
        }
        if(TextUtils.isEmpty(phone)){
            // email is empty
            Toast.makeText(this,"Please enter Phone",Toast.LENGTH_SHORT).show();
            // stoping executing the function
            return;
        }
        if(imgUrl==null){
            // user Didn't upload an img
            Toast.makeText(this,"Please Upload an image",Toast.LENGTH_SHORT).show();
            return;
        }
        // if we reach here the data are correct
        progressDialog.setMessage("Posting ...");
        progressDialog.show();
        // what left add image then insert data into all and
        //1- uploading image to FireBase
        String imgUniqueName=databaseReference.push().getKey();
        StorageReference filePath=storageReference.child("images").child(imgUniqueName);

        filePath.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Toast.makeText(OpenCase.this,"Image Uploaded Successfuly",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OpenCase.this,"Something Went Wrong with Img uploading try again please!",Toast.LENGTH_SHORT).show();
                return;
            }
        });
        //2- storing Data in CaseData object
        CaseData case1= new CaseData(title,phone,location,des,imgUniqueName,needs);
        //3- storing case Data in firebase under path: root/Requests/type/item/
        for(int i=0; i<spinners.size(); i+=2){
            Spinner spType=spinners.get(i);
            Spinner spItem=spinners.get(i+1);
            String type= spType.getSelectedItem().toString();
            String item=spItem.getSelectedItem().toString();
            databaseReference.child("Requests").child(type).child(item).push().setValue(case1);
        }

        //4- storing Case data in firebase under path root/requests/AllRequests/
        databaseReference.child("Requests").child("AllRequests").push().setValue(case1);
        // done From posting data
        progressDialog.dismiss();
        Toast.makeText(OpenCase.this,"Posted Successfuly!",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(OpenCase.this,MainActivity.class));

    }

}
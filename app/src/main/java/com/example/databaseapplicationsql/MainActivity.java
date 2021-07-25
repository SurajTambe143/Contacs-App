package com.example.databaseapplicationsql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2,et3;
    Button b1,b2,b3,b4;
    DB_Connectivity g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);


        g=new DB_Connectivity(this);
        //SQLiteDatabase db=g.getReadableDatabase();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=et1.getText().toString();
                String phone1=et2.getText().toString();
                String gender1=et3.getText().toString();

                if (name1.equals("") || phone1.equals("") || gender1.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter all The Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean i=g.insert_data(name1,phone1,gender1);
                    if(i==true)
                    {
                        Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Not Successfull", Toast.LENGTH_SHORT).show();
                    }
                }
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
                a.setMessage("Are You Sure You Want to Delete ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=et1.getText().toString();
                                Boolean i=g.delete_data(name);
                                if(i==true){
                                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No",null);
                AlertDialog c=a.create();
                c.show();

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor t=g.getinfo();
                if(t.getCount()==0){
                    Toast.makeText(MainActivity.this, "No DATA Found", Toast.LENGTH_SHORT).show();
                }

                StringBuffer buffer=new StringBuffer();
                while(t.moveToNext()){
                    buffer.append("name: "+t.getString(1)+"\n");
                    buffer.append("phone: "+t.getString(2)+"\n");
                    buffer.append("gender: "+t.getString(3)+"\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Contacts");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et1.getText().toString();
                String phone=et2.getText().toString();
                String gender=et3.getText().toString();
                Boolean i=g.update_data(name,phone,gender);
                if(i==true){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
        a.setMessage("Do you Want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       MainActivity.super.onBackPressed();;
                    }
                })
                .setNegativeButton("No",null);
        AlertDialog c=a.create();
        c.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m= getMenuInflater();
        m.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sql:
                Toast.makeText(this, "Already Using SQL", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.fs:
                Toast.makeText(this, "Yet to make", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
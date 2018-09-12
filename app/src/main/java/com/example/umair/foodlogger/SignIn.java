package com.example.umair.foodlogger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.umair.foodlogger.Common.Common;
import com.example.umair.foodlogger.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
EditText EdtPhone,EdtPassword;
Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        EdtPassword=(EditText)findViewById(R.id.edtPassword);
        EdtPhone=(EditText)findViewById(R.id.edtPhone);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);

        //Init Firebase
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog =new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please Waiting....");
                mDialog.show();

               table_user.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       //Check if user not exist in database
                       if (dataSnapshot.child(EdtPhone.getText().toString()).exists()) {
                           //Get User Information
                           mDialog.dismiss();
                           User user = dataSnapshot.child(EdtPhone.getText().toString()).getValue(User.class);
                           if (user.getPassword().equals(EdtPassword.getText().toString())) {
                              Intent homeIntent = new Intent(SignIn.this,Home.class);
                               Common.currentUser=user;
                               startActivity(homeIntent);
                               finish();
                           } else {
                               Toast.makeText(SignIn.this, "Sign In Failed!!!", Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           mDialog.dismiss();
                           Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

            }
        });
    }
}

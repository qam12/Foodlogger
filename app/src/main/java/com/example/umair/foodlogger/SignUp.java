package com.example.umair.foodlogger;
import com.example.umair.foodlogger.Model.User;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.String;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
EditText EdtPhone,EdtName,EdtPassword;
Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EdtName = (EditText)findViewById(R.id.edtName);
        EdtPassword = (EditText)findViewById(R.id.edtPassword);
        EdtPhone = (EditText)findViewById(R.id.edtPhone);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog =new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Waiting....");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(EdtPhone.getText().toString()).exists()){
                        mDialog.dismiss();
                            Toast.makeText(SignUp.this,"Phone Number already registered",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            User user =new User(EdtName.getText().toString(),EdtPassword.getText().toString());
                            table_user.child(EdtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Sign Up Successful",Toast.LENGTH_SHORT).show();
                            finish();
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

package com.example.android.makeupbook.accounts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.makeupbook.MainActivity;
import com.example.android.makeupbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindViews({R.id.email_editField,R.id.password_editField,R.id.username_field})
    List<EditText> signUpDetails;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        //Get Firebase auth instance
        auth =FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");
    }

    public void alreadyMember(View view){
        startActivity(new Intent(SignUpActivity.this,SigningActivity.class));
    }

    public void signUpPressed(View view) {
        final String userName = signUpDetails.get(2).getText().toString().trim();
        final String email = signUpDetails.get(0).getText().toString().trim();
        String password = signUpDetails.get(1).getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.usernameMissingToast), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordToast), Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordTooShotToast), Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, getResources().getString(R.string.registrationFailed) + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            User user = new User(userName,email);

                            mDatabaseReference.child(auth.getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                                Intent homeScreenIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                                startActivity(homeScreenIntent);
                                                finish();
                                    }else {
                                        Toast.makeText(SignUpActivity.this, getResources().getString(R.string.registrationFailed) + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        }
                    }
                });
    }
}
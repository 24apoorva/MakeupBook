package com.example.android.makeupbook.accounts;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.makeupbook.MainActivity;
import com.example.android.makeupbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SigningActivity extends AppCompatActivity {

    @BindViews({R.id.username_id, R.id.password_id})
    List<EditText> user_login_views;
//    @BindViews({R.id.login_id,R.id.signUp_id})
//    List<Button> authenticationButtons;
//    @BindView(R.id.forgotPassword_id)
//    TextView forgotPassword;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;

    //private final String TAG = SigningActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);
        ButterKnife.bind(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() != null) {
            openHomeScreen();
        }

    }

    //When SignUp button pressed
    @OnClick(R.id.signUp_id)
    public void signUpButtonPressed(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigningActivity.this,SignUpActivity.class));
            }
        });
    }

    //geting userName and displaying home screen
    private void openHomeScreen(){
        String id = auth.getCurrentUser().getUid();
        mFirebaseDatabase.getReference("users/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(User.class).getUserName().toUpperCase();
                Intent homeScreenIntent = new Intent(SigningActivity.this,MainActivity.class);
                homeScreenIntent.putExtra(MainActivity.USERNAME,name);
                startActivity(homeScreenIntent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //When forgot password is clicked
    @OnClick(R.id.forgotPassword_id)
    public void forgotEmailPressed(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigningActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    @OnClick(R.id.login_id)
    public void logInButtonPressed(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Getting data from edit text fields.
                String userName = user_login_views.get(0).getText().toString();
                final String password = user_login_views.get(1).getText().toString();

                //Checking if user name is empty
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.usernameMissingToast), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Checking if password is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordToast), Toast.LENGTH_SHORT).show();
                    return;
                }

                //Checking user authentication when login button is clicked
                auth.signInWithEmailAndPassword(userName,password)
                        .addOnCompleteListener(SigningActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    if (password.length() < 6) {
                                        user_login_views.get(1).setError(getString(R.string.passwordTooShotToast));
                                    } else {
                                        Toast.makeText(SigningActivity.this, getResources().getString(R.string.auth_failed)+task.getException(), Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    openHomeScreen();
                                }
                            }
                        });
            }
        });


    }

}

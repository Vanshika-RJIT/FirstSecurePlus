package com.example.firstsecureplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileAuth extends AppCompatActivity {
    EditText t1, t2;
    Button b1, b2;
    FirebaseAuth mauth;
    String Codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);
        t1 = findViewById(R.id.editText8);
        t2 = findViewById(R.id.editText10);
        b1 = findViewById(R.id.button8);
        b2 = findViewById(R.id.button7);
    }

    private void Send(View view) {
        String phone = t1.getText().toString();
        if (phone.isEmpty()) {
            t1.setError("Mobile number is required");
            t1.requestFocus();
            return;
        }
        if (phone.length() < 10) {
            t1.setError("Please enter a valid mobile number");
            t1.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Codesent=s;

        }
    };


    private void Verify(View view) {
        String code = t2.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Codesent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MobileAuth.this,"Submitted  Verification Code Successfully",Toast.LENGTH_SHORT).show();


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(MobileAuth.this,"Submitted Incorrect Verification Code",Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });
    }

}



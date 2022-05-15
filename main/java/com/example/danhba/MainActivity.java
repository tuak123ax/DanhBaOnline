package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText mail,pass;
    Button signin,signup;
    public static FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        AnhXa();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangKy();
            }
        });
    }
    private void AnhXa()
    {
        mail=(EditText)findViewById(R.id.DKEmail);
        pass=(EditText)findViewById(R.id.DKPass);
        signin=(Button)findViewById(R.id.DKBut);
        signup=(Button)findViewById(R.id.dangky);
        mAuth=FirebaseAuth.getInstance();
    }
    private void DangKy()
    {
        Intent intent=new Intent(MainActivity.this,DangKy.class);
        startActivity(intent);
    }
    private void DangNhap()
    {
        String email=mail.getText().toString();
        String password=pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                            Intent home=new Intent(MainActivity.this,Home.class);
                            startActivity(home);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class DangKy extends AppCompatActivity {

    EditText dkmail,dkpass,dkconfirmpass;
    Button back,dangkyBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dang_ky);

        AnhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dangkyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=dkmail.getText().toString();
                String password=dkpass.getText().toString();
                String confirmPass=dkconfirmpass.getText().toString();
                if(email.equals("")||password.equals("")||confirmPass.equals(""))
                {
                    Toast.makeText(DangKy.this,"Hãy điền đủ thông tin!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!password.equals(confirmPass))
                    {
                        Toast.makeText(DangKy.this,"Password không giống nhau!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        MainActivity.mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(DangKy.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(DangKy.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(DangKy.this,"Đăng ký thất bại!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
    private void AnhXa()
    {
        dkmail=(EditText)findViewById(R.id.DKEmail);
        dkpass=(EditText)findViewById(R.id.DKPass);
        dkconfirmpass=(EditText)findViewById(R.id.ConfirmPass);
        back=(Button)findViewById(R.id.backBut);
        dangkyBut=(Button)findViewById(R.id.DKBut);
    }
}
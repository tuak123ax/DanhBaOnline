package com.example.danhba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    EditText name,phone;
    Button back,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);

        AnhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=name.getText().toString();
                String sodienthoai=phone.getText().toString();
                if(hoten.equals("")||sodienthoai.equals(""))
                {
                    Toast.makeText(AddActivity.this,"Hãy nhập đủ thông tin!",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean check=false;
                    Data data=new Data(hoten,sodienthoai);
                    for(int i=0;i<Home.dataList.size();i++)
                    {
                        if(data.getName().equals(Home.dataList.get(i).name)&&
                                data.getSdt().equals(Home.dataList.get(i).sdt))
                        {
                            check=true;
                            break;
                        }
                    }
                    if(check==true)
                    {
                        Toast.makeText(AddActivity.this,"Số liên lạc đã tồn tại!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Home.mDatabase.child(Home.user.getUid()).push().setValue(data);
                        Toast.makeText(AddActivity.this,"Thêm thành công!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
    private void AnhXa()
    {
        name=(EditText)findViewById(R.id.Ten);
        phone=(EditText)findViewById(R.id.SDT);
        back=(Button)findViewById(R.id.Back);
        add=(Button)findViewById(R.id.addBut);
    }
}
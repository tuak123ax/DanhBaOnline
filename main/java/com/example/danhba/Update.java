package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {

    EditText ten,sdt;
    Button update, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        AnhXa();
        Intent intent=getIntent();
        Data data= (Data) intent.getSerializableExtra("data");
        ten.setText(data.name);
        sdt.setText(data.sdt);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten=ten.getText().toString();
                String phone=sdt.getText().toString();
                if(hoten.equals("")||phone.equals(""))
                {
                    Toast.makeText(Update.this,"Hãy nhập đủ thông tin!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Data newData=new Data(hoten,phone);
                    Query query = Home.mDatabase.child(Home.user.getUid()).orderByChild("sdt").equalTo(data.sdt);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                snapshot.getRef().setValue(newData);
                            }
                            for(int i=0;i<Home.dataList.size();i++)
                            {
                                if(data.getName().equals(Home.dataList.get(i).name)&&
                                        data.getSdt().equals(Home.dataList.get(i).sdt))
                                {
                                    Home.dataList.set(i,newData);
                                    break;
                                }
                            }
                            Home.dataAdapter.notifyDataSetChanged();
                            Toast.makeText(Update.this,"Sửa thành công!",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }
        });
    }
    public void AnhXa()
    {
        ten=(EditText)findViewById(R.id.updateTen);
        sdt=(EditText)findViewById(R.id.updateSDT);
        update=(Button)findViewById(R.id.updateBut);
        back=(Button)findViewById(R.id.updateBack);
    }
}
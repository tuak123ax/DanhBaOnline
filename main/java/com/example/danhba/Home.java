package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    public static FirebaseUser user;
    public static DatabaseReference mDatabase;
    public static ArrayList<Data> dataList;
    public static DataAdapter dataAdapter;
    public static ListView listView;
    public static int position;
    public SearchView searchView;
    public String lastQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        listView=(ListView)findViewById(R.id.listView);
        user= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dataList=new ArrayList<>();
        LoadData();
        dataAdapter=new DataAdapter(this,R.layout.data_row,dataList);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dataAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.search: return true;
            case R.id.add:
                Intent addIntent=new Intent(Home.this,AddActivity.class);
                startActivity(addIntent);
                break;
            case R.id.out:
                finish();
                break;
            case R.id.help:Help();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void LoadData(){
        mDatabase.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Data data=snapshot.getValue(Data.class);
                dataList.add(data);
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void Help()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("H?????ng d???n");
        alertDialog.setMessage("1.Nh???n v??o bi???u t?????ng k??nh l??p ????? t??m ki???m.\n"
        +"2.Nh???n v??o bi???u t?????ng d???u + ????? th??m s??? li??n l???c m???i.\n"
        +"3.Nh???n m???t l???n v??o t??n ho???c s??? ??i???n tho???i b???t k??? ????? s???a th??ng tin.\n"
        +"4.Gi??? im v??o t??n ho???c s??? ??i???n tho???i b???t k??? ????? x??a.\n"
        +"5.Nh???n v??o bi???u t?????ng G???I ????? chuy???n qua m??n h??nh g???i.\n"
        +"6.Nh???n v??o bi???u t?????ng TIN NH???N ????? chuy???n qua m??n h??nh tin nh???n.");
        alertDialog.setPositiveButton("???? hi???u", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int ii) {

            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
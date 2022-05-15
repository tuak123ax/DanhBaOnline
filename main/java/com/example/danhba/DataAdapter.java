package com.example.danhba;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class DataAdapter extends BaseAdapter implements Filterable {
    public Context context;
    public int layout;
    public List<Data> listData;
    public List<Data> listDataFilter;

    public DataAdapter(Context context, int layout, List<Data> listData) {
        this.context = context;
        this.layout = layout;
        this.listData = listData;
        this.listDataFilter=listData;
    }

    @Override
    public int getCount() {
        return listDataFilter.size();
    }

    @Override
    public Object getItem(int i) {
        return listDataFilter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = listData.size();
                    filterResults.values = listData;

                }else{
                    List<Data> results= new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Data item:listData){
                        if(item.getName().toLowerCase().contains(searchStr) || item.getSdt().contains(searchStr)){
                            results.add(item);

                        }
                        filterResults.count = results.size();
                        filterResults.values = results;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                listDataFilter = (List<Data>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    private class ViewHolder{
        TextView hoten;
        TextView sdt;
        ImageButton call;
        ImageButton message;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder=new ViewHolder();
        if(view==null)
        {
            view=inflater.inflate(layout,null);
            viewHolder.hoten=view.findViewById(R.id.textView4);
            viewHolder.sdt=view.findViewById(R.id.textView5);
            viewHolder.call=view.findViewById(R.id.imageButtonCall);
            viewHolder.message=view.findViewById(R.id.imageButtonMess);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.hoten.setText(listDataFilter.get(i).name);
        viewHolder.sdt.setText(listDataFilter.get(i).sdt);
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action action=new Action(context);
                action.Call(listDataFilter.get(i));
            }
        });
        viewHolder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action action=new Action(context);
                action.Message(listDataFilter.get(i));
            }
        });
        viewHolder.hoten.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DeleteData(view,i);
                return true;
            }
        });
        viewHolder.sdt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DeleteData(view,i);
                return true;
            }
        });
        viewHolder.hoten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData(view,i);
            }
        });
        viewHolder.sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData(view,i);
            }
        });
        return view;
    }
    public void DeleteData(View view,int i)
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle("Xoá số liên lạc");
        alertDialog.setMessage("Bạn có chắc chắn muốn xóa số của "+listDataFilter.get(i).name+" không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int ii) {
                Query query = Home.mDatabase.child(Home.user.getUid()).orderByChild("sdt").equalTo(listDataFilter.get(i).sdt);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        listData.remove(Home.listView.getPositionForView(view));
                        notifyDataSetChanged();
                        Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
    public void UpdateData(View view,int i){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle("Sửa thông tin liên lạc");
        alertDialog.setMessage("Bạn có muốn sửa thông tin của "+listDataFilter.get(i).name+" không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int ii) {
                Action action=new Action(context);
                action.Update(listDataFilter.get(i));
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
    class Action extends AppCompatActivity{
        private Context controller;


        public Action(Context context)
        {
            controller=context;
        }
        public void Call(Data data){
            Intent intent=new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+data.sdt));
            controller.startActivity(intent);
        }

        public void Message(Data data){
            Intent intent=new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sms:"+data.sdt));
            intent.putExtra("sms_body","Hello "+data.name);
            controller.startActivity(intent);
        }
        public void Update(Data data)
        {
            Intent intent=new Intent(controller,Update.class);
            intent.putExtra("data",data);
            controller.startActivity(intent);
        }
    }
}

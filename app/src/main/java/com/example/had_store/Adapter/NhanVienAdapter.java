package com.example.had_store.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.had_store.DAO.NhanVienDao;
import com.example.had_store.Fragment.Fragment_NhanVien;
import com.example.had_store.Model.NhanVien;
import com.example.had_store.R; // Replace with your actual package name
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
    private NhanVienDao dao; // Add this line
    private Context context;
    private List<NhanVien> nhanVienList;

    // Update the constructor to receive NhanVienDao
    public NhanVienAdapter(Context context, List<NhanVien> nhanVienList, NhanVienDao dao) {
        this.context = context;
        this.nhanVienList = nhanVienList;
        this.dao = dao; // Add this line
    }
    @Override
    public int getCount() {
        return nhanVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nhanvien, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMaNv = convertView.findViewById(R.id.tvMaNv);
            viewHolder.tvTenNv = convertView.findViewById(R.id.tvTenNv);
            viewHolder.tvSoNv = convertView.findViewById(R.id.tvSoNv);
            viewHolder.tvEmailNv = convertView.findViewById(R.id.tvEmailNv);
            viewHolder.anhnv = convertView.findViewById(R.id.imgAnhNv);
            viewHolder.deleteNv = convertView.findViewById(R.id.imgDeleteNv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        NhanVien nhanVien = nhanVienList.get(position);


        // Populate the data into the template view using the data object
        viewHolder.tvMaNv.setText("ID EMPLOYEE : " + nhanVien.getMaNv());
        viewHolder.tvTenNv.setText("NAME : " + nhanVien.getTenNv());
        viewHolder.tvSoNv.setText("TELL : " + nhanVien.getSoNv());
        viewHolder.tvEmailNv.setText("EMAIL : " + nhanVien.getEmailNv());

        viewHolder.deleteNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dao != null && nhanVien != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("DELETE");
                    builder.setMessage("Bạn có muốn xóa không ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int deletedRows = dao.delete(nhanVien.getMaNv());

                            // Update the list and notify the adapter about the change
                            if (deletedRows > 0) {
                                nhanVienList.remove(nhanVien);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xóa nhân viên thất bại ", Toast.LENGTH_SHORT).show();
                            }
                            dialogInterface.cancel();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    builder.show();
                } else {
                    Log.e("DeleteButton", "dao or nhanVien is null");
                }
            }
        });

        // Load image from URL using Picasso
        Picasso.get().load(nhanVien.getAnhNv()).into(viewHolder.anhnv);



        return convertView;
    }

    private static class ViewHolder {
        TextView tvMaNv;
        TextView tvTenNv;
        TextView tvSoNv;
        TextView tvEmailNv;
        ImageView anhnv,deleteNv;
        // Add more TextViews or other views based on your layout
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                List<NhanVien> filteredList = new ArrayList<>();

                for (NhanVien nhanVien : nhanVienList) {
                    if (nhanVien.getMaNv().toLowerCase().contains(charString)) {
                        filteredList.add(nhanVien);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // Update the filtered list and notify the adapter about the change
                nhanVienList = (List<NhanVien>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

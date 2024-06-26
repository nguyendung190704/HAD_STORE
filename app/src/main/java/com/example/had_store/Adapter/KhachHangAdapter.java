package com.example.had_store.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.had_store.DAO.KhachHangDao;

import com.example.had_store.Model.KhachHang;

import com.example.had_store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KhachHangAdapter extends BaseAdapter {
    private KhachHangDao dao; // Add this line
    private Context context;
    private List<KhachHang> khachHangList;

    // Update the constructor to receive NhanVienDao


    public KhachHangAdapter(Context context, List<KhachHang> khachHangList, KhachHangDao dao) {
        this.context = context;
        this.khachHangList = khachHangList;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return khachHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return khachHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMaKh = convertView.findViewById(R.id.tvMaKH);
            viewHolder.tvTenKh = convertView.findViewById(R.id.tvTenKH);
            viewHolder.tvsoKh = convertView.findViewById(R.id.tvSoKH);
            viewHolder.tvemailKh = convertView.findViewById(R.id.tvEmailKH);
            viewHolder.tvdiaChiKh = convertView.findViewById(R.id.tvdiaChiKH);
            viewHolder.tvanhKh = convertView.findViewById(R.id.imgAnhKH);
            viewHolder.imgDeleteKh = convertView.findViewById(R.id.imgDeleteKH);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (KhachHangAdapter.ViewHolder) convertView.getTag();
        }


        KhachHang khachHang = khachHangList.get(i);

        viewHolder.tvMaKh.setText("Mã : " + khachHang.getMaKh());
        viewHolder.tvTenKh.setText("Tên : " + khachHang.getTenKh());
        viewHolder.tvsoKh.setText("Tell : " + khachHang.getSoKh());
        viewHolder.tvemailKh.setText("Email : " + khachHang.getEmailKh());
        viewHolder.tvdiaChiKh.setText("Địa chỉ : " + khachHang.getDiaChiKh());

        String imagePath = khachHang.getAnhKh();
        if (imagePath != null && !imagePath.isEmpty()) {Picasso.get().load(imagePath).into(viewHolder.tvanhKh);
        } else {
            viewHolder.tvanhKh.setImageResource(R.drawable.erro);
        }

        viewHolder.imgDeleteKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dao != null && khachHang != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("DELETE");
                    builder.setMessage("Bạn có muốn xóa không ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int deletedRows = dao.delete(khachHang.getMaKh());

                            if (deletedRows > 0) {
                                khachHangList.remove(khachHang);
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
                    alert.show();
                } else {
                    Log.e("DeleteButton", "dao or khachHang is null");
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvMaKh, tvTenKh, tvdiaChiKh, tvemailKh, tvsoKh;
        ImageView imgDeleteKh, tvanhKh;
    }

    public void add(KhachHang khachHang) {
        khachHangList.add(khachHang);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                List<KhachHang> filteredList = new ArrayList<>();

                for (KhachHang khachHang : khachHangList) {
                    if (khachHang.getMaKh().toLowerCase().contains(charString)) {filteredList.add(khachHang);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                khachHangList = (List<KhachHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

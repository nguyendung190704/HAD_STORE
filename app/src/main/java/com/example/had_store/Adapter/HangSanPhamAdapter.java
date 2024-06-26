package com.example.had_store.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.had_store.DAO.HangSanPhamDao;
import com.example.had_store.Model.HangSanPham;
import com.example.had_store.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HangSanPhamAdapter extends RecyclerView.Adapter<HangSanPhamAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<HangSanPham> list;
    private final HangSanPhamDao hangSanPhamDao;

    public HangSanPhamAdapter(Context context, ArrayList<HangSanPham> list) {
        this.context = context;
        this.list = list;
        this.hangSanPhamDao = new HangSanPhamDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hangsanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HangSanPham hsp = list.get(position);
        holder.tvMaHang.setText("Mã hãng: " + String.valueOf(hsp.getMaHang()));
        holder.tvTenHang.setText("Tên hãng: " + hsp.getTenHang());
        holder.tvDiaChiHang.setText("Địa chỉ hãng: " + hsp.getDiaChiHang());

        Picasso.get().load(hsp.getAnhHang()).into(holder.imgAnhHSp);

        holder.imgDeleteHang.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("CẢNH BÁO");
            builder.setMessage("BẠN CÓ MUỐN XOÁ?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                if (hangSanPhamDao.delete(hsp.getMaHang())) {
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                Toast.makeText(context, "Không xoá", Toast.LENGTH_SHORT).show();
            });
            builder.show();
        });
        holder.imgupdateHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(hsp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define your views here
        TextView tvMaHang, tvTenHang, tvDiaChiHang;
        ImageView imgAnhHSp;
        Button imgupdateHang, imgDeleteHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize your views here using findViewById
            tvMaHang = itemView.findViewById(R.id.tvMaHang);
            tvTenHang = itemView.findViewById(R.id.tvTenHang);
            tvDiaChiHang = itemView.findViewById(R.id.tvDiaChiHang);
            imgAnhHSp = itemView.findViewById(R.id.imgAnhHSp);
            imgupdateHang = itemView.findViewById(R.id.imgupdateHang);
            imgDeleteHang = itemView.findViewById(R.id.imgDeleteHang);
        }
    }

    public void update(HangSanPham hssp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_updatehsp, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtmadvup = view.findViewById(R.id.txtmadvup);
        TextView txttendv = view.findViewById(R.id.txttendvup);
        EditText tvDiaChiHang = view.findViewById(R.id.txtdiachihangdv);
        EditText txtanhhang = view.findViewById(R.id.txtanhhangdv);
        Button btnupdate = view.findViewById(R.id.btnUpdateForm);

        txtmadvup.setText(String.valueOf(hssp.getMaHang()));
        txttendv.setText(hssp.getTenHang());
        tvDiaChiHang.setText(hssp.getDiaChiHang());
        txtanhhang.setText(hssp.getAnhHang());

        btnupdate.setOnClickListener(v -> {
            String maha = txtmadvup.getText().toString().trim();
            String tendv = txttendv.getText().toString().trim();
            String diachihang = tvDiaChiHang.getText().toString().trim();
            String anhhang = txtanhhang.getText().toString().trim();

            if (TextUtils.isEmpty(maha) || TextUtils.isEmpty(tendv) || TextUtils.isEmpty(diachihang) || TextUtils.isEmpty(anhhang)) {
                Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                try {
//                    int canNang = Integer.parseInt(anhhang);
//                    if (canNang < 1) {
//                        Toast.makeText(context, "Số cân nặng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
//                    } else {
                    hssp.setMaHang(Integer.parseInt(maha));
                    hssp.setTenHang(tendv);
                    hssp.setDiaChiHang(diachihang);
                    hssp.setAnhHang(anhhang);

                    int position = getAdapterPosition(); // Replace this with the correct position logic
                    if (hangSanPhamDao.update(hssp)) {
                        list.set(position, hssp);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                    //}
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Vui lòng nhập số cân nặng hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getAdapterPosition() {
        // Replace this with your logic to get the appropriate adapter position
        // Return the correct position in the adapter where the item is updated
        return 0;
    }
}

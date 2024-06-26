package com.example.had_store.Adapter;

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

import com.example.had_store.DAO.GioHangDao;
import com.example.had_store.DAO.HangSanPhamDao;
import com.example.had_store.DAO.SanPhamDao;
import com.example.had_store.Model.GioHang;
import com.example.had_store.Model.SanPham;
import com.example.had_store.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private List<SanPham> productList;
    private String userType;
    private String maKh;
    private HangSanPhamDao hangSanPhamDao;
    private GioHangDao gioHangDao;
    private SanPhamDao sanPhamDao;


    public SanPhamAdapter(Context context, List<SanPham> productList, String userType, String maKh) {
        this.context = context;
        this.productList = productList;
        this.userType = userType;
        this.maKh = maKh;
        this.hangSanPhamDao = new HangSanPhamDao(context);
        this.gioHangDao = new GioHangDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = productList.get(position);
        holder.tvMaSp.setText(String.valueOf("Mã sản phẩm: " + sanPham.getMasp()));
        holder.tvTenSp.setText("Tên: " + sanPham.getTenSp());
        holder.tvGiaSp.setText(String.valueOf("Giá: " + sanPham.getGiaSp()));
        holder.tvSoLuongSp.setText(String.valueOf("Số lượng: " + sanPham.getSoLuongSp()));
        holder.tvtrangthaiSp.setText("Trạng thái: " + sanPham.getTtSp());

        if (sanPham != null) {
            String tenHang = hangSanPhamDao.getTenHangById(sanPham.getMaHang());
            holder.tvMaHang.setText(String.valueOf("Tên Hãng: " + tenHang));
        }

        String imageUrl = sanPham.getAnhSp();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imgAnhSp);
        } else {
            // Handle the case where the image URL is null or empty
            // For example, set a placeholder image or hide the ImageView
        }

        if ("CUSTOMER".equals(userType)) {
            holder.imgDeleteSach.setVisibility(View.GONE);
            holder.btnupdatesp.setText("Thêm vào giỏ ");
            holder.btnupdatesp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    themGioHang(sanPham);
                }
            });
        } else {
            holder.imgDeleteSach.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CẢNH BÁO");
                builder.setMessage("BẠN CÓ MUỐN XOÁ K");
                builder.setPositiveButton("có", (dialogInterface, i) -> {
                    if (sanPhamDao.delete(sanPham.getMasp())) {
                        productList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Xoá ok", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xoá k đc", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("KHông", (dialogInterface, i) -> {
                    Toast.makeText(context, "không có", Toast.LENGTH_SHORT).show();
                });
                builder.show();
            });

            holder.btnupdatesp.setOnClickListener(view -> updateSanPham(sanPham, position));
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaSp, tvTenSp, tvGiaSp, tvSoLuongSp, tvMaHang, tvtrangthaiSp;
        ImageView imgAnhSp;
        Button btnupdatesp, imgDeleteSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSp = itemView.findViewById(R.id.tvMaSp);
            tvTenSp = itemView.findViewById(R.id.tvTenSp);
            tvGiaSp = itemView.findViewById(R.id.tvGiaSp);
            tvSoLuongSp = itemView.findViewById(R.id.tvSoLuongSp);
            tvMaHang = itemView.findViewById(R.id.tvMaHang);
            imgAnhSp = itemView.findViewById(R.id.imgAnhSp);
            tvtrangthaiSp = itemView.findViewById(R.id.tvtrangthaiSp);
            btnupdatesp = itemView.findViewById(R.id.btnupdatesp);
            imgDeleteSach = itemView.findViewById(R.id.imgDeleteSach);
        }
    }

    private void themGioHang(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_giohang, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edSoLuongGio = view.findViewById(R.id.edSoLuongGio);
        EditText edDiaChiGio = view.findViewById(R.id.edDiaChiGio);
        Button btnCancelGio = view.findViewById(R.id.btnCancelGio);

        btnCancelGio.setOnClickListener(v -> dialog.dismiss());

        Button btnSaveGio = view.findViewById(R.id.btnSaveGio);
        btnSaveGio.setOnClickListener(v -> {
            int soLuong = Integer.parseInt(edSoLuongGio.getText().toString().trim());
            String diaChi = edDiaChiGio.getText().toString().trim();
            if(soLuong > sanPham.getSoLuongSp()){
                Toast.makeText(context, "Số lượng sản phẩm không đủ !", Toast.LENGTH_SHORT).show();
            }else{
                boolean success = gioHangDao.insertGioHang(new GioHang(soLuong, diaChi, sanPham.getMasp(), maKh));
                if (success) {
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Thêm vào giỏ hàng không thành công", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void updateSanPham(SanPham sanPham, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_sanpham, null);
        builder.setView(view);
        Dialog updateDialog = builder.create();
        updateDialog.show();

        EditText edAnhSp = view.findViewById(R.id.edAnhSp);
        EditText edMaSp = view.findViewById(R.id.edMaSp);
        EditText edTenSp = view.findViewById(R.id.edTenSp);
        EditText edGiaSp = view.findViewById(R.id.edGiaSp);
        EditText edSoLuongSp = view.findViewById(R.id.edSoLuongSp);
        EditText edMotaSp = view.findViewById(R.id.edMotaSp);
        EditText cbTrangThaiSp = view.findViewById(R.id.edtrangthaiSp);
        EditText edMaHangSp = view.findViewById(R.id.edMaHangSp);
        Button btnSaveLS = view.findViewById(R.id.btnSaveLS);
        Button btnCancelSp = view.findViewById(R.id.btnCancelsp);

        // Set values from the current SanPham to the dialog
        edAnhSp.setText(sanPham.getAnhSp());
        edMaSp.setText(String.valueOf(sanPham.getMasp()));
        edTenSp.setText(sanPham.getTenSp());
        edGiaSp.setText(String.valueOf(sanPham.getGiaSp()));
        edSoLuongSp.setText(String.valueOf(sanPham.getSoLuongSp()));
        edMotaSp.setText(sanPham.getMota());
        cbTrangThaiSp.setText(sanPham.getTtSp());
        edMaHangSp.setText(String.valueOf(sanPham.getMaHang()));

        btnCancelSp.setOnClickListener(v -> updateDialog.dismiss());

        btnSaveLS.setOnClickListener(v -> {
            // Retrieve values from dialog
            String trangThaiSp = cbTrangThaiSp.getText().toString().trim();
            String anhSp = edAnhSp.getText().toString().trim();
            String maSp = edMaSp.getText().toString().trim();
            String tenSp = edTenSp.getText().toString().trim();
            String giaSp = edGiaSp.getText().toString().trim();
            String soLuongSp = edSoLuongSp.getText().toString().trim();
            String moTaSp = edMotaSp.getText().toString().trim();
            String maHangSp = edMaHangSp.getText().toString().trim();

            // Validate input
            if (TextUtils.isEmpty(anhSp) || TextUtils.isEmpty(maSp) || TextUtils.isEmpty(tenSp)
                    || TextUtils.isEmpty(giaSp) || TextUtils.isEmpty(maHangSp)
                    || TextUtils.isEmpty(soLuongSp) || TextUtils.isEmpty(moTaSp)) {
                Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                // Update the values in the original list item
                SanPham updatedSp = new SanPham();
                updatedSp.setAnhSp(anhSp);
                updatedSp.setMasp(Integer.parseInt(maSp));
                updatedSp.setTenSp(tenSp);
                updatedSp.setSoLuongSp(Integer.parseInt(soLuongSp));
                updatedSp.setGiaSp(Integer.parseInt(giaSp));
                updatedSp.setTtSp(trangThaiSp);
                updatedSp.setMaHang(Integer.parseInt(maHangSp));
                updatedSp.setMota(moTaSp);

                productList.set(position, updatedSp);

                // Notify the adapter of the data change for this specific item
                notifyItemChanged(position);

                // Dismiss the dialog and show a toast
                updateDialog.dismiss();
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.had_store.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.had_store.DAO.DonHangDao;
import com.example.had_store.DAO.GioHangDao;
import com.example.had_store.DAO.SanPhamDao;
import com.example.had_store.Model.DonHang;
import com.example.had_store.Model.GioHang;
import com.example.had_store.Model.SanPham;
import com.example.had_store.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class GioHangAdapter extends BaseAdapter {

    private Context context;
    private List<GioHang> gioHangList;
    private GioHangDao gioHangDao;
    private DonHangDao donHangDao;
    private SanPhamDao sanPhamDao;
    private String maKh; // Add this line
    private String userType;


    public GioHangAdapter(Context context, List<GioHang> gioHangList, String userType, String maKh) {
        this.context = context;
        this.gioHangList = gioHangList;
        this.gioHangDao = new GioHangDao(context);
        this.sanPhamDao = new SanPhamDao(context);
        this.donHangDao = new DonHangDao(context);

        this.userType = userType;  // Corrected
        this.maKh = maKh;  // Corrected
    }

    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_giohang, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMaGio = view.findViewById(R.id.tvMaGio);
            viewHolder.tvSoLuong = view.findViewById(R.id.tvSoLuong);
            viewHolder.tvDiaChiGio = view.findViewById(R.id.tvDiaChiGio);
            viewHolder.tvTenSp = view.findViewById(R.id.tvTenSp);
            viewHolder.tvThanhTien = view.findViewById(R.id.tvThanhTien);
            viewHolder.imgAnhSp = view.findViewById(R.id.imgAnhSp);
            viewHolder.imgDeleteGio = view.findViewById(R.id.imgDeleteGio);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvMaGio.setVisibility(View.GONE);


        GioHang gioHang = gioHangList.get(position);

        // Get the corresponding SanPham based on maSp
        SanPham sanPham = sanPhamDao.getSanPhamByMaSp(String.valueOf(gioHang.getMaSp()));

        // Set data to the views
        viewHolder.tvMaGio.setText("Mã giỏ : " + gioHang.getMaGio());
        viewHolder.tvSoLuong.setText("Số lượng sản phẩm: " + gioHang.getSoLuong());
        viewHolder.tvDiaChiGio.setText("Địa chỉ giao : " + gioHang.getDiaChiGio());

        // Load the image using Picasso
        if (sanPham != null) {
            Picasso.get().load(sanPham.getAnhSp()).into(viewHolder.imgAnhSp);
            viewHolder.tvTenSp.setText("Tên sản phẩm : " + sanPham.getTenSp());
            viewHolder.tvThanhTien.setText("Thành tiền : " + sanPham.getGiaSp() * gioHang.getSoLuong());

        } else {
            viewHolder.tvTenSp.setText("Tên sản phẩm : Không có thông tin");

        }

        // ... (other code)

        viewHolder.imgDeleteGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CẢNH BÁO");
                builder.setMessage("BẠN CÓ MUỐN XOÁ K");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            // Open the database connection
                            gioHangDao.open();

                            // Handle delete operation
                            if (gioHangDao.delete(gioHang.getMaGio())) {
                                // Successful delete
                                gioHangList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // Delete failed
                                Toast.makeText(context, "Xoá không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Handle any exceptions that might occur during delete
                            Toast.makeText(context, "Lỗi khi xoá: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } finally {
                            // Close the database connection in the finally block
                            gioHangDao.close();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing if the user chooses not to delete
                    }
                });
                builder.show();
            }
        });
        Button btnDatHang = view.findViewById(R.id.btnDat);

        // Set click listener for Đặt Hàng button
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current GioHang item
                GioHang gioHang = gioHangList.get(position);
                if (!donHangDao.isMaGioExists(String.valueOf(gioHang.getMaGio()))) {
                    // Create a new DonHang object
                    DonHang donHang = new DonHang();

                    // Set relevant information for DonHang
                    donHang.setNgayLap(new Date()); // Set the current date/time, you can modify as needed
                    donHang.setTrangThaiDon("Đang Đóng Gói");
                    donHang.setMaGio(gioHang.getMaGio());
                    donHang.setMaKh(gioHang.getMaKh()); // Set the maKh from the current GioHang

                    SanPham sanPham = sanPhamDao.getSanPhamByMaSp(String.valueOf(gioHang.getMaSp()));
                    if (sanPham != null) {
                        int newQuantity = sanPham.getSoLuongSp() - gioHang.getSoLuong();
                        if (newQuantity < 0) {
                            Toast.makeText(context, "Số lượng sản phẩm không đủ", Toast.LENGTH_SHORT).show();
                        } else {
                            sanPhamDao.updateProductQuantity(sanPham.getMasp(), newQuantity);
                            long result = donHangDao.insert(donHang);

                            if (result != -1) {
                                // Successfully inserted, you can handle any additional logic here
                                Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // Insert failed, handle accordingly
                                Toast.makeText(context, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    // DonHang with the same maGio already exists, notify the user
                    Toast.makeText(context, "Đơn hàng đã tồn tại cho giỏ hàng này", Toast.LENGTH_SHORT).show();
                }
            }
        });


// ... (other code)


        return view;
    }


    // ViewHolder pattern for better performance
    static class ViewHolder {
        TextView tvMaGio;
        TextView tvSoLuong;
        TextView tvDiaChiGio;
        TextView tvTenSp, tvThanhTien;
        ImageView imgAnhSp;
        Button imgDeleteGio;
    }
}

package com.example.had_store.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.had_store.Adapter.SanPhamAdapter;
import com.example.had_store.DAO.SanPhamDao;
import com.example.had_store.Model.SanPham;
import com.example.had_store.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment_SanPham extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<SanPham> productList = new ArrayList<>();
    private SanPhamAdapter productAdapter;
    private SanPhamDao sanPhamDao;
    private String userType; // Declare userType variable
    private String maKh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__san_pham, container, false);

        recyclerView = view.findViewById(R.id.recviewsanpham);
        fab = view.findViewById(R.id.fabSanPham);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sanPhamDao = new SanPhamDao(getContext());

        // Retrieve the user's ID (maKh) from SharedPreferences
        maKh = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                .getString("maKh", "");

        Bundle args = getArguments();
        if (args != null) {
            userType = args.getString("USER_TYPE");
            productList.addAll(sanPhamDao.getAll());
            productAdapter = new SanPhamAdapter(getContext(), productList, userType, maKh);
            recyclerView.setAdapter(productAdapter);
            // Check USER_TYPE and set visibility accordingly
            if ("CUSTOMER".equals(userType)) {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogToAddProduct();
                    }
                });
            }
        } else {
            Log.d("Fragment_SanPham", "Arguments are null");
        }

        return view;
    }

    // Hiển thị dialog thêm sản phẩm mới
    public void showDialogToAddProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sanpham, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edMaSp = view.findViewById(R.id.edMaSp);
        EditText edTenSp = view.findViewById(R.id.edTenSp);
        EditText edGiaSp = view.findViewById(R.id.edGiaSp);
        EditText edSoLuongSp = view.findViewById(R.id.edSoLuongSp);
        EditText edMaHangSp = view.findViewById(R.id.edMaHangSp);
        EditText edTrangThai = view.findViewById(R.id.edtrangthaiSp);
        EditText edAnhSp = view.findViewById(R.id.edAnhSp);
        Button btnSave = view.findViewById(R.id.btnSaveLS);

        Button btnCancel = view.findViewById(R.id.btnCancelsp);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các EditText trong dialog
                try {
                    String tensp = edTenSp.getText().toString().trim();
                    String giasp = edGiaSp.getText().toString().trim();
                    String soluongsp = edSoLuongSp.getText().toString().trim();
                    String mahangsp = edMaHangSp.getText().toString().trim();
                    String tt = edTrangThai.getText().toString().trim();
                    String anhsp = edAnhSp.getText().toString().trim();

                    if (tensp.isEmpty() || giasp.isEmpty() || soluongsp.isEmpty() || mahangsp.isEmpty() || anhsp.isEmpty()) {
                        Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tạo sản phẩm mới và thêm vào cơ sở dữ liệu
                        SanPham newProduct = new SanPham(Integer.parseInt(mahangsp), Integer.parseInt(giasp), Integer.parseInt(soluongsp), tensp, anhsp, tt, "description");
                        if (sanPhamDao.insert(newProduct, maKh)) {
                            // Refresh danh sách sản phẩm và thông báo thành công
                            productList.clear();
                            productList.addAll(sanPhamDao.getAll());
                            productAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails
                    Toast.makeText(requireContext(), "Giá trị không hợp lệ", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Handle other exceptions
                    Toast.makeText(requireContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}

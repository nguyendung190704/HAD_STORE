package com.example.had_store;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.had_store.Fragment.Fragment_DoiMatKhau;
import com.example.had_store.Fragment.Fragment_DonHang;
import com.example.had_store.Fragment.Fragment_GioHang;
import com.example.had_store.Fragment.Fragment_HangSanPham;
import com.example.had_store.Fragment.Fragment_KhachHang;
import com.example.had_store.Fragment.Fragment_NhanVien;
import com.example.had_store.Fragment.Fragment_SanPham;
import com.example.had_store.Fragment.Fragment_TrangChu;
import com.google.android.material.navigation.NavigationView;

public class ManHinh_TrangChu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav;
    View mHeaderView;
    private String maKh;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_trang_chu);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav);
        mHeaderView = nav.getHeaderView(0);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        userType = intent.getStringExtra("USER_TYPE");
        maKh = intent.getStringExtra("MA_KH");

        setTitle("Trang chủ");
        Fragment_TrangChu trangChu = new Fragment_TrangChu();
        replaceFrg(trangChu);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.quanlyhangsanpham) {
                    if (userType != null && !"CUSTOMER".equals(userType)) {
                        setTitle("Quản lý hãng sản phẩm ");
                        Fragment_HangSanPham hsp = new Fragment_HangSanPham();
                        replaceFrg(hsp);
                    } else {
                        Toast.makeText(ManHinh_TrangChu.this, "Bạn không có quyền truy cập mục này", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.trangchu) {
                    setTitle("Trang chủ ");
                    Fragment_TrangChu sp = new Fragment_TrangChu();
                    replaceFrg(sp);
                } else if (item.getItemId() == R.id.quanlynhanvien) {
                    if (userType != null && !"CUSTOMER".equals(userType)) {
                        setTitle("Quản lý nhân viên");
                        Fragment_NhanVien hsp = new Fragment_NhanVien();
                        replaceFrg(hsp);
                    } else {
                        Toast.makeText(ManHinh_TrangChu.this, "Bạn không có quyền truy cập mục này", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.quanlydonhang) {
                    setTitle("Quản lý đơn hàng");
                    Fragment_DonHang hsp = new Fragment_DonHang();
                    replaceFrg(hsp);
                } else if (item.getItemId() == R.id.quanlysanpham) {
                    setTitle("Quản lý sản phẩm ");
                    Fragment_SanPham sanPhamFragment = new Fragment_SanPham();
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_TYPE", userType);
                    sanPhamFragment.setArguments(bundle);
                    replaceFrg(sanPhamFragment);
                } else if (item.getItemId() == R.id.quanlykhachhang) {
                    if (userType != null && !"CUSTOMER".equals(userType)) {
                        setTitle("Quản lý khách hàng ");
                        Fragment_KhachHang hsp = new Fragment_KhachHang();
                        replaceFrg(hsp);
                    } else {
                        Toast.makeText(ManHinh_TrangChu.this, "Bạn không có quyền truy cập mục này", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.quanlygiohang) {
                    setTitle("Quản lý giỏ hàng ");
                    Fragment_GioHang gioHangFragment = new Fragment_GioHang();
                    Bundle bundle = new Bundle();
                    bundle.putString("MA_KH", maKh);
                    gioHangFragment.setArguments(bundle);
                    replaceFrg(gioHangFragment);
                } else if (item.getItemId() == R.id.doimatkhau) {
                    setTitle("Đổi mật khẩu");
                    Fragment_DoiMatKhau hsp = new Fragment_DoiMatKhau();
                    replaceFrg(hsp);
                } else if (item.getItemId() == R.id.dangxuat) {
                    showLogoutDialog();
                }

                return true;
            }
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManHinh_TrangChu.this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ManHinh_TrangChu.this, ManHinh_Chao.class);
                startActivity(intent);
                finish();
                Toast.makeText(ManHinh_TrangChu.this, "Log Out successful", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing if "No" is clicked
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void replaceFrg(Fragment frg) {
        Bundle bundle = new Bundle();
        bundle.putString("MA_KH", maKh);
        bundle.putString("USER_TYPE", userType);
        frg.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmNav, frg).commit();
    }
}

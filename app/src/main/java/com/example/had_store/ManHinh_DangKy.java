package com.example.had_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.had_store.DAO.KhachHangDao;
import com.example.had_store.DAO.NhanVienDao;
import com.example.had_store.Model.KhachHang;
import com.example.had_store.Model.NhanVien;

public class ManHinh_DangKy extends AppCompatActivity {

    private EditText edMaNv, edTenNv, edMatKhauNv, edSoNv, edEmailNv, edAnhNv, edDiaChi;
    private Button btnSave, btnCancel;
    private KhachHangDao nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ky);

        // Initialize views
        edMaNv = findViewById(R.id.edMaNv);
        edTenNv = findViewById(R.id.edTenNv);
        edMatKhauNv = findViewById(R.id.edMatKhauNv);
        edSoNv = findViewById(R.id.edSoNv);
        edEmailNv = findViewById(R.id.edEmailNv);
        edAnhNv = findViewById(R.id.edAnhNv);
        edDiaChi = findViewById(R.id.edDiaChiNv);
        btnSave = findViewById(R.id.btnSaveLS);
        btnCancel = findViewById(R.id.btnCancelLS);

        // Initialize DAO
        nhanVienDao = new KhachHangDao(this);

        // Set click listener for the Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        // Set click listener for the Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinh_DangKy.this, ManHinh_DangNhap.class);
                startActivity(intent);
                finish(); // Close the registration screen
            }
        });
    }

    private void register() {
        String maNv = edMaNv.getText().toString().trim();
        String tenNv = edTenNv.getText().toString().trim();
        String matKhauNv = edMatKhauNv.getText().toString().trim();
        int soNv = Integer.parseInt(edSoNv.getText().toString().trim());
        String emailNv = edEmailNv.getText().toString().trim();
        String anhNv = edAnhNv.getText().toString().trim();
        String diachi = edDiaChi.getText().toString().trim();

        // Validate input
        if (maNv.isEmpty() || tenNv.isEmpty() || matKhauNv.isEmpty() || emailNv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the employee code already exists
        if (nhanVienDao.isMaNvExists(maNv)) {
            Toast.makeText(this, "Mã nhân viên đã tồn tại, vui lòng chọn mã khác", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new NhanVien object
        KhachHang newNhanVien = new KhachHang(maNv, tenNv, matKhauNv, soNv, emailNv,diachi, anhNv);

        // Insert the new NhanVien into the database
        long result = nhanVienDao.insert(newNhanVien);
        if (result > 0) {
            // Registration successful
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ManHinh_DangKy.this, ManHinh_DangNhap.class);
            startActivity(intent);
            finish(); // Close the registration screen
        } else {
            // Registration failed
            Toast.makeText(this, "Đăng ký thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }
}

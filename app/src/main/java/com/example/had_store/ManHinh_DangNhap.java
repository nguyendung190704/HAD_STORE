
package com.example.had_store;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.had_store.DAO.KhachHangDao;
import com.example.had_store.DAO.NhanVienDao;

public class ManHinh_DangNhap extends AppCompatActivity {

    private EditText edMaNv, edMatKhauNv;
    private CheckBox chkRememberPass;
    private Button btnLogin, btnCancel;
    private NhanVienDao nhanVienDao;
    private KhachHangDao khachHangDao;

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_MA_NV = "maNv";
    private static final String KEY_MAT_KHAU = "matKhau";
    private static final String KEY_REMEMBER_PASS = "rememberPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        // Initialize views
        edMaNv = findViewById(R.id.edMaNvDn);
        edMatKhauNv = findViewById(R.id.edMatKhauNvDn);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnDangKyDn);

        // Initialize DAO
        nhanVienDao = new NhanVienDao(this);
        khachHangDao = new KhachHangDao(this);

        // Restore saved login information
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean rememberPass = prefs.getBoolean(KEY_REMEMBER_PASS, false);
        if (rememberPass) {
            String savedMaNv = prefs.getString(KEY_MA_NV, "");
            String savedMatKhau = prefs.getString(KEY_MAT_KHAU, "");

            edMaNv.setText(savedMaNv);
            edMatKhauNv.setText(savedMatKhau);
            chkRememberPass.setChecked(true);
        }

        // Set click listener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Set click listener for the cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinh_DangNhap.this, ManHinh_DangKy.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for rememberPass checkbox
        chkRememberPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginInfo();
            }
        });

        Spinner spDangNhap = findViewById(R.id.spDangNhap);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.user_types,  // Define an array of user types in strings.xml
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spDangNhap.setAdapter(adapter);

        // Set a listener for item selection
        spDangNhap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item (position)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if nothing is selected
            }
        });
    }

    private void login() {
        String maNv = edMaNv.getText().toString().trim();
        String matKhau = edMatKhauNv.getText().toString().trim();

        if (maNv.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected item position from the Spinner
        Spinner spDangNhap = findViewById(R.id.spDangNhap);
        int selectedPosition = spDangNhap.getSelectedItemPosition();

        if (selectedPosition == 0) { // Check if "Người dùng" is selected
            int result = khachHangDao.checkLogin(maNv, matKhau);


            if (result == 1) {

                Toast.makeText(this, "Đăng nhập Người dùng thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManHinh_DangNhap.this, ManHinh_TrangChu.class);
                intent.putExtra("USER_TYPE", "CUSTOMER"); // Truyền quyền truy cập người dùng
                intent.putExtra("MA_KH", maNv); // Truyền mã khách hàng
                SharedPreferences preferences = ManHinh_DangNhap.this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("maKh", maNv);
                editor.apply();
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai mã người dùng hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        } else if (selectedPosition == 1) { // Check if "Nhân viên" is selected
            int result = nhanVienDao.checkLogin(maNv, matKhau);

            if (result == 1) {
                Toast.makeText(this, "Đăng nhập Nhân viên thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManHinh_DangNhap.this, ManHinh_TrangChu.class);
                intent.putExtra("USER_TYPE", "ADMIN"); // Truyền quyền truy cập admin
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai mã nhân viên hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle login for other user types if needed
            // For now, show a toast message
            Toast.makeText(this, "Đăng nhập với loại người dùng khác", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLoginInfo() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (chkRememberPass.isChecked()) {
            String maNv = edMaNv.getText().toString().trim();
            String matKhau = edMatKhauNv.getText().toString().trim();
            editor.putString(KEY_MA_NV, maNv);
            editor.putString(KEY_MAT_KHAU, matKhau);
            editor.putBoolean(KEY_REMEMBER_PASS, true);
        } else {
            editor.remove(KEY_MA_NV);
            editor.remove(KEY_MAT_KHAU);
            editor.remove(KEY_REMEMBER_PASS);
        }

        editor.apply();
    }


}

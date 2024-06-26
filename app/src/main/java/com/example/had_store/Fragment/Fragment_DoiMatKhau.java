package com.example.had_store.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.had_store.DAO.KhachHangDao;
import com.example.had_store.R;
import com.google.android.material.textfield.TextInputEditText;


public class Fragment_DoiMatKhau extends Fragment {
    private TextInputEditText edPassOld, edPassChange, edRePassChange;
    private Button btnSaveUserChange, btnCancelUserChange;
    private KhachHangDao khachHangDao;

    public Fragment_DoiMatKhau() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__doi_mat_khau, container, false);

        // Initialize views
        edPassOld = view.findViewById(R.id.edPassOld);
        edPassChange = view.findViewById(R.id.edPassChange);
        edRePassChange = view.findViewById(R.id.edRePassChange);
        btnSaveUserChange = view.findViewById(R.id.btnSaveUserChange);
        btnCancelUserChange = view.findViewById(R.id.btnCancelUserChange);

        // Initialize DAO
        khachHangDao = new KhachHangDao(requireContext());

        // Set onClickListener for Save button
        btnSaveUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        // Set onClickListener for Cancel button
        btnCancelUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel action
                // For example, navigate back to the previous screen or perform any other action
            }
        });

        return view;
    }

    private void changePassword() {
        String oldPassword = edPassOld.getText().toString().trim();
        String newPassword = edPassChange.getText().toString().trim();
        String reEnteredPassword = edRePassChange.getText().toString().trim();

        SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE);
        String maKh = preferences.getString("maKh", "");

        // Validate input
        if (oldPassword.isEmpty() || newPassword.isEmpty() || reEnteredPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(reEnteredPassword)) {
            Toast.makeText(requireContext(), "mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUserId = maKh;
        int result = khachHangDao.changePassword(currentUserId, oldPassword, newPassword);

        if (result > 0) {
            Toast.makeText(requireContext(), "Đã thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
        } else if (result == -1) {
            Toast.makeText(requireContext(), "Mật mã cũ không chính xác", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Không thể thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }

    // Replace this with the actual method to get the current user ID

}
package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDao {
    private SQLiteDatabase db;

    public KhachHangDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("maKh", khachHang.getMaKh());
        values.put("tenKh", khachHang.getTenKh());
        values.put("matKhauKh", khachHang.getMatKhauKh());
        values.put("soKh", khachHang.getSoKh());
        values.put("emailKh", khachHang.getEmailKh());
        values.put("diaChiKh", khachHang.getDiaChiKh());
        values.put("anhKh", khachHang.getAnhKh());

        return db.insert("KhachHang", null, values);
    }
    public int update(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put("tenKh", khachHang.getTenKh());
        values.put("matKhauKh", khachHang.getMatKhauKh());
        values.put("soKh", khachHang.getSoKh());
        values.put("emailKh", khachHang.getEmailKh());
        values.put("diaChiKh", khachHang.getDiaChiKh());
        values.put("anhKh", khachHang.getAnhKh());

        return db.update("KhachHang", values, "maKh=?", new String[]{khachHang.getMaKh()});
    }


    public int delete(String id) {
        return db.delete("KhachHang", "maKh=?", new String[]{id});
    }
    public List<KhachHang> getData(String sql, String... selectionArgs) {
        List<KhachHang> list = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(sql, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    KhachHang khachHang = new KhachHang();
                    int maKhIndex = cursor.getColumnIndex("maKh");
                    if (maKhIndex != -1) {
                        khachHang.setMaKh(cursor.getString(maKhIndex));
                    }

                    int tenKhIndex = cursor.getColumnIndex("tenKh");
                    if (tenKhIndex != -1) {
                        khachHang.setTenKh(cursor.getString(tenKhIndex));
                    }

                    int matKhauKhIndex = cursor.getColumnIndex("matKhauKh");
                    if (matKhauKhIndex != -1) {
                        khachHang.setMatKhauKh(cursor.getString(matKhauKhIndex));
                    }

                    int soKhIndex = cursor.getColumnIndex("soKh");
                    if (soKhIndex != -1 && !cursor.isNull(soKhIndex)) {
                        khachHang.setSoKh(cursor.getInt(soKhIndex));
                    }
                    int emailKhIndex = cursor.getColumnIndex("emailKh");
                    if (emailKhIndex != -1) {
                        khachHang.setEmailKh(cursor.getString(emailKhIndex));
                    }

                    int diaChiKhIndex = cursor.getColumnIndex("diaChiKh");
                    if (diaChiKhIndex != -1) {
                        khachHang.setDiaChiKh(cursor.getString(diaChiKhIndex));
                    }

                    int anhKhIndex = cursor.getColumnIndex("anhKh");
                    if (anhKhIndex != -1) {
                        khachHang.setAnhKh(cursor.getString(anhKhIndex));
                    }

                    list.add(khachHang);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public List<KhachHang> getAllEmployees() {
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }
    public int checkLogin(String maKh, String matKhau) {

        // Columns to retrieve
        String[] columns = {"maKh"};

        // Selection criteria
        String selection = "maKh = ? AND matKhauKh = ?";
        String[] selectionArgs = {maKh, matKhau};

        // Query the database
        Cursor cursor = db.query("KhachHang", columns, selection, selectionArgs, null, null, null);

        int result = cursor.getCount();

        // Close the cursor and database
        cursor.close();


        return result;
    }
    public boolean isMaNvExists(String maKh) {
        String query = "SELECT * FROM KhachHang WHERE maKh = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maKh});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public int changePassword(String maKh, String oldPassword, String newPassword) {
        // Check if the old password is correct
        if (checkLogin(maKh, oldPassword) > 0) {
            // Old password is correct, update the password
            ContentValues values = new ContentValues();
            values.put("matKhauKh", newPassword);

            return db.update("KhachHang", values, "maKh=?", new String[]{maKh});
        } else {
            // Old password is incorrect
            return -1;
        }
    }

}

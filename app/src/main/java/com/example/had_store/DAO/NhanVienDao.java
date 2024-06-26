package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDao {
    private SQLiteDatabase db;

    public NhanVienDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("maNv", nhanVien.getMaNv());
        values.put("tenNv", nhanVien.getTenNv());
        values.put("matKhauNv", nhanVien.getMatKhauNv());
        values.put("soNv", nhanVien.getSoNv());
        values.put("emailNv", nhanVien.getEmailNv());
        values.put("anhNv", nhanVien.getAnhNv());

        return db.insert("NhanVien", null, values);
    }

    public int update(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("tenNv", nhanVien.getTenNv());
        values.put("matKhauNv", nhanVien.getMatKhauNv());
        values.put("soNv", nhanVien.getSoNv());
        values.put("emailNv", nhanVien.getEmailNv());
        values.put("anhNv", nhanVien.getAnhNv());

        return db.update("NhanVien", values, "maNv=?", new String[]{nhanVien.getMaNv()});
    }


    public int delete(String id) {
        return db.delete("NhanVien", "maNv=?", new String[]{id});
    }


    public List<NhanVien> getData(String sql, String... selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNv(cursor.getString(cursor.getColumnIndex("maNv")));
            nhanVien.setTenNv(cursor.getString(cursor.getColumnIndex("tenNv")));
            nhanVien.setMatKhauNv(cursor.getString(cursor.getColumnIndex("matKhauNv")));
            nhanVien.setSoNv(cursor.getInt(cursor.getColumnIndex("soNv")));
            nhanVien.setEmailNv(cursor.getString(cursor.getColumnIndex("emailNv")));
            nhanVien.setAnhNv(cursor.getString(cursor.getColumnIndex("anhNv")));

            list.add(nhanVien);
        }

        cursor.close();
        return list;
    }
    public List<NhanVien> getAllEmployees() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }
    public int checkLogin(String maNv, String matKhau) {
        String sql = "SELECT * FROM NhanVien WHERE maNv=? AND matKhauNv=?";
        List<NhanVien> list = getData(sql, maNv, matKhau);
        if (list.size() == 0) return -1;
        return 1;
    }
    public boolean isMaNvExists(String maNv) {
        String query = "SELECT * FROM NhanVien WHERE maNv = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maNv});

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

}

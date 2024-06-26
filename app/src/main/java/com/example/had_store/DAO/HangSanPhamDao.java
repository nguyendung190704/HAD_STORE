package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.HangSanPham;

import java.util.ArrayList;

public class HangSanPhamDao {
    private final DbHelper dbHelper;

    public HangSanPhamDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<HangSanPham> selectAll() {
        ArrayList<HangSanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM HangSanPham", null);
            if (cursor.moveToFirst()) {
                do {
                    HangSanPham hsp = new HangSanPham();
                    hsp.setMaHang(cursor.getInt(cursor.getColumnIndex("maHang")));
                    hsp.setTenHang(cursor.getString(cursor.getColumnIndex("tenHang")));
                    hsp.setDiaChiHang(cursor.getString(cursor.getColumnIndex("diaChiHang")));
                    hsp.setAnhHang(cursor.getString(cursor.getColumnIndex("anhHang")));
                    list.add(hsp);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean insert(HangSanPham hsp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenHang", hsp.getTenHang());
        values.put("diaChiHang", hsp.getDiaChiHang());
        values.put("anhHang", hsp.getAnhHang());
        long row = db.insert("HangSanPham", null, values);
        return row != -1;
    }

    public boolean update(HangSanPham hsp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenHang", hsp.getTenHang());
        values.put("diaChiHang", hsp.getDiaChiHang());
        values.put("anhHang", hsp.getAnhHang());
        long row = db.update("HangSanPham", values, "maHang=?", new String[]{String.valueOf(hsp.getMaHang())});
        return row > 0;
    }

    public boolean delete(int maHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("HangSanPham", "maHang=?", new String[]{String.valueOf(maHang)});
        return row > 0;
    }
    public String getTenHangById(int maHang) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tenHang = null;

        String[] columns = {"tenHang"};
        String selection = "maHang=?";
        String[] selectionArgs = {String.valueOf(maHang)};

        Cursor cursor = db.query("HangSanPham", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            tenHang = cursor.getString(cursor.getColumnIndex("tenHang"));
            cursor.close();
        }

        return tenHang;
    }
}

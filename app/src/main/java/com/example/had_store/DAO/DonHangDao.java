package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.DonHang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHangDao {
    private static final String TABLE_NAME = "DonHang";
    private SQLiteDatabase database;


    private DbHelper dbHelper;
    private SimpleDateFormat dateFormat;

    public DonHangDao(Context context) {
        dbHelper = new DbHelper(context);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        database = dbHelper.getWritableDatabase(); // or getReadableDatabase() depending on your use case

    }

    public List<DonHang> getAllDonHang() {
        List<DonHang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        try {
            int maDonIndex = cursor.getColumnIndex("maDon");
            int ngayLapIndex = cursor.getColumnIndex("ngayLap");
            int trangThaiDonIndex = cursor.getColumnIndex("trangThaiDon");
            int maGioIndex = cursor.getColumnIndex("maGio");
            int maKhIndex = cursor.getColumnIndex("maKh");

            while (cursor.moveToNext()) {
                DonHang donHang = new DonHang();
                donHang.setMaDon(cursor.getInt(maDonIndex));
                donHang.setNgayLap(parseDate(cursor.getString(ngayLapIndex)));
                donHang.setTrangThaiDon(cursor.getString(trangThaiDonIndex));
                donHang.setMaGio(cursor.getInt(maGioIndex));
                donHang.setMaKh(cursor.getString(maKhIndex));

                donHangList.add(donHang);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return donHangList;
    }


    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public long insert(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        values.put("ngayLap", dateFormat.format(new Date(currentTimeMillis)));
        values.put("trangThaiDon", "Đang Đóng Gói");
        values.put("maGio",donHang.getMaGio());

        values.put("maKh", donHang.getMaKh());

        long result = db.insert(TABLE_NAME, null, values);

        db.close();
        return result;
    }

    public int update(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (donHang.getNgayLap() != null) {
            values.put("ngayLap", dateFormat.format(donHang.getNgayLap()));
        }
        values.put("trangThaiDon", donHang.getTrangThaiDon());
        values.put("maGio",donHang.getMaGio());
        values.put("maKh", donHang.getMaKh());

        int result = db.update(TABLE_NAME, values, "maDon=?", new String[]{String.valueOf(donHang.getMaDon())});

        db.close();
        return result;
    }

    public int delete(int maDon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "maDon=?", new String[]{String.valueOf(maDon)});
        db.close();
        return result;
    }
    public List<DonHang> getPendingOrders() {
        List<DonHang> pendingOrders = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"maDon", "ngayLap", "trangThaiDon","maGio", "maKh"};
        String selection = "trangThaiDon = ?";
        String[] selectionArgs = {"Đang Đóng Gói"};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setNgayLap(parseDate(cursor.getString(cursor.getColumnIndex("ngayLap"))));
                donHang.setMaDon(cursor.getInt(cursor.getColumnIndex("maDon")));
                donHang.setTrangThaiDon(cursor.getString(cursor.getColumnIndex("trangThaiDon")));
                donHang.setMaGio(cursor.getInt(cursor.getColumnIndex("maGio")));
                donHang.setMaKh(cursor.getString(cursor.getColumnIndex("maKh")));

                pendingOrders.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pendingOrders;
    }
    public boolean isMaGioExists(String maGio) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + DonHangDao.TABLE_NAME + " WHERE maGio = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maGio});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }


    public int calculateTotalCost(int maGio) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalCost = 0;

        String query = "SELECT SUM(sp.giaSp * gh.soLuong) AS totalCost " +
                "FROM SanPham sp INNER JOIN GioHang gh ON sp.maSp = gh.maSp " +
                "WHERE gh.maGio = ?";
        String[] selectionArgs = {String.valueOf(maGio)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            totalCost = cursor.getInt(cursor.getColumnIndex("totalCost"));
        }

        cursor.close();
        db.close();

        return totalCost;
    }

    public List<DonHang> getDonHangsByMaKh(String maKh) {
        List<DonHang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            if (maKh == null) {
                // Query to get all items in the cart
                cursor = db.query("DonHang", null, null, null, null, null, null);
            } else {
                // Query to get items in the cart for a specific user
                String[] selectionArgs = {maKh};
                cursor = db.query("DonHang", null, "maKh = ?", selectionArgs, null, null, null);
            }

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    DonHang donHang = new DonHang();
                    donHang.setNgayLap(parseDate(cursor.getString(cursor.getColumnIndex("ngayLap"))));
                    donHang.setMaDon(cursor.getInt(cursor.getColumnIndex("maDon")));
                    donHang.setTrangThaiDon(cursor.getString(cursor.getColumnIndex("trangThaiDon")));
                    donHang.setMaGio(cursor.getInt(cursor.getColumnIndex("maGio")));
                    donHang.setMaKh(cursor.getString(cursor.getColumnIndex("maKh")));
                    donHangList.add(donHang);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Close the cursor before closing the database
            db.close();
        }
        return donHangList;
    }


}

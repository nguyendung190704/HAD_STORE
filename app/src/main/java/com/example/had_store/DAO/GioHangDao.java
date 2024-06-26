package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.GioHang;
import com.example.had_store.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class GioHangDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public GioHangDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
            Log.d("GioHangDao", "Database opened successfully");
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error opening the database: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (database != null && database.isOpen()) {
                database.close();
                Log.d("GioHangDao", "Database closed successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error closing the database: " + e.getMessage());
        } finally {
            dbHelper.close();
        }
    }

    // Insert a new GioHang record
    public boolean insertGioHang(GioHang gioHang) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put("soLuong", gioHang.getSoLuong());
            values.put("diaChiGio", gioHang.getDiaChiGio());
            values.put("maSp", gioHang.getMaSp());
            values.put("maKh", gioHang.getMaKh());

            long result = db.insert("GioHang", null, values);

            // Check if the insertion was successful
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error inserting GioHang: " + e.getMessage());
            return false;
        }
    }

    // Get a list of all GioHang records
    public List<GioHang> getAllGioHangs() {
        Log.d("GioHangDao", "Executing getAllGioHangs");
        List<GioHang> gioHangList = new ArrayList<>();
        try (Cursor cursor = database.query("GioHang", null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GioHang gioHang = new GioHang();
                    gioHang.setMaGio(cursor.getInt(cursor.getColumnIndex("maGio")));
                    gioHang.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                    gioHang.setDiaChiGio(cursor.getString(cursor.getColumnIndex("diaChiGio")));
                    gioHang.setMaSp(cursor.getInt(cursor.getColumnIndex("maSp")));
                    gioHang.setMaKh(cursor.getString(cursor.getColumnIndex("maKh")));

                    gioHangList.add(gioHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error getting all GioHangs: " + e.getMessage());
        }

        return gioHangList;
    }

    // Update a GioHang record
    public int updateGioHang(GioHang gioHang) {
        try {
            ContentValues values = new ContentValues();
            values.put("soLuong", gioHang.getSoLuong());
            values.put("diaChiGio", gioHang.getDiaChiGio());
            values.put("maSp", gioHang.getMaSp());
            values.put("maKh", gioHang.getMaKh());

            return database.update("GioHang", values, "maGio = ?", new String[]{String.valueOf(gioHang.getMaGio())});
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error updating GioHang: " + e.getMessage());
            return -1;
        }
    }

    // Delete a GioHang record
    public boolean delete(int maGio) {
        try {
            int result = database.delete("GioHang", "maGio = ?", new String[]{String.valueOf(maGio)});
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error deleting GioHang: " + e.getMessage());
            return false;
        }
    }

    public List<GioHang> getGioHangsByMaKh(String maKh) {
        List<GioHang> gioHangList = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Ensure the database connection is open
            open();

            if (maKh == null) {
                // Query to get all items in the cart
                cursor = database.query("GioHang", null, null, null, null, null, null);
            } else {
                // Query to get items in the cart for a specific user
                String[] selectionArgs = {maKh};
                cursor = database.query("GioHang", null, "maKh = ?", selectionArgs, null, null, null);
            }

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GioHang gioHang = new GioHang();
                    gioHang.setMaGio(cursor.getInt(cursor.getColumnIndex("maGio")));
                    gioHang.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                    gioHang.setDiaChiGio(cursor.getString(cursor.getColumnIndex("diaChiGio")));
                    gioHang.setMaSp(cursor.getInt(cursor.getColumnIndex("maSp")));
                    gioHang.setMaKh(cursor.getString(cursor.getColumnIndex("maKh")));

                    gioHangList.add(gioHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GioHangDao", "Error getting GioHangs by maKh: " + e.getMessage());
        } finally {
            // Close the cursor in the finally block to ensure it's always closed
            if (cursor != null) {
                cursor.close();
            }

            // Close the database connection
            close();
        }

        return gioHangList;
    }
}

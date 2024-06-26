package com.example.had_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.had_store.Database.DbHelper;
import com.example.had_store.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public SanPhamDao(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(SanPham sanPham,String maKh) {
        ContentValues values = createContentValues(sanPham);

        long row = db.insert("SanPham", null, values);
        return (row != -1);
    }

    public int update(SanPham sanPham) {
        ContentValues values = createContentValues(sanPham);

        return db.update("SanPham", values, "maSp=?", new String[]{String.valueOf(sanPham.getMasp())});
    }
    public List<SanPham> getSaleSanPhamList() {
        String sql = "SELECT * FROM SanPham WHERE trangThaiSp = 'sale'";
        return getData(sql);
    }

    public boolean delete(int id) {
        return db.delete("SanPham", "maSp=?", new String[]{String.valueOf(id)}) > 0;
    }

    public List<SanPham> getData(String sql, String... selectionArgs) {
        List<SanPham> list = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(sql, selectionArgs);

            while (cursor.moveToNext()) {
                SanPham sanPham = extractSanPhamFromCursor(cursor);
                list.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public List<SanPham> getAll() {
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }

    private ContentValues createContentValues(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put("tenSp", sanPham.getTenSp());
        values.put("giaSp", sanPham.getGiaSp());
        values.put("soLuongSp", sanPham.getSoLuongSp());
        values.put("maHang", sanPham.getMaHang());
        values.put("anhSp", sanPham.getAnhSp());
        values.put("trangThaiSp", sanPham.getTtSp());
        values.put("mota", sanPham.getMota());
        return values;
    }

    private SanPham extractSanPhamFromCursor(Cursor cursor) {
        SanPham sanPham = new SanPham();
        sanPham.setMasp(cursor.getInt(cursor.getColumnIndex("maSp")));
        sanPham.setTenSp(cursor.getString(cursor.getColumnIndex("tenSp")));
        sanPham.setGiaSp(cursor.getInt(cursor.getColumnIndex("giaSp")));
        sanPham.setSoLuongSp(cursor.getInt(cursor.getColumnIndex("soLuongSp")));
        sanPham.setMaHang(cursor.getInt(cursor.getColumnIndex("maHang")));
        sanPham.setTtSp(cursor.getString(cursor.getColumnIndex("trangThaiSp")));
        sanPham.setMota(cursor.getString(cursor.getColumnIndex("mota")));
        sanPham.setAnhSp(cursor.getString(cursor.getColumnIndex("anhSp")));
        return sanPham;
    }
    public SanPham getSanPhamByMaSp(String maSp) {
        SanPham sanPham = null;

        Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE maSp=?", new String[]{maSp});

        if (cursor != null && cursor.moveToFirst()) {
            sanPham = new SanPham();
            sanPham.setMasp(cursor.getInt(cursor.getColumnIndex("maSp")));
            sanPham.setTenSp(cursor.getString(cursor.getColumnIndex("tenSp")));
            sanPham.setGiaSp(cursor.getInt(cursor.getColumnIndex("giaSp")));
            sanPham.setMaHang(cursor.getInt(cursor.getColumnIndex("maHang")));
            sanPham.setSoLuongSp(cursor.getInt(cursor.getColumnIndex("soLuongSp")));
            sanPham.setTtSp(cursor.getString(cursor.getColumnIndex("trangThaiSp")));
            sanPham.setAnhSp(cursor.getString(cursor.getColumnIndex("anhSp")));
            cursor.close();
        }

        return sanPham;
    }

    // Close the database connection
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public List<SanPham> getTop5ProductsInCart() {
        List<SanPham> topProducts = new ArrayList<>();

        // Query to get the top 5 products with the highest quantity in the shopping cart
        String query = "SELECT SanPham.* " +
                "FROM SanPham " +
                "JOIN GioHang ON SanPham.maSp = GioHang.maSp " +
                "ORDER BY GioHang.soLuong DESC " +
                "LIMIT 5";

        // Execute the query and populate the list
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            SanPham sanPham = new SanPham();
            sanPham.setMasp(cursor.getInt(cursor.getColumnIndex("maSp")));
            sanPham.setTenSp(cursor.getString(cursor.getColumnIndex("tenSp")));
            sanPham.setGiaSp(cursor.getInt(cursor.getColumnIndex("giaSp")));
            sanPham.setMaHang(cursor.getInt(cursor.getColumnIndex("maHang")));
            sanPham.setSoLuongSp(cursor.getInt(cursor.getColumnIndex("soLuongSp")));
            sanPham.setTtSp(cursor.getString(cursor.getColumnIndex("trangThaiSp")));
            sanPham.setAnhSp(cursor.getString(cursor.getColumnIndex("anhSp")));
            sanPham.setMota(cursor.getString(cursor.getColumnIndex("mota")));
            topProducts.add(sanPham);
        }
        cursor.close();

        return topProducts;
    }
    public int updateProductQuantity(int maSp, int newQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuongSp", newQuantity);
        return db.update("SanPham", values, "maSp=?", new String[]{String.valueOf(maSp)});
    }

}



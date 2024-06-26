package com.example.had_store.Model;

public class HangSanPham {
    private int maHang;
    private String tenHang;
    private String diaChiHang;
    private String anhHang;


    public HangSanPham(String tenHang, String diaChiHang, String anhHang) {
        this.tenHang = tenHang;
        this.diaChiHang = diaChiHang;
        this.anhHang = anhHang;
    }

    @Override
    public String toString() {
        return "HangSanPham{" +
                "maHang=" + maHang +
                ", tenHang='" + tenHang + '\'' +
                ", diaChiHang='" + diaChiHang + '\'' +
                ", anhHang='" + anhHang + '\'' +
                '}';
    }



    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getDiaChiHang() {
        return diaChiHang;
    }

    public void setDiaChiHang(String diaChiHang) {
        this.diaChiHang = diaChiHang;
    }

    public String getAnhHang() {
        return anhHang;
    }

    public void setAnhHang(String anhHang) {
        this.anhHang = anhHang;
    }

    public HangSanPham() {
    }

    public HangSanPham(int maHang, String tenHang, String diaChiHang, String anhHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.diaChiHang = diaChiHang;
        this.anhHang = anhHang;
    }


}

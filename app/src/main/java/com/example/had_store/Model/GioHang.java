package com.example.had_store.Model;

public class GioHang {
    private int maGio;
    private int soLuong;
    private String diaChiGio;
    private int maSp;
    private String maKh;

    public int getMaGio() {
        return maGio;
    }

    public void setMaGio(int maGio) {
        this.maGio = maGio;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDiaChiGio() {
        return diaChiGio;
    }

    public void setDiaChiGio(String diaChiGio) {
        this.diaChiGio = diaChiGio;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public GioHang() {
    }

    public GioHang(int maGio, int soLuong, String diaChiGio, int maSp, String maKh) {
        this.maGio = maGio;
        this.soLuong = soLuong;
        this.diaChiGio = diaChiGio;
        this.maSp = maSp;
        this.maKh = maKh;
    }

    public GioHang(int soLuong, String diaChiGio, int maSp, String maKh) {
        this.soLuong = soLuong;
        this.diaChiGio = diaChiGio;
        this.maSp = maSp;
        this.maKh = maKh;
    }
}

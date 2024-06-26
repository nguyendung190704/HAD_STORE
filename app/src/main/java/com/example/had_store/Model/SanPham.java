package com.example.had_store.Model;

public class SanPham {
   private int masp,maHang,giaSp,soLuongSp;
   private String tenSp,anhSp,ttSp,mota;

    public SanPham() {
    }

    public SanPham(int maHang, int giaSp, int soLuongSp, String tenSp, String anhSp, String ttSp, String mota) {
        this.maHang = maHang;
        this.giaSp = giaSp;
        this.soLuongSp = soLuongSp;
        this.tenSp = tenSp;
        this.anhSp = anhSp;
        this.ttSp = ttSp;
        this.mota = mota;
    }

    public SanPham(int masp, int maHang, int giaSp, int soLuongSp, String tenSp, String anhSp, String ttSp, String mota) {
        this.masp = masp;
        this.maHang = maHang;
        this.giaSp = giaSp;
        this.soLuongSp = soLuongSp;
        this.tenSp = tenSp;
        this.anhSp = anhSp;
        this.ttSp = ttSp;
        this.mota = mota;
    }

    public SanPham(int masp, int maHang, int giaSp, int soLuongSp, String tenSp, String anhSp) {
        this.masp = masp;
        this.maHang = maHang;
        this.giaSp = giaSp;
        this.soLuongSp = soLuongSp;
        this.tenSp = tenSp;
        this.anhSp = anhSp;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }

    public int getSoLuongSp() {
        return soLuongSp;
    }

    public void setSoLuongSp(int soLuongSp) {
        this.soLuongSp = soLuongSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getAnhSp() {
        return anhSp;
    }

    public void setAnhSp(String anhSp) {
        this.anhSp = anhSp;
    }

    public String getTtSp() {
        return ttSp;
    }

    public void setTtSp(String ttSp) {
        this.ttSp = ttSp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}

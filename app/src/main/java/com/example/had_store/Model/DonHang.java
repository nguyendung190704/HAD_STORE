package com.example.had_store.Model;

import java.util.Date;

public class DonHang {
    private int maDon;
    private Date ngayLap;
    private String trangThaiDon;
    private int maGio;

    public int getMaGio() {
        return maGio;
    }

    public void setMaGio(int maGio) {
        this.maGio = maGio;
    }

    public DonHang(int maDon, Date ngayLap, String trangThaiDon, int maGio, String maKh) {
        this.maDon = maDon;
        this.ngayLap = ngayLap;
        this.trangThaiDon = trangThaiDon;
        this.maGio = maGio;
        this.maKh = maKh;
    }

    private String maKh;

    public int getMaDon() {
        return maDon;
    }

    public void setMaDon(int maDon) {
        this.maDon = maDon;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getTrangThaiDon() {
        return trangThaiDon;
    }

    public void setTrangThaiDon(String trangThaiDon) {
        this.trangThaiDon = trangThaiDon;
    }



    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public DonHang() {
    }

    public DonHang(int maDon, Date ngayLap, String trangThaiDon, String maKh) {
        this.maDon = maDon;
        this.ngayLap = ngayLap;
        this.trangThaiDon = trangThaiDon;
        this.maKh = maKh;
    }
}

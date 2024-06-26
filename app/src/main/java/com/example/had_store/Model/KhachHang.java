package com.example.had_store.Model;

public class KhachHang {
    private String maKh;
    private String tenKh,matKhauKh;
    private int soKh;
    private String emailKh,diaChiKh,anhKh;

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKh='" + maKh + '\'' +
                ", tenKh='" + tenKh + '\'' +
                ", matKhauKh='" + matKhauKh + '\'' +
                ", soKh=" + soKh +
                ", emailKh='" + emailKh + '\'' +
                ", diaChiKh='" + diaChiKh + '\'' +
                ", anhKh='" + anhKh + '\'' +
                '}';
    }

    public KhachHang(String maKh, String tenKh, String matKhauKh, int soKh, String emailKh, String diaChiKh, String anhKh) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.matKhauKh = matKhauKh;
        this.soKh = soKh;
        this.emailKh = emailKh;
        this.diaChiKh = diaChiKh;
        this.anhKh = anhKh;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getMatKhauKh() {
        return matKhauKh;
    }

    public void setMatKhauKh(String matKhauKh) {
        this.matKhauKh = matKhauKh;
    }

    public int getSoKh() {
        return soKh;
    }

    public void setSoKh(int soKh) {
        this.soKh = soKh;
    }

    public String getEmailKh() {
        return emailKh;
    }

    public void setEmailKh(String emailKh) {
        this.emailKh = emailKh;
    }

    public String getDiaChiKh() {
        return diaChiKh;
    }

    public void setDiaChiKh(String diaChiKh) {
        this.diaChiKh = diaChiKh;
    }

    public String getAnhKh() {
        return anhKh;
    }

    public void setAnhKh(String anhKh) {
        this.anhKh = anhKh;
    }

    public KhachHang() {
    }


}

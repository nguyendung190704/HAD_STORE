package com.example.had_store.Model;

public class NhanVien {
    private String maNv;
    private String tenNv,matKhauNv;
    private int soNv;
    private String emailNv;
    private String anhNv;

    public NhanVien() {
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNv=" + maNv +
                ", tenNv='" + tenNv + '\'' +
                ", matKhauNv='" + matKhauNv + '\'' +
                ", soNv=" + soNv +
                ", emailNv='" + emailNv + '\'' +
                ", anhNv='" + anhNv + '\'' +
                '}';
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getMatKhauNv() {
        return matKhauNv;
    }

    public void setMatKhauNv(String matKhauNv) {
        this.matKhauNv = matKhauNv;
    }

    public int getSoNv() {
        return soNv;
    }

    public void setSoNv(int soNv) {
        this.soNv = soNv;
    }

    public String getEmailNv() {
        return emailNv;
    }

    public void setEmailNv(String emailNv) {
        this.emailNv = emailNv;
    }

    public String getAnhNv() {
        return anhNv;
    }

    public void setAnhNv(String anhNv) {
        this.anhNv = anhNv;
    }

    public NhanVien(String maNv, String tenNv, String matKhauNv, int soNv, String emailNv, String anhNv) {
        this.maNv = maNv;
        this.tenNv = tenNv;
        this.matKhauNv = matKhauNv;
        this.soNv = soNv;
        this.emailNv = emailNv;
        this.anhNv = anhNv;
    }
}

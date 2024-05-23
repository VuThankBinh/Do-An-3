package com.vtb.dhbc.ClassDL;

public class CauHoi {
    private int id;
    private String HinhAnh;
    private String DapAn;
    private int TinhTrang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getDapAn() {
        return DapAn;
    }

    public void setDapAn(String dapAn) {
        DapAn = dapAn;
    }

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public CauHoi(int id, String hinhAnh, String dapAn, int tinhTrang) {
        this.id = id;
        HinhAnh = hinhAnh;
        DapAn = dapAn;
        TinhTrang = tinhTrang;
    }

    public CauHoi() {
    }

}

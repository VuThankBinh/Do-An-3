package com.vtb.dhbc.ClassDL;

public class Room {
    public String player1Id;
    public String player2Id;
    public String danhSachManhGhepDaMo;
    public String danhSachCauCaDao;
    public int questionTu;
    public String turn;
    public String status;
    public String idWin;
    public  String statusP1;
    public  String statusP2;

    public Room() {
        // Default constructor
    }

    public Room(String player1Id, String player2Id, String danhSachCauCaDao, String danhSachManhGhepDaMo, int currentQuestionIndex, String turn, String status,String idwin,String statusp1,String statusp2) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.danhSachManhGhepDaMo = danhSachManhGhepDaMo;
        this.danhSachCauCaDao = danhSachCauCaDao;
        this.questionTu = currentQuestionIndex;
        this.turn = turn;
        this.status = status;
        this.idWin=idwin;
        this.statusP1=statusp1;
        this.statusP2=statusp2;
    }

    public String getIdWin() {
        return idWin;
    }

    public String getStatusP1() {
        return statusP1;
    }

    public void setStatusP1(String statusP1) {
        this.statusP1 = statusP1;
    }

    public String getStatusP2() {
        return statusP2;
    }

    public void setStatusP2(String statusP2) {
        this.statusP2 = statusP2;
    }

    public void setIdWin(String idWin) {
        this.idWin = idWin;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public String getDanhSachManhGhepDaMo() {
        return danhSachManhGhepDaMo;
    }

    public void setDanhSachManhGhepDaMo(String danhSachManhGhepDaMo) {
        this.danhSachManhGhepDaMo = danhSachManhGhepDaMo;
    }

    public String getDanhSachCauCaDao() {
        return danhSachCauCaDao;
    }

    public void setDanhSachCauCaDao(String danhSachCauCaDao) {
        this.danhSachCauCaDao = danhSachCauCaDao;
    }

    public int getQuestionTu() {
        return questionTu;
    }

    public void setQuestionTu(int questionTu) {
        this.questionTu = questionTu;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
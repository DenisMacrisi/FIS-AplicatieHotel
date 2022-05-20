package com.example.proiectfis;

public class reservation {

    private int reservationID;

    private int roomNumber;

    private String reservation_date;
    private String status;
    private String username;

    public reservation(int reservationID,String username, int roomNumber,  String status,  String reservation_date) {
        this.reservationID = reservationID;
        this.username=username;
        this.roomNumber = roomNumber;
        this.status = status;
        this.reservation_date = reservation_date;

    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public int getReservationID() {
        return reservationID;
    }
    public String getReservationDate() {
        return reservation_date;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public final void setCustomerIDNumber(String Username){
        this.username=Username;
    }
    public final void setReservationDate(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public final void setStatus(String status) {
        this.status = status;
    }

    public final void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
}
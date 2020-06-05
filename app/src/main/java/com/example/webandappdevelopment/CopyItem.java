package com.example.webandappdevelopment;

public class CopyItem {
    private int copyId;
    private String status;
    private boolean isBorrowable;

    public CopyItem(int copyId, String status, boolean isBorrowable) {
        this.copyId = copyId;
        this.status = status;
        this.isBorrowable = isBorrowable;
    }

    public int getCopyId() {
        return copyId;
    }

    public String getStatus() {
        return status;
    }

    public boolean isBorrowable() {
        return isBorrowable;
    }
}

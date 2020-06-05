package com.example.webandappdevelopment;

public class LoanItem {
    private int exId;
    private String mBookTitle;
    private String mBookAuthor;
    private String getmBookIsbn;
    private String mBorrowed;
    private String mDueDate;
    private String mNumberRenewals;

    public LoanItem(int exId, String mBookTitle, String mBookAuthor, String getmBookIsbn, String mBorrowed, String mDueDate, String mNumberRenewals) {
        this.exId = exId;
        this.mBookTitle = mBookTitle;
        this.mBookAuthor = mBookAuthor;
        this.getmBookIsbn = getmBookIsbn;
        this.mBorrowed = mBorrowed;
        this.mDueDate = mDueDate;
        this.mNumberRenewals = mNumberRenewals;
    }

    public int getExId() {
        return exId;
    }

    public String getmBorrowed() {
        return mBorrowed;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public String getmNumberRenewals() {
        return mNumberRenewals;
    }

    public void setmNumberRenewals(String mNumberRenewals) {
        this.mNumberRenewals = mNumberRenewals;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public String getGetmBookIsbn() {
        return getmBookIsbn;
    }

    public String getmDueDate() {
        return mDueDate;
    }
}

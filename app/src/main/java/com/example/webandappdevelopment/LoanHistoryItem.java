package com.example.webandappdevelopment;

public class LoanHistoryItem {
    private String mBookTitle;
    private String mBookAuthor;
    private String getmBookIsbn;
    private String mBorrowed;
    private String mDueDate;
    private String mReturned;
    private int mNumberRenewals;

    public LoanHistoryItem(String mBookTitle, String mBookAuthor, String getmBookIsbn, String mBorrowed, String mDueDate, String mReturned, int mNumberRenewals) {
        this.mBookTitle = mBookTitle;
        this.mBookAuthor = mBookAuthor;
        this.getmBookIsbn = getmBookIsbn;
        this.mBorrowed = mBorrowed;
        this.mDueDate = mDueDate;
        this.mReturned = mReturned;
        this.mNumberRenewals = mNumberRenewals;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public String getGetmBookIsbn() {
        return getmBookIsbn;
    }

    public String getmBorrowed() {
        return mBorrowed;
    }

    public String getmDueDate() {
        return mDueDate;
    }

    public String getmReturned() {
        return mReturned;
    }

    public int getmNumberRenewals() {
        return mNumberRenewals;
    }
}

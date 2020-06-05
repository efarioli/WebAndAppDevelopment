package com.example.webandappdevelopment;

public class LoanItemBuilder {
    private int exId;
    private String mBookTitle;
    private String mBookAuthor;
    private String mBookIsbn;
    private String mBorrowed;
    private String mDueDate;
    private String mNumberRenewals;

    public LoanItemBuilder setExId(int exId) {
        this.exId = exId;
        return this;
    }

    public LoanItemBuilder setmBookTitle(String mBookTitle) {
        this.mBookTitle = mBookTitle;
        return this;
    }

    public LoanItemBuilder setmBookAuthor(String mBookAuthor) {
        this.mBookAuthor = mBookAuthor;
        return this;
    }

    public LoanItemBuilder setmBookIsbn(String getmBookIsbn) {
        this.mBookIsbn = getmBookIsbn;
        return this;
    }

    public LoanItemBuilder setmBorrowed(String mBorrowed) {
        this.mBorrowed = mBorrowed;
        return this;
    }

    public LoanItemBuilder setmDueDate(String mDueDate) {
        this.mDueDate = mDueDate;
        return this;
    }

    public LoanItemBuilder setmNumberRenewals(String mNumberRenewals) {
        this.mNumberRenewals = mNumberRenewals;
        return this;
    }

    public LoanItem createExampleItem() {
        return new LoanItem(exId, mBookTitle, mBookAuthor, mBookIsbn, mBorrowed, mDueDate, mNumberRenewals);
    }
}
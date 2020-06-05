package com.example.webandappdevelopment;

public class LoanHistoryItemBuilder {
    private String mBookTitle;
    private String mBookAuthor;
    private String getmBookIsbn;
    private String mBorrowed;
    private String mDueDate;
    private String mReturned;
    private int mNumberRenewals;

    public LoanHistoryItemBuilder setmBookTitle(String mBookTitle) {
        this.mBookTitle = mBookTitle;
        return this;
    }

    public LoanHistoryItemBuilder setmBookAuthor(String mBookAuthor) {
        this.mBookAuthor = mBookAuthor;
        return this;
    }

    public LoanHistoryItemBuilder setGetmBookIsbn(String getmBookIsbn) {
        this.getmBookIsbn = getmBookIsbn;
        return this;
    }

    public LoanHistoryItemBuilder setmBorrowed(String mBorrowed) {
        this.mBorrowed = mBorrowed;
        return this;
    }

    public LoanHistoryItemBuilder setmDueDate(String mDueDate) {
        this.mDueDate = mDueDate;
        return this;
    }

    public LoanHistoryItemBuilder setmReturned(String mReturned) {
        this.mReturned = mReturned;
        return this;
    }

    public LoanHistoryItemBuilder setmNumberRenewals(int mNumberRenewals) {
        this.mNumberRenewals = mNumberRenewals;
        return this;
    }

    public LoanHistoryItem createLoanHistoryItem() {
        return new LoanHistoryItem(mBookTitle, mBookAuthor, getmBookIsbn, mBorrowed, mDueDate, mReturned, mNumberRenewals);
    }
}
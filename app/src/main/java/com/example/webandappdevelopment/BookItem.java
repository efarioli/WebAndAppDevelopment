package com.example.webandappdevelopment;

public class BookItem {
    private int exId;
    private String mBookTitle;
    private String mBookAuthor;
    private String getmBookIsbn;

    public BookItem(int exId, String mBookTitle, String mBookAuthor, String getmBookIsbn) {
        this.exId = exId;
        this.mBookTitle = mBookTitle;
        this.mBookAuthor = mBookAuthor;
        this.getmBookIsbn = getmBookIsbn;
    }

    public int getExId() {
        return exId;
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
}

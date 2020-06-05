package com.example.webandappdevelopment.ModelDTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookDTO implements Parcelable
{
    private final int id;
    private final String title;
    private final String author;
    private final String isbn;
    private  ArrayList<CopyDTO> copies;

    public BookDTO(int id, String title, String author, String isbn)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = new ArrayList<>();
    }

    protected BookDTO(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        isbn = in.readString();
    }

    public static final Creator<BookDTO> CREATOR = new Creator<BookDTO>() {
        @Override
        public BookDTO createFromParcel(Parcel in) {
            return new BookDTO(in);
        }

        @Override
        public BookDTO[] newArray(int size) {
            return new BookDTO[size];
        }
    };

    public void addCopy(CopyDTO copy)
    {
        copies.add(copy);
    }

    public ArrayList<CopyDTO> getCopies()
    {
        return copies;
    }

    public int getId()
    {
        return id;
    }

    public int getNumberOfCopies()
    {
        return copies.size();
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getIsbn()
    {
        return isbn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(isbn);
    }
}

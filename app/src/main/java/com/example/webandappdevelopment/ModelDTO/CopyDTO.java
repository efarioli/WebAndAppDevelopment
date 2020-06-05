package com.example.webandappdevelopment.ModelDTO;

import android.os.Parcel;
import android.os.Parcelable;

public class CopyDTO implements Parcelable
{
    private final int id;
    private final BookDTO book;
    private final boolean referenceOnly;
    private final boolean onLoan;

    public CopyDTO(int id, BookDTO book, boolean referenceOnly, boolean onLoan)
    {
        this.id = id;
        this.book = book;
        this.referenceOnly = referenceOnly;
        this.onLoan = onLoan;
    }

    protected CopyDTO(Parcel in) {
        id = in.readInt();
        book = in.readParcelable(BookDTO.class.getClassLoader());
        referenceOnly = in.readByte() != 0;
        onLoan = in.readByte() != 0;
    }

    public static final Creator<CopyDTO> CREATOR = new Creator<CopyDTO>() {
        @Override
        public CopyDTO createFromParcel(Parcel in) {
            return new CopyDTO(in);
        }

        @Override
        public CopyDTO[] newArray(int size) {
            return new CopyDTO[size];
        }
    };

    public int getId()
    {
        return id;
    }

    public BookDTO getBook()
    {
        return book;
    }

    public String getStatus()
    {
        return isReferenceOnly() ? "Reference only" : (isOnLoan() ? "On loan" : "Available");
    }

    public boolean isOnLoan()
    {
        return onLoan;
    }

    public boolean isReferenceOnly()
    {
        return referenceOnly;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(book, flags);
        dest.writeByte((byte) (referenceOnly ? 1 : 0));
        dest.writeByte((byte) (onLoan ? 1 : 0));
    }
}
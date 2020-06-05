package com.example.webandappdevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<BookItem> mBookList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener {
        void onMoreInfoBtnClick(int position);
        //magic happen
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }


    public static class BookViewHolder extends RecyclerView.ViewHolder{
        //elements that appears in the view
        private TextView mTextViewBookTitle;
        private TextView mTextViewBookAuthor;
        private TextView mTextViewIsbn;
        private Button mViewDetailsButton;


        public BookViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextViewBookTitle = itemView.findViewById(R.id.book_title);
            mTextViewBookAuthor = itemView.findViewById(R.id.book_author);
            mTextViewIsbn = itemView.findViewById(R.id.book_isbn);
            mViewDetailsButton = itemView.findViewById(R.id.btn_view_book_detaisl);

            mViewDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onMoreInfoBtnClick(position);
                        }
                    }
                }
            });


        }
    }

    public BookAdapter(ArrayList<BookItem> exampleList){
        mBookList = exampleList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        BookViewHolder evh = new BookViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookItem currItem = mBookList.get(position);
        holder.mTextViewBookTitle.setText(currItem.getmBookTitle());
        holder.mTextViewBookAuthor.setText(currItem.getmBookAuthor());
        holder.mTextViewIsbn.setText(currItem.getGetmBookIsbn());

    }

    @Override
    public int getItemCount() {
        try {
            return mBookList.size();
        }catch (Exception ex){return 0;}

    }

}

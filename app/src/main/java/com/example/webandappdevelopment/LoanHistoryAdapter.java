package com.example.webandappdevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoanHistoryAdapter extends RecyclerView.Adapter<LoanHistoryAdapter.LoanHistoryViewHolder> {
    private ArrayList<LoanHistoryItem> mLoanHistoryList;

    public static class LoanHistoryViewHolder extends RecyclerView.ViewHolder{
        //elements that appears in the view
        private TextView mTextViewBookTitle;
        private TextView mTextViewBookAuthor;
        private TextView mTextViewIsbn;
        private TextView mLoanDate;
        private TextView mDueDate;
        private TextView mReturned;
        private TextView mNumberRenewals;


        public LoanHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewBookTitle = itemView.findViewById(R.id.tw_loan_history_book_title);
            mTextViewBookAuthor = itemView.findViewById(R.id.tw_loan_history_book_author);
            mTextViewIsbn = itemView.findViewById(R.id.tw_loan_history_book_isbn);
            mLoanDate = itemView.findViewById(R.id.tw_loan_history_copy_borrowed);
            mDueDate = itemView.findViewById(R.id.tw_loan_history_copy_due_date);
            mReturned = itemView.findViewById(R.id.tw_loan_history_copy_returned);
            mNumberRenewals = itemView.findViewById(R.id.tw_loan_history_copy_renewals);
        }
    }

    public LoanHistoryAdapter(ArrayList<LoanHistoryItem> loanHistoryList){
        mLoanHistoryList = loanHistoryList;
    }

    @NonNull
    @Override
    public LoanHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_history_item, parent, false);
        LoanHistoryViewHolder evh = new LoanHistoryViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull LoanHistoryViewHolder holder, int position) {
        LoanHistoryItem currItem = mLoanHistoryList.get(position);
        holder.mTextViewBookTitle.setText(currItem.getmBookTitle());
        holder.mTextViewBookAuthor.setText(currItem.getmBookAuthor());
        holder.mTextViewIsbn.setText(currItem.getGetmBookIsbn());
        holder.mLoanDate.setText(currItem.getmBorrowed());
        holder.mDueDate.setText(currItem.getmDueDate());
        holder.mReturned.setText(currItem.getmReturned());
        holder.mNumberRenewals.setText("" + currItem.getmNumberRenewals());
    }

    @Override
    public int getItemCount() {
        try{return mLoanHistoryList.size();} catch (Exception ex){return 0;}
    }

}

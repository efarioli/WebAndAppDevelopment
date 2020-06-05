package com.example.webandappdevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {
    private ArrayList<LoanItem> mLoanList;
    private OnItemClickListener mListener;



    public interface  OnItemClickListener {
        void onRenewBtnClick(int position);
        void onReturnBtnClick(int position);
        //magic happen
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }


    public static class LoanViewHolder extends RecyclerView.ViewHolder{
        //elements that appears in the view
        private TextView mTextViewBookTitle;
        private TextView mTextViewBookAuthor;
        private TextView mTextViewIsbn;
        private TextView mLoanDate;
        private TextView mDueDate;
        private TextView mNumberRenewals;
        private Button mRenewButton;
        private Button mReturnButton;


        public LoanViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextViewBookTitle = itemView.findViewById(R.id.book_title);
            mTextViewBookAuthor = itemView.findViewById(R.id.book_author);
            mTextViewIsbn = itemView.findViewById(R.id.book_isbn);
            mLoanDate = itemView.findViewById(R.id.copy_borrowed);
            mDueDate = itemView.findViewById(R.id.copy_due_date);
            mNumberRenewals = itemView.findViewById(R.id.copy_number_renewals);
            mRenewButton = itemView.findViewById(R.id.renew_button);
            mReturnButton = itemView.findViewById(R.id.return_book);

            mRenewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onRenewBtnClick(position);
                        }
                    }
                }
            });
            mReturnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReturnBtnClick(position);
                        }
                    }
                }
            });


        }
    }

    public LoanAdapter(ArrayList<LoanItem> loanItems){
        mLoanList = loanItems;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_item, parent, false);
        LoanViewHolder evh = new LoanViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        LoanItem currItem = mLoanList.get(position);
        holder.mTextViewBookTitle.setText(currItem.getmBookTitle());
        holder.mTextViewBookAuthor.setText(currItem.getmBookAuthor());
        holder.mTextViewIsbn.setText(currItem.getGetmBookIsbn());
        holder.mLoanDate.setText(currItem.getmBorrowed());
        holder.mDueDate.setText(currItem.getmDueDate());
        holder.mNumberRenewals.setText(currItem.getmNumberRenewals());
        if (Integer.parseInt(holder.mNumberRenewals.getText().toString()) >= 3){
            holder.mRenewButton.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        try {
            return mLoanList.size();
        } catch (Exception ex){return 0;}

    }


}

package com.example.webandappdevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CopyAdapter extends RecyclerView.Adapter<CopyAdapter.CopyViewHolder> {
    private ArrayList<CopyItem> mCopyList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onBorrowCopyBtnClick(int position);
        //magic happen
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }


    public static class CopyViewHolder extends RecyclerView.ViewHolder {
        //elements that appears in the view
        private TextView mTextViewCopyId;
        private TextView mTextViewCopyStatus;
        private Button mButtonBorrowCopy;


        public CopyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextViewCopyId = itemView.findViewById(R.id.text_view_copy_id);
            mTextViewCopyStatus = itemView.findViewById(R.id.text_view_copy_status);
            mButtonBorrowCopy = itemView.findViewById(R.id.button_borrow_copy);

            mButtonBorrowCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onBorrowCopyBtnClick(position);
                        }
                    }
                }
            });


        }
    }

    public CopyAdapter(ArrayList<CopyItem> copyList) {
        mCopyList = copyList;
    }

    @NonNull
    @Override
    public CopyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.copy_item, parent, false);
        CopyViewHolder evh = new CopyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CopyViewHolder holder, int position) {
        CopyItem currItem = mCopyList.get(position);
        holder.mTextViewCopyId.setText("" + currItem.getCopyId());
        holder.mTextViewCopyStatus.setText(currItem.getStatus());
        if (!currItem.isBorrowable()) {
            holder.mButtonBorrowCopy.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        try {
            return mCopyList.size();
        } catch (Exception ex) {return 0;}

    }
}

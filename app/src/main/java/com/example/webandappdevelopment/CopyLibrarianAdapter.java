package com.example.webandappdevelopment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CopyLibrarianAdapter extends RecyclerView.Adapter<CopyLibrarianAdapter.CopyLibrarianViewHolder> {
    private ArrayList<CopyItem> mCopyList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener {
        void onBorrowCopyBtnClick(int position);
        //magic happen
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }


    public static class CopyLibrarianViewHolder extends RecyclerView.ViewHolder{
        //elements that appears in the view
        private TextView mTextViewCopyId;
        private TextView mTextViewCopyStatus;


        public CopyLibrarianViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextViewCopyId = itemView.findViewById(R.id.text_view_copy_id_librarian);
            mTextViewCopyStatus = itemView.findViewById(R.id.text_view_copy_status_librarian);



        }
    }

    public CopyLibrarianAdapter(ArrayList<CopyItem> copyList){
        mCopyList = copyList;
    }

    @NonNull
    @Override
    public CopyLibrarianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.copy_item_librarian, parent, false);
        CopyLibrarianViewHolder evh = new CopyLibrarianViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CopyLibrarianViewHolder holder, int position) {
        CopyItem currItem = mCopyList.get(position);
        holder.mTextViewCopyId.setText(""+ currItem.getCopyId());
        holder.mTextViewCopyStatus.setText(currItem.getStatus());


    }

    @Override
    public int getItemCount() {
        try{
            return mCopyList.size();
        } catch (Exception ex){return 0;}

    }
}


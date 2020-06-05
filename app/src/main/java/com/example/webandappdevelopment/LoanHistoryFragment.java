package com.example.webandappdevelopment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandappdevelopment.ModelDTO.BookDTO;
import com.example.webandappdevelopment.ModelDTO.LoanDTO;
import com.example.webandappdevelopment.Utils.Myhelper;
import com.example.webandappdevelopment.Utils.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class LoanHistoryFragment extends Fragment {

    private ArrayList<LoanHistoryItem> mLoanHistoryList;
    private RecyclerView mRecyclerView;
    private LoanHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<LoanDTO> mHistoryLoans;

    private TextView title2;
    private TextView title3;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loan_history, container, false);

        title2 = v.findViewById(R.id.loan_history_title2);
        title3 = v.findViewById(R.id.loan_history_title3);
        title2.setText(SessionObject.getInstance().getMemberDTO().getName());
        title3.setText(R.string.loan_history);
        buildRecyclerView(v);

        createLoanHistoryList();
        return v;
    }



    private void createLoanHistoryList() {

        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        int memberID = SessionObject.getInstance(null, null).getMemberDTO().getId();
        Call<List<LoanDTO>> call = retroApi.loanHistory(memberID);
        mLoanHistoryList = new ArrayList<>();

        RetrofitHelper.makeCall((t) -> {
                    makeAsyncList(t);
                }, call, null, getActivity()

        );
    }

    private void makeAsyncList(List<LoanDTO> loans) {
        mHistoryLoans = loans;

        for (LoanDTO loan : mHistoryLoans) {
            BookDTO book = loan.getCopy().getBook();
            LoanHistoryItem loanHItem = new LoanHistoryItemBuilder()
                    .setmBookTitle(book.getTitle())
                    .setmBookAuthor(book.getAuthor())
                    .setGetmBookIsbn(book.getIsbn())
                    .setmBorrowed(Myhelper.CalendarToStringF(loan.getLoanDate()))
                    .setmDueDate(Myhelper.CalendarToStringF(loan.getDueDate()))
                    .setmReturned(Myhelper.CalendarToStringF(loan.getReturnDate()))
                    .setmNumberRenewals(loan.getNumberOfRenewals())
                    .createLoanHistoryItem();
            mLoanHistoryList.add(loanHItem);
        }
        buildRecyclerView(getView());
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_view_loan_history);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new LoanHistoryAdapter(mLoanHistoryList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
}

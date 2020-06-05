package com.example.webandappdevelopment;

import android.content.Context;
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
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class LoanFragment extends Fragment {
    private ArrayList<LoanItem> mExampleList;
    private RecyclerView mRecyclerView;
    private LoanAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<LoanDTO> mLoans;

    TextView mTitle1;
    TextView mTitle2;
    TextView mTitle3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loan, container, false);

        mTitle1 = v.findViewById(R.id.view_loans_title1);
        mTitle2 = v.findViewById(R.id.view_loans_title2);
        mTitle3 = v.findViewById(R.id.view_loans_title3);
        mTitle1.setText(R.string.member_area);
        mTitle2.setText(SessionObject.getInstance().getMemberDTO().getName());
        mTitle3.setText(R.string.current_loans);



        buildRecyclerView(v);
        createExampleList();
        return v;
    }

    public void renewLoan(int position, String text) {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<List<LoanDTO>> call = retroApi.renewLoan(mLoans.get(position).getId(), mLoans.get(position));
        Context context1 = getActivity();
        RetrofitHelper.makeCall((loans) -> {
                    renewLoanAsync(loans, position);

                }, call, position, context1
        );
    }

    public void renewLoanAsync(List<LoanDTO> loans, int position) {
        mLoans = loans;
        LoanDTO loan = mLoans.get(position);
        mExampleList.get(position).setmNumberRenewals("" + (loan.getNumberOfRenewals()));
        mAdapter.notifyItemChanged(position);
        String message = String.format("Book \"%s\" \n(Copy no %s)\nhas been renewed", loan.getCopy().getBook().getTitle(), "" + loan.getCopy().getId());
        StyleableToast.makeText(getContext(), message, R.style.toast_success).show();
    }


    public void returnBook(int position) {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<List<LoanDTO>> call = retroApi.addBookToLoanHistory(mLoans.get(position).getId(), mLoans.get(position));
        RetrofitHelper.makeCall((t) -> {
                    returnBookAsync(t, position);
                }, call, position, getActivity()
        );
    }

    private void returnBookAsync(List<LoanDTO> loans, int position) {

        mLoans.remove(position);
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    private void createExampleList() {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        int userId = SessionObject.getInstance().getMemberDTO().getId();
        Call<List<LoanDTO>> call = retroApi.getCurrentLoanForMember(userId);
        mExampleList = new ArrayList<>();

        RetrofitHelper.makeCall((t) -> {
                    makeAsyncList(t);
                }, call, null, getActivity()

        );
    }

    private void makeAsyncList(List<LoanDTO> loans) {
        mLoans = loans;
        for (LoanDTO loan : mLoans) {
            BookDTO book = loan.getCopy().getBook();
            LoanItem ex = new LoanItemBuilder().setExId(loan.getId())
                    .setmBookTitle(book.getTitle())
                    .setmBookAuthor(book.getAuthor())
                    .setmBookIsbn(book.getIsbn())
                    .setmBorrowed(Myhelper.CalendarToStringF(loan.getLoanDate()))
                    .setmDueDate(Myhelper.CalendarToStringF(loan.getDueDate()))
                    .setmNumberRenewals("" + loan.getNumberOfRenewals())
                    .createExampleItem();
            mExampleList.add(ex);
        }
        buildRecyclerView(getView());
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_view_loan);
        mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new LoanAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new LoanAdapter.OnItemClickListener() {
            @Override
            public void onRenewBtnClick(int position) {
                renewLoan(position, "Clicked! pos " + position);
            }

            @Override
            public void onReturnBtnClick(int position) {
                returnBook(position);
            }
        });
    }
}


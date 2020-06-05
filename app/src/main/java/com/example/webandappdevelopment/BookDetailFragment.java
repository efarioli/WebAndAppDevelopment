package com.example.webandappdevelopment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandappdevelopment.ModelDTO.BookDTO;
import com.example.webandappdevelopment.ModelDTO.CopyDTO;
import com.example.webandappdevelopment.ModelDTO.MemberDTO;
import com.example.webandappdevelopment.ModelDTO.ResultDTO;
import com.example.webandappdevelopment.Utils.RetrofitHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class BookDetailFragment extends Fragment {
    private static final String ARG_JSON_STRING_BOOK = "argTextJsonBook";

    private TextView mBookTitle;
    private TextView mBookAuthor;
    TextView mBookIsbn;
    TextView mBookNumCopies;
    BookDTO mBook;

    private ArrayList<CopyItem> mCopyList;
    List<CopyDTO> mCopies;
    private RecyclerView mRecyclerView;
    private CopyAdapter mCopyAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView title2;
    private TextView title3;


    public static BookDetailFragment newInstance(String jsonString) {
        BookDetailFragment sf = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSON_STRING_BOOK, jsonString);
        sf.setArguments(args);
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
        mBook = getBookDetails();
        buildRecyclerView(v);
        createCopyList(mBook);

        title2 = v.findViewById(R.id.book_details_title2);
        title3 = v.findViewById(R.id.book_details_title3);
        title2.setText(SessionObject.getInstance().getMemberDTO().getName());
        title3.setText(R.string.book_details);


        return v;
    }

    private void setupViewTextsForBook(View v, BookDTO bookDTO) {
        mBookTitle = v.findViewById(R.id.text_view_book_detail_title);
        mBookAuthor = v.findViewById(R.id.text_view_book_detail_author);
        mBookIsbn = v.findViewById(R.id.text_view_book_detail_isbn);
        mBookNumCopies = v.findViewById(R.id.text_view_book_detail_copies);
        mBookTitle.setText(bookDTO.getTitle());
        mBookAuthor.setText(bookDTO.getAuthor());
        mBookIsbn.setText(bookDTO.getIsbn());
        mBookNumCopies.setText("Number of Copies: " + bookDTO.getNumberOfCopies());
    }

    public void borrowCopy(int position) {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        SessionObject sessionObject = SessionObject.getInstance();
        MemberDTO memberDTO = sessionObject.getMemberDTO();
        Call<ResultDTO> call = retroApi.borrowCopy(memberDTO.getId(), mCopies.get(position).getId());
        RetrofitHelper.makeCall((resultDTO) -> {
                    borrowCopyAsync(resultDTO, position);
                }, call, position, getContext()
        );
    }

    private BookDTO getBookDetails() {
        BookDTO book = null;
        if (getArguments() != null) {
            String text = getArguments().getString(ARG_JSON_STRING_BOOK);
            book = new Gson().fromJson(text, BookDTO.class);
            List<CopyDTO> copies = book.getCopies();
        }
        return book;
    }

    public void borrowCopyAsync(ResultDTO result, int position) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoanFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

        navigationView.setCheckedItem(R.id.nav_current_loans);
        String message = String.format("Book \"%s\" \n(Copy no %s)\nhas been borrowed", mBook.getTitle(), "" + mCopies.get(position).getId());
        StyleableToast.makeText(getContext(), message, R.style.toast_success).show();

    }

    private void createCopyList(BookDTO bookDTO) {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<BookDTO> call = retroApi.getBookWithCopies(bookDTO.getId());
        mCopyList = new ArrayList<>();

        RetrofitHelper.makeCall((book) -> {
                    makeCopyListAsync(book);
                }, call, null, getActivity()
        );


    }

    private void makeCopyListAsync(BookDTO book) {
        mCopies = book.getCopies();
        for (CopyDTO copy : mCopies) {
            CopyItem copyItem = new CopyItem(
                    copy.getId(),
                    copy.getStatus(),
                    copy.isReferenceOnly() ? false : copy.isOnLoan() ? false : true);
            mCopyList.add(copyItem);
        }
        setupViewTextsForBook(getView(), book);

        buildRecyclerView(getView());
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.copies_item_rec_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCopyAdapter = new CopyAdapter(mCopyList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCopyAdapter);
        mCopyAdapter.setOnItemClickListener(new CopyAdapter.OnItemClickListener() {
            @Override
            public void onBorrowCopyBtnClick(int position) {
                borrowCopy(position);
            }
        });
    }
}

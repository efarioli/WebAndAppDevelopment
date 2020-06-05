package com.example.webandappdevelopment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandappdevelopment.ModelDTO.BookDTO;
import com.example.webandappdevelopment.ModelDTO.CopyDTO;
import com.example.webandappdevelopment.Utils.RetrofitHelper;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class BookDetailLibrarianFragment extends Fragment {
    private static final String ARG_JSON_STRING_BOOK = "argTextJsonBook";

    private TextView mBookTitle;
    private TextView mBookAuthor;
    TextView mBookIsbn;
    TextView mBookNumCopies;
    BookDTO mBook;
    private Button mAddCopy;

    private com.shawnlin.numberpicker.NumberPicker numberPicker1;
    private Switch aSwitch;

    private ArrayList<CopyItem> mCopyList;
    List<CopyDTO> mCopies;
    private RecyclerView mRecyclerView;
    private CopyLibrarianAdapter mCopyLibrarianAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView title1;
    private TextView title2;


    public static BookDetailLibrarianFragment newInstance(String jsonString) {
        BookDetailLibrarianFragment sf = new BookDetailLibrarianFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSON_STRING_BOOK, jsonString);
        sf.setArguments(args);


        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details_librarian, container, false);
        title1 = v.findViewById(R.id.book_details_lib_title1);
        title2 = v.findViewById(R.id.book_details_lib_title2);
        title1.setText(R.string.librarian_area);
        title2.setText(SessionObject.getInstance().getLibrarianDTO().getName());

        BookDTO mBook = getBookDetails();
        buildRecyclerView(v);
        createCopyList(mBook);
        mAddCopy = v.findViewById(R.id.btn_add_copies);
        numberPicker1 = v.findViewById(R.id.number_picker);
        aSwitch = v.findViewById(R.id.switch_is_reference);


        mAddCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCopies(mBook, numberPicker1.getValue(), aSwitch.isChecked());
            }
        });
        return v;
    }

    private void setupViewTextsForBook(View v, BookDTO bookDTO) {
        mBookTitle = v.findViewById(R.id.tw__lib_book_detail_title);
        mBookAuthor = v.findViewById(R.id.tw_book_lib_detail_author);
        mBookIsbn = v.findViewById(R.id.tw_Lib_book_detail_isbn);
        mBookNumCopies = v.findViewById(R.id.tw_lib_book_detail_copies);
        mBookTitle.setText(bookDTO.getTitle());
        mBookAuthor.setText(bookDTO.getAuthor());
        mBookIsbn.setText(bookDTO.getIsbn());
        mBookNumCopies.setText("Number of Copies: " + bookDTO.getNumberOfCopies());
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
        mRecyclerView = v.findViewById(R.id.copies_item_rec_view_librarian);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCopyLibrarianAdapter = new CopyLibrarianAdapter(mCopyList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCopyLibrarianAdapter);
    }

    private void addCopies(BookDTO bookDTO, int numCopies, boolean isRef) {
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<BookDTO> call = retroApi.addCopies(bookDTO.getId(), numCopies, isRef);
        RetrofitHelper.makeCall((book) -> {
                    addCopiesAsync(book);
                }, call, null, getActivity()
        );
    }

    private void addCopiesAsync(BookDTO book) {

        int oldValue = mCopies.size();
        mCopies = book.getCopies();
        int newValue = mCopies.size();

        int copiesSize = mCopies.size();
        for (int i = oldValue; i < newValue; i++) {
            CopyItem copyItem = new CopyItem(
                    mCopies.get(i).getId(),
                    mCopies.get(i).getStatus(),
                    mCopies.get(i).isReferenceOnly() ? false : mCopies.get(i).isOnLoan() ? false : true);
            mCopyList.add(copyItem);
           mCopyLibrarianAdapter.notifyItemInserted(i);
        }

        int copiesAdded = mCopies.size() - oldValue;
        mBookNumCopies.setText("Number of Copies: " + book.getNumberOfCopies());

        if (copiesAdded > 0) {
            String message = String.format("%s copy/ies has been added!", copiesAdded);
            StyleableToast.makeText(getView().getContext(), message, R.style.toast_success).show();
        }
    }
}

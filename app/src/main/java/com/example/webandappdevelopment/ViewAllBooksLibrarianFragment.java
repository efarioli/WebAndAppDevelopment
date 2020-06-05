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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandappdevelopment.ModelDTO.BookDTO;
import com.example.webandappdevelopment.ModelDTO.LoanDTO;
import com.example.webandappdevelopment.Utils.RetrofitHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ViewAllBooksLibrarianFragment extends Fragment {
    private Call<List<LoanDTO>> call;

    private ArrayList<BookItem> mBookList;
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<BookDTO> mBooks;
    private RestFulWebServiceApi retroApi;
    private View v;

    private TextView title1;
    private TextView title2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_view_all_books, container, false);
        title1 = v.findViewById(R.id.view_all_books_title1);
        title2 = v.findViewById(R.id.view_all_books_title2);
        title1.setText(R.string.librarian_area);
        title2.setText(SessionObject.getInstance().getLibrarianDTO().getName());

        buildRecyclerView(v);
        createExampleList();
        return v;

    }


    public void seeMoreInfo(int position) {
        BookDTO book = mBooks.get(position);
        String bookString = new Gson().toJson(book);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view_librarian);

        /////SHOW THE OPTION "ALL BOOKS" IN THE DRAWER UNCHECKED
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        final BookDetailLibrarianFragment bookDetailFragment = BookDetailLibrarianFragment.newInstance(bookString);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_librarian,
                bookDetailFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }


    private void createExampleList() {

        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<List<BookDTO>> call = retroApi.getAllBooks();
        mBookList = new ArrayList<>();

        RetrofitHelper.makeCall((books) -> {
                    makeAsyncList(books);
                }, call, null, getActivity()

        );
    }

    private void makeAsyncList(List<BookDTO> books) {
        mBooks = books;
        for (BookDTO book : mBooks) {
            BookItem bookItem = new BookItem(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn()
            );
            mBookList.add(bookItem);
        }
        buildRecyclerView(getView());
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.book_item_rec_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new BookAdapter(mBookList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onMoreInfoBtnClick(int position) {
                seeMoreInfo(position);
            }
        });
    }
}


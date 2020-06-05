package com.example.webandappdevelopment;

import com.example.webandappdevelopment.ModelDTO.BookDTO;
import com.example.webandappdevelopment.ModelDTO.LibrarianDTO;
import com.example.webandappdevelopment.ModelDTO.LoanDTO;
import com.example.webandappdevelopment.ModelDTO.MemberDTO;
import com.example.webandappdevelopment.ModelDTO.ResultDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestFulWebServiceApi {
    @GET("checkloginmember")
    Call<MemberDTO> checkLogin(@HeaderMap Map<String, String> headers);

    @GET("checkloginadmin")
    Call<LibrarianDTO> checkLoginAdmin(@HeaderMap Map<String, String> headers);


    @GET("members/{id}/loans")
    Call<List<LoanDTO>> getCurrentLoanForMember(@Path("id") int userId);

    @GET("books")
    Call<List<BookDTO>> getAllBooks();

    @GET("books/{id}")
    Call<BookDTO> getBookWithCopies(@Path("id") int bookId);

    @POST("books/{bookid}/copies/{numofcopyes}/{isref}")
    Call<BookDTO> addCopies(@Path("bookid") int bookId, @Path("numofcopyes") int numberOfCopies, @Path("isref") boolean isRef);

    @PUT("loans/{loanid}")
    Call<List<LoanDTO>> renewLoan(@Path("loanid") int loanId, @Body LoanDTO loan);

    @POST("loans/history/{loanid}")
    Call<List<LoanDTO>> addBookToLoanHistory(@Path("loanid") int loanId, @Body LoanDTO loan);

    @PUT("member/{memberid}/borrowcopy/{copyid}")
    Call<ResultDTO> borrowCopy(@Path("memberid") int memberId, @Path("copyid") int copyId);

    @GET("loanhistory/{memberid}")
    Call<List<LoanDTO>> loanHistory(@Path("memberid") int memberId);

}

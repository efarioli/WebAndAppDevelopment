package com.example.webandappdevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webandappdevelopment.ModelDTO.LibrarianDTO;
import com.example.webandappdevelopment.ModelDTO.MemberDTO;
import com.example.webandappdevelopment.Utils.Myhelper;
import com.example.webandappdevelopment.Utils.RetrofitHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout textInputUserName;
    private TextInputLayout textInputPassword;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private MaterialButton mMember;
    private MaterialButton mLibrarian;
    private Button mBtnLogin;

    private Button buttonChoosen;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputUserName = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
        mMember = findViewById(R.id.btn_member);

        materialButtonToggleGroup = findViewById(R.id.toggleGroup);
        mLibrarian = findViewById(R.id.btn_librarian);
        mBtnLogin = findViewById(R.id.button_login);


        makeToggleButtonactLikeRadioButton();

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean areEmpty = areInputFieldsEmpty();
                if (areEmpty){
                    StyleableToast.makeText(getApplicationContext(), "Username and Password\nmust not be empty", R.style.toast_error).show();
                    return;
                }
                int checkedBtn = materialButtonToggleGroup.getCheckedButtonId();
                buttonChoosen = findViewById(checkedBtn);

                if (buttonChoosen.getText().toString().toLowerCase().equals("member")) {
                    checkLoginMember();
                } else {
                    checkLoginAdmin();
                }
            }
        });

    }

    private boolean areInputFieldsEmpty() {
        if (textInputUserName.getEditText().getText().length()==0 || textInputPassword.getEditText().getText().length()==0){
            return true;
        }
        return false;
    }

    private void checkLoginMember() {
        Map<String, String> headers = getMapOfHeadersFromTextViews();
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<MemberDTO> call = retroApi.checkLogin(headers);

        RetrofitHelper.makeCall((member) -> {
                    checkLoginMemberAsync(member);
                }, call, null, this
        );
    }

    private void checkLoginMemberAsync(MemberDTO memberDTO) {
        if (memberDTO.getId() != 0) {
            SessionObject sessionObject = SessionObject.getInstance(memberDTO, null);
            openMemberMainActivity();
        } else {
            textInputPassword.getEditText().setText("");
            StyleableToast.makeText(this, "Invalid Credentials!", R.style.toast_error).show();

        }
    }

    private void checkLoginAdmin() {
        Map<String, String> headers = getMapOfHeadersFromTextViews();
        RestFulWebServiceApi retroApi = RetrofitHelper.getRetrofitSepUP();
        Call<LibrarianDTO> call = retroApi.checkLoginAdmin(headers);
        RetrofitHelper.makeCall((member) -> {
                    checkLoginAdminAsync(member);
                }, call, null, this

        );
    }

    private void checkLoginAdminAsync(LibrarianDTO librarianDTO) {
        if (librarianDTO.getId() != 0) {
            SessionObject sessionObject = SessionObject.getInstance(null, librarianDTO);
            openLibrarianMainActivity();
        } else {
            textInputPassword.getEditText().setText("");
            StyleableToast.makeText(this, "Invalid Credentials!", R.style.toast_error).show();
        }
    }

    private Map<String, String> getMapOfHeadersFromTextViews() {
        String queryString = textInputUserName.getEditText().getText() + ":" + textInputPassword.getEditText().getText();
        String strEncode = Myhelper.getEncoded(queryString);
        Map headers = new HashMap<String, String>();
        headers.put("authorization", strEncode);
        return headers;
    }

    private void makeToggleButtonactLikeRadioButton() {
        mMember.addOnCheckedChangeListener((button, isChecked) -> {
            if (!isChecked && !mLibrarian.isChecked()) {
                button.setChecked(true);
            }
        });
        mLibrarian.addOnCheckedChangeListener((button, isChecked) -> {
            if (!isChecked && !mMember.isChecked()) {
                button.setChecked(true);
            }
        });
    }

    private void openMemberMainActivity() {
        Intent intent = new Intent(this, MemberActivity.class);
        startActivity(intent);
    }

    private void openLibrarianMainActivity() {
        Intent intent = new Intent(this, LibrarianActivity.class);
        startActivity(intent);
    }
}
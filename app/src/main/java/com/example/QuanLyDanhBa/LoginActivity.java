package com.example.QuanLyDanhBa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private TextView tvError;
    private Button btnLogin;

    // Tài khoản cố định
    private static final String FIXED_USERNAME = "admin";
    private static final String FIXED_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);

        tvError.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    tvError.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }

                if (username.equals(FIXED_USERNAME) && password.equals(FIXED_PASSWORD)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Không cho quay lại màn hình đăng nhập bằng nút Back
                } else {
                    tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
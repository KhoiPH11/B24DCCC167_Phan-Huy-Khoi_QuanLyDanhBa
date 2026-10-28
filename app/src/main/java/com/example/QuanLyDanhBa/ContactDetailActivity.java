package com.example.QuanLyDanhBa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView tvInitial, tvName, tvPhone, tvGroupGender, tvNote;
    private CheckBox chkFavorite;
    private Button btnCall, btnBack, btnDelete;

    private Contact contact;
    private int position;
    private boolean favoriteChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        tvInitial = findViewById(R.id.tvInitial);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvGroupGender = findViewById(R.id.tvGroupGender);
        tvNote = findViewById(R.id.tvNote);
        chkFavorite = findViewById(R.id.chkFavorite);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        // Nhận dữ liệu từ Intent
        contact = (Contact) getIntent().getSerializableExtra("contact");
        position = getIntent().getIntExtra("position", -1);

        if (contact == null) {
            Toast.makeText(this, "Không tìm thấy liên hệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị thông tin
        tvInitial.setText(contact.getInitial());
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvGroupGender.setText(contact.getGroup() + "  •  " + contact.getGender());
        tvNote.setText(contact.getNote().isEmpty() ? "(Không có ghi chú)" : contact.getNote());
        chkFavorite.setChecked(contact.isFavorite());

        // Xử lý thay đổi yêu thích
        chkFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contact.setFavorite(isChecked);
                favoriteChanged = true;
            }
        });

        // Nút Gọi điện
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + contact.getPhone()));
                startActivity(dialIntent);
            }
        });

        // Nút Quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
            }
        });

        // Nút Xóa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactDetailActivity.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa liên hệ \"" + contact.getName() + "\"?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("action", "delete");
                            resultIntent.putExtra("position", position);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        // Xử lý nút Back vật lý / cử chỉ
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                returnResult();
            }
        });
    }

    private void returnResult() {
        Intent resultIntent = new Intent();
        if (favoriteChanged) {
            resultIntent.putExtra("action", "update");
            resultIntent.putExtra("contact", contact);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
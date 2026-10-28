package com.example.QuanLyDanhBa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtNote;
    private Spinner spnGroup;
    private RadioGroup rgGender;
    private CheckBox chkFavorite;
    private Button btnSave;

    private ArrayList<String> existingPhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtNote = findViewById(R.id.edtNote);
        spnGroup = findViewById(R.id.spnGroup);
        rgGender = findViewById(R.id.rgGender);
        chkFavorite = findViewById(R.id.chkFavorite);
        btnSave = findViewById(R.id.btnSave);

        // Nhận danh sách số điện thoại hiện có
        existingPhones = getIntent().getStringArrayListExtra("existingPhones");
        if (existingPhones == null) {
            existingPhones = new ArrayList<>();
        }

        // Setup Spinner nhóm
        String[] groups = {"Gia đình", "Bạn bè", "Công việc", "Khác"};
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroup.setAdapter(groupAdapter);

        // Mặc định chọn Nam
        RadioButton rbMale = findViewById(R.id.rbMale);
        rbMale.setChecked(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }

    private void saveContact() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String note = edtNote.getText().toString().trim();
        String group = spnGroup.getSelectedItem().toString();
        boolean favorite = chkFavorite.isChecked();

        // Kiểm tra họ tên
        if (name.isEmpty()) {
            edtName.setError("Họ tên không được để trống");
            edtName.requestFocus();
            return;
        }

        // Kiểm tra số điện thoại
        if (phone.isEmpty()) {
            edtPhone.setError("Số điện thoại không được để trống");
            edtPhone.requestFocus();
            return;
        }

        if (!isValidPhone(phone)) {
            edtPhone.setError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0");
            edtPhone.requestFocus();
            return;
        }

        // Kiểm tra trùng số điện thoại
        if (existingPhones.contains(phone)) {
            edtPhone.setError("Số điện thoại đã tồn tại trong danh bạ");
            edtPhone.requestFocus();
            Toast.makeText(this, "Số điện thoại đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy giới tính
        String gender = "Nam";
        int checkedId = rgGender.getCheckedRadioButtonId();
        if (checkedId == R.id.rbFemale) {
            gender = "Nữ";
        } else if (checkedId == R.id.rbOther) {
            gender = "Khác";
        }

        Contact newContact = new Contact(name, phone, group, gender, favorite, note);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newContact", newContact);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private boolean isValidPhone(String phone) {
        // Phải đúng 10 chữ số và bắt đầu bằng 0
        return phone.matches("^0\\d{9}$");
    }
}
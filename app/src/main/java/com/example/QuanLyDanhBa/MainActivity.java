package com.example.QuanLyDanhBa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD = 100;
    private static final int REQUEST_DETAIL = 101;

    private TextView tvStats;
    private ListView lvContacts;
    private Button btnAdd;

    private ArrayList<Contact> contactList;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStats = findViewById(R.id.tvStats);
        lvContacts = findViewById(R.id.lvContacts);
        btnAdd = findViewById(R.id.btnAdd);

        // Khởi tạo dữ liệu mẫu
        contactList = new ArrayList<>();
        contactList.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, ""));
        contactList.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, "Chị gái"));
        contactList.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nam", false, "Đồng nghiệp"));

        adapter = new ContactAdapter(this, contactList);
        lvContacts.setAdapter(adapter);

        updateStats();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                // Truyền danh sách số điện thoại hiện có để kiểm tra trùng
                ArrayList<String> phoneList = new ArrayList<>();
                for (Contact c : contactList) {
                    phoneList.add(c.getPhone());
                }
                intent.putStringArrayListExtra("existingPhones", phoneList);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
                intent.putExtra("contact", contact);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });
    }

    private void updateStats() {
        int total = contactList.size();
        int favCount = 0;
        for (Contact c : contactList) {
            if (c.isFavorite()) {
                favCount++;
            }
        }
        tvStats.setText("Tổng: " + total + " liên hệ  |  Yêu thích: " + favCount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD) {
                Contact newContact = (Contact) data.getSerializableExtra("newContact");
                if (newContact != null) {
                    contactList.add(newContact);
                    adapter.notifyDataSetChanged();
                    updateStats();
                }
            } else if (requestCode == REQUEST_DETAIL) {
                String action = data.getStringExtra("action");
                int position = data.getIntExtra("position", -1);

                if (position >= 0 && position < contactList.size()) {
                    if ("delete".equals(action)) {
                        contactList.remove(position);
                    } else if ("update".equals(action)) {
                        Contact updated = (Contact) data.getSerializableExtra("contact");
                        if (updated != null) {
                            contactList.set(position, updated);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    updateStats();
                }
            }
        }
    }
}
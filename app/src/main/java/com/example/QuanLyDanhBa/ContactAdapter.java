package com.example.QuanLyDanhBa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        }

        Contact contact = contacts.get(position);

        TextView tvInitial = convertView.findViewById(R.id.tvInitial);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvGroup = convertView.findViewById(R.id.tvGroup);
        ImageView imgFavorite = convertView.findViewById(R.id.imgFavorite);

        tvInitial.setText(contact.getInitial());
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvGroup.setText(contact.getGroup());

        if (contact.isFavorite()) {
            imgFavorite.setVisibility(View.VISIBLE);
            imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            imgFavorite.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
package com.example.QuanLyDanhBa;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private String group;
    private String gender;
    private boolean favorite;
    private String note;

    public Contact(String name, String phone, String group, String gender, boolean favorite, String note) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.favorite = favorite;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Lấy chữ cái đầu của tên (phần cuối cùng sau khoảng trắng)
    public String getInitial() {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        String lastName = parts[parts.length - 1];
        return lastName.substring(0, 1).toUpperCase();
    }
}
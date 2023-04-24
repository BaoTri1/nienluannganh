package com.example.shopproject.sqlite.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Account")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String _id;
    private String email;
    private String passwd;
    private boolean login;

    public Account(String _id, String email, String passwd, boolean login) {
        this._id = _id;
        this.email = email;
        this.passwd = passwd;
        this.login = login;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}

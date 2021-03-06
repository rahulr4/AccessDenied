package com.kairos.android.example.User;

import java.sql.Time;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by akashdeep on 2/22/2018.
 */

public class User extends RealmObject{

    @PrimaryKey
    long id;

    String name;

    String email;

    RealmList<TimeStamp> time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<TimeStamp> getTime() {
        return time;
    }

    public void setTime(RealmList<TimeStamp> time) {
        this.time = time;
    }
}

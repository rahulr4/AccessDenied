package com.kairos.android.example.database;

import com.google.gson.Gson;

import android.app.Application;
import android.content.Context;

import com.kairos.android.example.User.User;
import com.kairos.android.example.User.UserInterface;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by akashdeep on 2/22/2018.
 */

public class RealmDatabaseController implements GenericDatabaseInterface {

    private static RealmDatabaseController instance;
    private Realm realm;

    public static RealmDatabaseController getInstance() {
        if(instance == null)
            instance = new RealmDatabaseController();
        return instance;
    }

    @Override
    public void init(Context context) {

        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tasky.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void insertUser(User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    @Override
    public User getUser(int id) {
        return realm.where(User.class).equalTo("id", id).findFirst();
    }
}

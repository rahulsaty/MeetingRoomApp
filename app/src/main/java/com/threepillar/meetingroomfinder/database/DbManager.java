package com.threepillar.meetingroomfinder.database;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * RealmManager
 */
public class DbManager {

    private static final String TAG = "DbManager";
    private static Realm realm;
    private static RealmConfiguration realmConfiguration;

    //Before you can use Realm in your app, you must initialize it first.
    public static void init(Context context) {
        Realm.init(context);
    }


    public static void initializeRealmConfig() {
        if (realmConfiguration == null) {
            Log.d(TAG, "Initializing Realm configuration.");
            setRealmConfiguration(new RealmConfiguration.Builder()
                    .name("iambank.realm")
                    .modules(new DbModule())
//                  .schemaVersion(2)                     // Must be bumped when the schema changes
//                  .migration(new Migration())        // Migration to run instead of throwing an exception
//                  .deleteRealmIfMigrationNeeded()
                    .build());
        }
    }

    public static void setRealmConfiguration(RealmConfiguration realmConfiguration) {
        DbManager.realmConfiguration = realmConfiguration;
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm getRealm() {
        return realm;
    }

}

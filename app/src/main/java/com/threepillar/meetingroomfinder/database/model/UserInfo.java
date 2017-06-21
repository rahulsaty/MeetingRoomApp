package com.threepillar.meetingroomfinder.database.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class UserInfo extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String title;
    @Required
    private String email;
    @Required
    private String fromTime;
    @Required
    private String toTime;

}

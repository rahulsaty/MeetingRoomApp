package com.threepillar.meetingroomfinder.database;


import com.threepillar.meetingroomfinder.database.model.Student;
import com.threepillar.meetingroomfinder.database.model.University;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {Student.class, University.class})
public class DbModule {

}

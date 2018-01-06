package com.techmainstay.nilsyncdemo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Nilesh on 29-Dec-17.
 */

public class Person extends RealmObject {

    private String name;
    private String age;
    private int syncflag;

    public Person(){

    }

    public Person(String name,String age,int syncflag){

        this.age = age;
        this.name=name;
        this.syncflag=syncflag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSyncflag() {
        return syncflag;
    }

    public void setSyncflag(int syncflag) {
        this.syncflag = syncflag;
    }
}

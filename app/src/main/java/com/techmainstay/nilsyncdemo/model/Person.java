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
    private Dog dog;
    private RealmList<Cat> Cat;
    @Ignore
    private int tempReference;

    private long id;

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

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public RealmList<com.techmainstay.nilsyncdemo.model.Cat> getCat() {
        return Cat;
    }

    public void setCat(RealmList<com.techmainstay.nilsyncdemo.model.Cat> cat) {
        Cat = cat;
    }

    public int getTempReference() {
        return tempReference;
    }

    public void setTempReference(int tempReference) {
        this.tempReference = tempReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

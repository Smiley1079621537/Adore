package me.lemuel.blisslemuel.items.movie;

import io.realm.RealmObject;

/**
 * Created by lemuel on 2017/3/1.
 */

public  class AvatarsBean extends RealmObject {

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}

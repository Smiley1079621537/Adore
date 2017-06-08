package me.lemuel.adore.bean.movie;

/**
 * Created by lemuel on 2017/3/1.
 */


public  class CastsBean{
    private String alt;
    private AvatarsBean avatars;
    private String name;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public AvatarsBean getAvatars() {
        return avatars;
    }

    public void setAvatars(AvatarsBean avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

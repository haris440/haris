package kido.sparks.app.Model;

import java.util.Collection;

public class Viewchild {
    public Viewchild() { }

    String key;
    String status;
    String babyname;
    String babyage;
    String babyweight;
    String babygender;
    public Viewchild(String key, String status, String babyname, String babyage, String babyweight, String babygender) {
        this.key = key;
        this.status = status;
        this.babyname = babyname;
        this.babyage = babyage;
        this.babyweight = babyweight;
        this.babygender = babygender;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getBabyage() {
        return babyage;
    }

    public void setBabyage(String babyage) {
        this.babyage = babyage;
    }

    public String getBabyweight() {
        return babyweight;
    }

    public void setBabyweight(String babyweight) {
        this.babyweight = babyweight;
    }

    public String getBabygender() {
        return babygender;
    }

    public void setBabygender(String babygender) {
        this.babygender = babygender;
    }


}

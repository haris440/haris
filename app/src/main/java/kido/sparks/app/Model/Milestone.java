package kido.sparks.app.Model;

import java.io.Serializable;

public class Milestone implements Serializable {
    public Milestone(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    String text;
    boolean status;
    String extra;
    String url;
    boolean urlstatus;
    String name;

    public Milestone(String text, boolean status, String extra, String url, boolean urlstatus, String name,String key) {
        this.text = text;
        this.status = status;
        this.extra = extra;
        this.url = url;
        this.urlstatus = urlstatus;
        this.name = name;
        this.key= key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUrlstatus() {
        return urlstatus;
    }

    public void setUrlstatus(boolean urlstatus) {
        this.urlstatus = urlstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

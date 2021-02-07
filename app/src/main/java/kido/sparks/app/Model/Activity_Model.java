package kido.sparks.app.Model;

import java.io.Serializable;

public class Activity_Model implements Serializable {

   public Activity_Model(){}
    String name;
    String extra;
    String url;
    String text;
    boolean urlstatus;
    String imgurl;
    String type;
    String subtype;

    public Activity_Model(String name, String extra, String url, String text, boolean urlstatus, String imgurl, String type, String subtype) {
        this.name = name;
        this.extra = extra;
        this.url = url;
        this.text = text;
        this.urlstatus = urlstatus;
        this.imgurl = imgurl;
        this.type = type;
        this.subtype = subtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUrlstatus() {
        return urlstatus;
    }

    public void setUrlstatus(boolean urlstatus) {
        this.urlstatus = urlstatus;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}

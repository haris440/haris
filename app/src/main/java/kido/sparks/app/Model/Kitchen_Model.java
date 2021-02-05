package kido.sparks.app.Model;

import java.io.Serializable;

public class Kitchen_Model implements Serializable {

   public Kitchen_Model(){}
    String name;



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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    String extra;
    String url;
    String text;

    public Kitchen_Model(String name, String extra, String url, String text, boolean urlstatus, String imgurl) {
        this.name = name;
        this.extra = extra;
        this.url = url;
        this.text = text;
        this.urlstatus = urlstatus;
        this.imgurl = imgurl;
    }

    public boolean isUrlstatus() {
        return urlstatus;
    }

    public void setUrlstatus(boolean urlstatus) {
        this.urlstatus = urlstatus;
    }

    boolean urlstatus;
    String imgurl;
}

package kido.sparks.app.Model;

import java.io.Serializable;

public class VideoModel implements Serializable {
    public  VideoModel(){}
    String name;
    String url;
    String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public VideoModel(String name, String url, String detail, String extra) {
        this.name = name;
        this.url = url;
        this.detail = detail;
        this.extra = extra;
    }

    String extra;

}

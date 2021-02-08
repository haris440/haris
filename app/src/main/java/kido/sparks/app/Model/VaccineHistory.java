package kido.sparks.app.Model;

public class VaccineHistory {
    public  VaccineHistory(){}
    String name;
    String detail;
    String month;
    String key;
    String extra;

    public VaccineHistory(String name, String detail, String month, String key, String extra) {
        this.name = name;
        this.detail = detail;
        this.month = month;
        this.key = key;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

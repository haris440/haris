package kido.sparks.app.Model;

public class Milestone {
    public Milestone(){}

    public Milestone(String text, String status, String extra) {
        this.text = text;
        this.status = status;
        this.extra = extra;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    String text;
    String status;
    String extra;
}

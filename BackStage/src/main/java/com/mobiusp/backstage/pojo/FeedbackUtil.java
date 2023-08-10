package com.mobiusp.backstage.pojo;

import java.util.List;

public class FeedbackUtil {
    private List<FeedBack> unhandled;
    private List<FeedBack> handled;

    @Override
    public String toString() {
        return "FeedbackUtil{" +
                "unhandled=" + unhandled +
                ", handled=" + handled +
                '}';
    }

    public List<FeedBack> getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(List<FeedBack> unhandled) {
        this.unhandled = unhandled;
    }

    public List<FeedBack> getHandled() {
        return handled;
    }

    public void setHandled(List<FeedBack> handled) {
        this.handled = handled;
    }

    public FeedbackUtil(List<FeedBack> unhandled, List<FeedBack> handled) {
        this.unhandled = unhandled;
        this.handled = handled;
    }
}

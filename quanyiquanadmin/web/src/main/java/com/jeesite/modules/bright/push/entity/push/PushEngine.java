package com.jeesite.modules.bright.push.entity.push;

public class PushEngine {
    private String status;
    private String autoStart;
    private String autoStop;
    private String newStart;
    private String pushNumber;
    private String interval;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(String autoStart) {
        this.autoStart = autoStart;
    }

    public String getAutoStop() {
        return autoStop;
    }

    public void setAutoStop(String autoStop) {
        this.autoStop = autoStop;
    }

    public String getNewStart() {
        return newStart;
    }

    public void setNewStart(String newStart) {
        this.newStart = newStart;
    }

    public String getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(String pushNumber) {
        this.pushNumber = pushNumber;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}

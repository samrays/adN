package com.adnaira.naira_ad;

public class AddInfo {
    private String photo;
    private String target_url;
    private String description;
    private String ip;
    private String token;

    public AddInfo(String ip, String token) {
        this.ip = ip;
        this.token = token;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTarget_url() {
        return target_url;
    }

    public String getDescription() {
        return description;
    }

    public String getIp() {
        return ip;
    }

    public String getToken() {
        return token;
    }
}

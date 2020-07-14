package com.back.weins.services;

public interface BlogService {
    public void setLabel(String label);
    public void setBlog(Integer uid, Integer type, String post_day, String video, String imag, String label);
}

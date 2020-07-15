package com.back.weins.services;

public interface BlogService {
    public void setLabel(String label);
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           String imag, String label, String username, String useravatar);
}

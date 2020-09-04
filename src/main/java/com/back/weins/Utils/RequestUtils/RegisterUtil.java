package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RegisterUtil {
    Integer id;
    String name;
    String password;
    String phone;
    String birthday;
    String avatar;
    Integer sex;
    List<Integer> interests = new ArrayList<>();
}

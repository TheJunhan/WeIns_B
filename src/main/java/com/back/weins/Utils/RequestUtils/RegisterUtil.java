package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}

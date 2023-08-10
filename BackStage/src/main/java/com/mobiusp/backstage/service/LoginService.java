package com.mobiusp.backstage.service;

import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Objects;

@Service
public class LoginService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginService.class);

    public LoginService() {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public boolean checkLogin (Map<String, Object> mp) {
        if (! Objects.equals(mp.get("uname"),uname)) return false;
        if (! Objects.equals(mp.get("password"),password)) return false;
        return true;
    }
}

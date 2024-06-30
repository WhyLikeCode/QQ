package com.why.qqcommon;

import java.io.Serializable;

/**
 * @Author: 王浩雨
 */

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String m_userId; //用户Id/用户名
    private String m_passwd;  //用户密码

    public User(){}
    public User(String m_userId, String m_passwd) {
        this.m_userId = m_userId;
        this.m_passwd = m_passwd;
    }

    public String getM_userId() {
        return m_userId;
    }

    public void setM_userId(String m_userId) {
        this.m_userId = m_userId;
    }

    public String getM_passwd() {
        return m_passwd;
    }

    public void setM_passwd(String m_passwd) {
        this.m_passwd = m_passwd;
    }
}

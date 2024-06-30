package com.why.qqcommon;

import java.io.Serializable;

/**
 * @Author: 王浩雨
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String m_sender;    //发送者
    private String m_getter;    //接收者
    private String m_content;   //消息内容
    private String m_sendTime;  //发送时间
    private String m_mesType;   //消息的类型 (text，video，file，picture....

    public Message(){}

    public String getM_mesType() {
        return m_mesType;
    }

    public void setM_mesType(String m_mesType) {
        this.m_mesType = m_mesType;
    }

    public String getM_sender() {
        return m_sender;
    }

    public void setM_sender(String m_sender) {
        this.m_sender = m_sender;
    }

    public String getM_getter() {
        return m_getter;
    }

    public void setM_getter(String m_getter) {
        this.m_getter = m_getter;
    }

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public String getM_sendTime() {
        return m_sendTime;
    }

    public void setM_sendTime(String m_sendTime) {
        this.m_sendTime = m_sendTime;
    }
}
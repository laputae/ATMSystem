package com.itheima;

public class Account {
    private String cardId;
    private String userName;
    private String passWord;
    private double money;   //璐︽埛浣欓
    private double quotaMoney;  //姣忔鍙栭挶棰濆害

    public String getCardId() {
        return cardId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public double getMoney() {
        return money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}

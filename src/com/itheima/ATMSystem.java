package com.itheima;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class ATMSystem {
    public static void main(String[] args){
        ArrayList<Account>accounts=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        while (true) {
            System.out.println("===============黑马ATM系统===============");
            System.out.println("1、账户登录");
            System.out.println("2、账户开户");

            System.out.println("请您选择操作：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    //用户登录
                    break;

                case 2:
                    //用户账户开户
                    register(accounts, sc);
                    break;

                default:
                    System.out.println("您输入的操作命令不存在");
            }
        }
    }

    /**
     * 用户开户功能的实现
     * @param accounts 接收的账户集合
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== 系统开户操作===============");
        //创建一个账户对象，用与后期封装帐户信息
        Account account=new Account();
        String userName="";
        while (true) {
            //录入这个账户的信息，添加到账户对象
            System.out.println("请输入用户名：");
            userName=sc.next();

            account.setUserName(userName);
            System.out.println("请输入密码：");
            String passWord=sc.next();
            System.out.println("请再次输入密码：");
            String okPassword=sc.next();
            if(okPassword.equals(passWord)){
                //两次输入的密码相同，可以添加到账户对象
                account.setPassWord(okPassword);
                break;
            } else {
                System.out.println("对不起，您两次输入的密码不一致，请重新输入");
            }
        }

        System.out.println("请您输入账户当次限额：");
        double quotaMoney=sc.nextDouble();
        account.setQuotaMoney(quotaMoney);

        //生成一个与其他账号不重复的八位账号（独立成方法）
        String cardId=getRandomCardId(accounts);
        //把账户对象添加到账户集合
        account.setCardId(cardId);
        accounts.add(account);
        System.out.println("恭喜您，"+userName+"先生/女士，您开户成功，您的卡号是："+cardId+"请妥善保管卡号");
    }

    /**
     * 为账户生成与其他账户卡号不同的八位随机账号
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r=new Random();
        while (true) {
            String cardId="";
            for (int i = 0; i < 8; i++) {
                cardId+=r.nextInt(10);
            }

            //判断这个八位的账号与其他账户的账号是否相同
            Account acc=getAccountByCardId(cardId,accounts);
            if(acc==null){
                return cardId;
            }
        }
    }
    public static Account getAccountByCardId(String cardId, ArrayList<Account> accounts){
        for(int i=0; i<accounts.size(); i++){
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;
    }
}

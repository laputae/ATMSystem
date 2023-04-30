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
                    login(accounts,sc);
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
     * 登录功能
     * @param accounts 全部账户的集合
     * @param sc 扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===============系统登陆操作===============");
        //判断账户集合中是否存在账户，如果不存在账户，不能进行登陆操作
        if(accounts.size()==0){
            System.out.printf("对比起，当前系统中无账户，请先开通账户，再来登录");
            return;
        }

        while (true) {
            //登陆操作
            System.out.println("请您输入卡号：");
            String cardId=sc.next();
            //根据卡号判断账户集合中是否存在账户对象
            Account acc=getAccountByCardId(cardId,accounts);
            if(acc!=null){
                while (true) {
                    System.out.println("请您输入密码：");
                    String passWord=sc.next();
                    //判断当前用户的密码与输入的密码是否一致
                    if(acc.getPassWord().equals(passWord)){
                        //登录成功
                        System.out.println("恭喜您，"+acc.getUserName()+"先生/女士进入系统，您的卡号是："+acc.getCardId());
                        //查询，转账，取款
                        showUserCommand(sc);
                        //break;
                    } else {
                        System.out.println("您输入的密码有误");
                    }
                }
            } else {
                System.out.println("对不起，系统中不存在该卡号");
            }
        }
    }

    private static void showUserCommand(Scanner sc) {
        while (true) {
            System.out.println("================用户操作页================");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、退出");
            System.out.println("7、注销账户");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command) {
                case 1:
                    //查询
                    break;
                case 2:
                    //存款
                    break;
                case 3:
                    //取款
                    break;
                case 4:
                    //转账
                    break;
                case 5:
                    //修改密码
                    break;
                case 6:
                    //退出
                    break;
                case 7:
                    //注销账户
                    break;
                default:
                    System.out.println("您输入的操作不正确");
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
        //录入这个账户的信息，添加到账户对象
        System.out.println("请输入用户名：");
        userName=sc.next();
        account.setUserName(userName);
        while (true) {
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

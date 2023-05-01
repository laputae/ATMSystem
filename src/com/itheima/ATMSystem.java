package com.itheima;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===============黑马ATM系统===============");
            System.out.println("1、账户登录");
            System.out.println("2、账户开户");

            System.out.println("请您选择操作：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //用户登录
                    login(accounts, sc);
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
     * @param sc       扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===============系统登陆操作===============");
        //判断账户集合中是否存在账户，如果不存在账户，不能进行登陆操作
        if (accounts.size() == 0) {
            System.out.print("对不起，当前系统中无账户，请先开通账户，再来登录");
            return;
        }

        while (true) {
            //登陆操作
            System.out.println("请您输入卡号：");
            String cardId = sc.next();
            //根据卡号判断账户集合中是否存在账户对象
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc != null) {
                while (true) {
                    System.out.println("请您输入密码：");
                    String passWord = sc.next();
                    //判断当前用户的密码与输入的密码是否一致
                    if (acc.getPassWord().equals(passWord)) {
                        //登录成功
                        System.out.println("恭喜您，" + acc.getUserName() + "先生/女士进入系统，您的卡号是：" + acc.getCardId());
                        //查询，转账，取款
                        showUserCommand(sc, acc, accounts);
                        return;
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

    private static void showUserCommand(Scanner sc, Account acc, ArrayList<Account> accounts) {
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
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询(展示用户信息)
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc, sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc, sc);
                    break;
                case 4:
                    //转账
                    transferMoney(acc, sc, accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassWord(sc, acc);
                    return;
                case 6:
                    //退出
                    System.out.println("退出成功，欢迎下次光临");
                    return;    //停止当前方法
                case 7:
                    //注销账户
                    if (deleteAccount(acc, sc, accounts)) {
                        //销户成功，回到首页
                        return;
                    } else {
                        break;
                    }
                default:
                    System.out.println("您输入的操作不正确");
            }
        }
    }

    /**
     * 销户功能
     *
     * @param acc
     * @param sc
     * @param accounts
     */
    private static boolean deleteAccount(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("请确认是否销户，请输入y或n。y(销户), n(不销户)");
        String rs = sc.next();
        if (rs.equals("y")) {//销户
            if (acc.getMoney() > 0) {
                System.out.println("您的账户还有钱，无法销户");
            } else {
                accounts.remove(acc);
                System.out.println("您的账户销户完成");
            }
            return true;
        }
        System.out.println("您没有销户");
        return false;
    }

    /**
     * 修改账户密码
     *
     * @param sc  扫描器
     * @param acc 当前账户对象
     */
    private static void updatePassWord(Scanner sc, Account acc) {
        System.out.println("==================用户密码修改==================");
        while (true) {
            System.out.println("请您输入当前密码：");
            String passWord = sc.next();
            // 判断密码是否正确
            if (acc.getPassWord().equals(passWord)) {
                while (true) {
                    // 密码正确，输入新密码
                    System.out.println("请您输入新密码;");
                    String newPassWord = sc.next();

                    System.out.println("请您再次输入新密码;");
                    String okPassWord = sc.next();

                    if (newPassWord.equals(okPassWord)) {
                        // 两次密码一致，可以修改
                        acc.setPassWord(okPassWord);
                        System.out.println("恭喜您，密码修改成功");
                        return;
                    } else {
                        System.out.println("您输入的两次密码不一致，请重新输入");
                    }
                }
            } else {
                System.out.println("您输入的密码不正确");
            }
        }
    }

    /**
     * 转账
     *
     * @param acc      自己的账户对象
     * @param sc       扫描器
     * @param accounts 全部账户的集合
     */
    private static void transferMoney(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("=======================用户转账操作=======================");
        // 1、判断是否有两个或两个以上账户
        if (accounts.size() < 2) {
            System.out.println("当前系统不足两个账户，不能转账，请去开户");
            return;
        }

        // 2、判断自己的账户是否有钱
        if (acc.getMoney() == 0) {
            System.out.println("您的帐户余额为0，无法转帐");
            return;
        }

        while (true) {
            // 3、开始转帐
            System.out.println("请输入对方的卡号");
            String cardId = sc.next();
            // 不能给自己转账
            if (cardId.equals(acc.getCardId())) {
                System.out.println("您不可以给自己转账");
                continue;   //进入下一次循环
            }
            Account account = getAccountByCardId(cardId, accounts);
            if (account == null) {
                System.out.println("对不起，您输入的账号不存在");
            } else {
                //对方账户存在
                String userName = account.getUserName();
                String tipName = "*" + userName.substring(1);
                System.out.println("请您输入"+tipName+"的姓氏");
                String preName = sc.next();

                // 认证姓氏是否正确
                if(userName.startsWith(preName)){
                    while (true) {
                        // 认证通过
                        System.out.println("请您输入转帐金额：");
                        double money = sc.nextDouble();
                        // 判断余额是否足够
                        if(money>=acc.getMoney()){
                            System.out.println("您的余额不足，最多可以转账："+acc.getMoney());
                        } else {
                            // 余额足够，可以转账
                            acc.setMoney(acc.getMoney()-money);
                            account.setMoney(account.getMoney()+money);
                            System.out.println("转账成功，您的账户还剩余："+acc.getMoney()+"元");
                            return;
                        }
                    }
                } else {
                    System.out.println("您输入的信息有误");
                }
            }
        }
    }

    /**
     * 取钱功能
     * @param acc 账户对象
     * @param sc  扫描器
     */
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("===========================用户取钱操作===========================");
        // 1、判断是否足够100元
        if (acc.getMoney() < 100) {
            System.out.println("对不起，当前账户不够100元，不能取钱");
            return;
        }

        // 2、提示用户输入取钱金额
        while (true) {
            System.out.println("请您输入取钱金额：");
            double money = sc.nextDouble();

            // 3、判断取钱金额是否超过当次限额
            if (money > acc.getQuotaMoney()) {
                System.out.println("对不起，您当前的取款金额超过当次限额，每次最多可取：" + acc.getQuotaMoney());
            } else {
                // 没有超过当次限额
                // 4、是否超过账户总余额
                if (money > acc.getMoney()) {
                    System.out.println("余额不足，您目前账户总余额是：" + acc.getMoney());
                } else {
                    System.out.println("恭喜您，取钱" + money + "元，成功！");
                    // 更新余额
                    acc.setMoney(acc.getMoney() - money);
                    // 取钱结束
                    showAccount(acc);
                    return;
                }
            }
        }
    }

    /**
     * 存钱
     * @param acc 当前账户对象
     * @param sc  扫描器
     */
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("===========================用户存钱操作===========================");
        System.out.println("请您输入存钱金额：");
        double money = sc.nextDouble();
        acc.setMoney(acc.getMoney() + money); //原来账户的钱加上现在存进去的钱
        //由于Account对象是引用类型，在修改acc的金额时修改的是原来存在的acc，不用把修改后的acc再加进accounts集合
        System.out.println("恭喜您存钱成功，当前账户信息如下：");
        showAccount(acc);
    }

    /**
     * 展示账户信息
     * @param acc 账户对象
     */
    private static void showAccount(Account acc) {
        System.out.println("===================当前账户信息如下===================");
        System.out.println("卡号：" + acc.getCardId());
        System.out.println("姓名：" + acc.getUserName());
        System.out.println("余额：" + acc.getMoney());
        System.out.println("限额：" + acc.getQuotaMoney());
    }

    /**
     * 用户开户功能的实现
     * @param accounts 接收的账户集合
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== 系统开户操作===============");
        //创建一个账户对象，用与后期封装帐户信息
        Account account = new Account();
        String userName = "";
        //录入这个账户的信息，添加到账户对象
        System.out.println("请输入用户名：");
        userName = sc.next();
        account.setUserName(userName);
        while (true) {
            System.out.println("请输入密码：");
            String passWord = sc.next();
            System.out.println("请再次输入密码：");
            String okPassword = sc.next();
            if (okPassword.equals(passWord)) {
                //两次输入的密码相同，可以添加到账户对象
                account.setPassWord(okPassword);
                break;
            } else {
                System.out.println("对不起，您两次输入的密码不一致，请重新输入");
            }
        }

        System.out.println("请您输入账户当次限额：");
        double quotaMoney = sc.nextDouble();
        account.setQuotaMoney(quotaMoney);

        //生成一个与其他账号不重复的八位账号（独立成方法）
        String cardId = getRandomCardId(accounts);
        //把账户对象添加到账户集合
        account.setCardId(cardId);
        accounts.add(account);
        System.out.println("恭喜您，" + userName + "先生/女士，您开户成功，您的卡号是：" + cardId + "请妥善保管卡号");
    }

    /**
     * 为账户生成与其他账户卡号不同的八位随机账号
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }

            //判断这个八位的账号与其他账户的账号是否相同
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc == null) {
                return cardId;
            }
        }
    }

    /**
     * 通过卡号查找账户对象
     *
     * @param cardId
     * @param accounts
     * @return
     */
    public static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;
    }
}

package com.queqianme.hpt.utils;

/**
 * 服务器URL地址
 */
public class HttpURL {
    /**
     * 服务器IP
     */
    public static  String IP = "http://192.168.1.140:8080";

    public static  String IP_NAME = "/sl/app/v2";


    //==============1.账号管理================
    /**
     * 1.1 注册接口
     */
    public static final String register = IP + IP_NAME + "/user/register";
    /**
     * 1.1.1 获取手机注册验证码
     */
    public static final String getRegisterCaptcha = IP + IP_NAME + "/user/getRegisterCaptcha";
    /**
     * 1.2 登录
     */
    public static final String login = IP + IP_NAME + "/user/login";
    /**
     * 1.3 注销
     */
    public static final String logout = IP + IP_NAME + "/user/logout";
    /**
     * 1.4 获取个人信息
     */
    public static final String getUserInfo = IP + IP_NAME + "/user/getUserInfo";
    /**
     * 1.5 修改密码
     */
    public static final String editPassword = IP + IP_NAME + "/user/editPassword";
    /**
     * 1.6 修改昵称
     */
    public static final String editNickname = IP + IP_NAME + "/user/editNickname";
    /**
     * 1.7 重置登陆密码
     */
    public static final String resetPassword = IP + IP_NAME + "/user/resetPassword";
    /**
     * 1.7.1 获取重置登陆密码验证码
     */
    public static final String getResetPasswordCaptcha = IP + IP_NAME + "/user/getResetPasswordCaptcha";
    /**
     * 1.7.2 验证重置登陆密码验证码
     */
    public static final String checkResetPasswordCaptcha = IP + IP_NAME + "/user/checkResetPasswordCaptcha";

    //==============2.认证信息================

    /**
     * 2.1 获取用户认证信息
     */
    public static final String getUserAuthInfo = IP + IP_NAME + "/auth/getUserAuthInfo";
    /**
     * 2.2 实名认证
     */
    public static final String authIdentityInfo = IP + IP_NAME + "/auth/authIdentityInfo";
    /**
     * 2.3 获取实名认证信息
     */
    public static final String getIdentityInfo = IP + IP_NAME + "/auth/getIdentityInfo";
    /**
     * 2.4 上传身份证相片
     */
    public static final String uploadIdentity = IP + IP_NAME + "/auth/uploadIdentity";
    /**
     * 2.5 认证相片信息
     */
    public static final String authPhotoInfo = IP + IP_NAME + "/auth/authPhotoInfo";

    /**
     * 2.6 获取相片认证信息
     */
    public static final String getPhotoInfo = IP + IP_NAME + "/auth/getPhotoInfo";
    /**
     * 2.7 认证个人信息
     */
    public static final String authPersonalInfo = IP + IP_NAME + "/auth/authPersonalInfo";
    /**
     * 2.8 获取个人认证信息
     */
    public static final String getAddressInfo = IP + IP_NAME + "/auth/getAddressInfo";
    /**
     * 2.9 认证学校/公司信息
     */
    public static final String authCareerInfo = IP + IP_NAME + "/auth/authCareerInfo";
    /**
     * 2.10 获取学校/公司信息
     */
    public static final String getCareerInfo = IP + IP_NAME + "/auth/getCareerInfo";
    /**
     * 2.11 添加银行卡
     */
    public static final String addBankCard = IP + IP_NAME + "/auth/addBankCard";
    /**
     * 2.11.1 验证添加银行卡验证码
     */
    public static final String checkBankCardCaptcha = IP + IP_NAME + "/auth/checkBankCardCaptcha";
    /**
     * 2.11.2 获取银行卡类型
     */
    public static final String getBankType = IP + IP_NAME + "/auth/getBankType";
    /**
     * 2.12 删除银行卡
     */
    public static final String deleteBankCard = IP + IP_NAME + "/auth/deleteBankCard";
    /**
     * 2.13 设置默认银行卡
     */
    public static final String defaultBankCard = IP + IP_NAME + "/auth/defaultBankCard";
    /**
     * 2.14获取银行卡列表
     */
    public static final String getBankCardList = IP + IP_NAME + "/auth/getBankCardList";

    //==============3.上传信息================

    /**
     * 3.1 上传头像图片
     */
    public static final String uploadIcon = IP + IP_NAME + "/upload/icon";
    /**
     * 3.2 上传GPS位置信息
     */
    public static final String uploadGps = IP + IP_NAME + "/upload/gps";
    /**
     * 3.3 上传电话簿
     */
    public static final String uploadTelbook = IP + IP_NAME + "/upload/telbook";
    /**
     * 3.4 上传通话记录
     */
    public static final String uploadCall = IP + IP_NAME + "/upload/call";
    /**
     * 3.5 获取银行卡列表
     */
    public static final String uploadSMS = IP + IP_NAME + "/upload/sms";

    //==============4.消息中心================

    /**
     * 4.1 消息列表
     */
    public static final String getMessageList = IP + IP_NAME + "/message/getMessageList";
    /**
     * 4.2 修改消息(设置消息已读)
     */
    public static final String editMessage = IP + IP_NAME + "/message/editMessage";
    /**
     * 4.3 删除消息
     */
    public static final String deleteMessage = IP + IP_NAME + "/message/deleteMessage";
    /**
     * 4.4 详细消息内容(会同时设置该消息已读)
     */
    public static final String loadMessage = IP + IP_NAME + "/message/loadMessage";

    //==============5.借条信息================

    /**
     * 5.1 获取添加借条信息
     */
    public static final String getAddBillInfo = IP + IP_NAME + "/loan/getAddBillInfo";
    /**
     * 5.2 添加借条信息
     */
    public static final String addBillInfo = IP + IP_NAME + "/loan/addBillInfo";
    /**
     * 5.2.1 获取日利息/每期还款金额
     */
    public static final String getRefundMoney = IP + IP_NAME + "/loan/getRefundMoney";
    /**
     * 5.3 发布借条
     */
    public static final String releaseBill = IP + IP_NAME + "/loan/releaseBill";
    /**
     * 5.4 取消发布借条
     */
    public static final String cancelBill = IP + IP_NAME + "/loan/cancelBill";
    /**
     * 5.5 获取借条列表
     */
    public static final String getBillList = IP + IP_NAME + "/loan/getBillList";
    /**
     * 5.5.1 获取应还款信息
     */
    public static final String getBillRefundInfo = IP + IP_NAME + "/loan/getBillRefundInfo";
    /**
     * 5.6 获取借条详细信息
     */
    public static final String getBillInfo = IP + IP_NAME + "/loan/getBillInfo";
    /**
     * 5.7 获取还款支付方式
     */
    public static final String getPayList = IP + IP_NAME + "/loan/getPayList";
    /**
     * 5.8 获取快钱一键支付还款信息
     */
    public static final String getKuaiOneKeyRepayInfo = IP + IP_NAME + "/loan/getKuaiOneKeyRepayInfo";
    /**
     * 5.9 快钱一键支付还款
     */
    public static final String getKuaiOneKeyRepay = IP + IP_NAME + "/loan/getKuaiOneKeyRepay";
    /**
     * 5.10 快钱一键支付结果
     */
    public static final String kuaiOneKeyRepay = IP + IP_NAME + "/loan/kuaiOneKeyRepay";

    //==============6.投资信息================
    /**
     * 6.1 投资首页
     */
    public static final String investList = IP + IP_NAME + "/invest/investList";
    /**
     * 6.2 借款详情
     */
    public static final String getBorrowingInfo = IP + IP_NAME + "/invest/getBorrowingInfo";
    /**
     * 6.3 获取借款人信息
     */
    public static final String getBorrowerInfo = IP + IP_NAME + "/invest/getBorrowerInfo";
    /**
     * 6.4 获取快钱一键支付投资准备信息
     */
    public static final String getKuaiOneKeyInvestPrepareInfo = IP + IP_NAME + "/invest/getKuaiOneKeyInvestPrepareInfo";
    /**
     * 6.5 获取快钱一键支付投资信息
     */
    public static final String getKuaiOneKeyInvestInfo = IP + IP_NAME + "/invest/getKuaiOneKeyInvestInfo";
    /**
     * 6.6 快钱一键支付(投资)
     */
    public static final String kuaiOneKeyInvest = IP + IP_NAME + "/invest/kuaiOneKeyInvest";
    /**
     * 6.7 我的投资
     */
    public static final String myInvest = IP + IP_NAME + "/invest/myInvest";
    /**
     * 6.8 我的投资账单信息
     */
    public static final String myInvestBillInfo = IP + IP_NAME + "/invest/myInvestBillInfo";
    /**
     * 6.9 我的投资借款人信息
     */
    public static final String myInvestBorrowerInfo = IP + IP_NAME + "/invest/myInvestBorrowerInfo";
    /**
     * 6.10 我的投资借款人信息(逾期)
     */
//    public static final String myInvestBorrowerInfo = IP + IP_NAME + "/invest/myInvestBorrowerInfo";
    /**
     * 6.11 获取标签列表
     */
    public static final String getLabelList = IP + IP_NAME + "/invest/getLabelList";
    /**
     * 6.12 添加标签
     */
    public static final String addLabel = IP + IP_NAME + "/invest/addLabel";

    //==============7.聊天信息================


    //==============8.意见反馈================

    /**
     * 8.1 提交意见反馈
     */
    public static final String submitFeedback = IP + IP_NAME + "/feedback/submitFeedback";

    //==============9.公共接口================

    /**
     * 9.1 提交意见反馈
     */
    public static final String getAndVersion = IP + IP_NAME + "/pub/getAndVersion";

}

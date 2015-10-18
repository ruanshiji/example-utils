package com.t2t.examples.mail;

import java.io.File;

public class MainClient {
	
	public static void main(String[] args) throws Exception {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("jruner@163.com");
		mailInfo.setPassword("dp1rtf73.14");// 您的邮箱密码
		mailInfo.setFromAddress("jruner@163.com");
		mailInfo.setToAddress("jrunner@126.com");
		mailInfo.setSubject("设置邮箱标题 工作汇报");
		mailInfo.setContent("hello world");

		mailInfo.setAttachFiles(new FileList().add(new File("c:\\test.txt")).add(new File("c:\\测试.js")));

		// 这个类主要来发送邮件
		MailSenderProcesser sms = new MailSenderProcesser();
		sms.sendMail(mailInfo, true);
		System.out.println("发送成功 !");
	}

}

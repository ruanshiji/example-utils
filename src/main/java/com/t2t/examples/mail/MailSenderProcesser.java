package com.t2t.examples.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 发送器
 */
public class MailSenderProcesser {

	/**
	 * 获取Session
	 */
	public Session getSession(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();

		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session session = Session.getDefaultInstance(pro, authenticator);
		return session;
	}

	/**
	 * 设置附件
	 */
	public void setAttachFile(Message message, BodyPart part, MailSenderInfo mailInfo) throws MessagingException, UnsupportedEncodingException {
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part);// 设置邮件消息的主要内容

		for (int i = 0; i < mailInfo.getAttachFiles().size(); i++) {
			File attachFile = (File) mailInfo.getAttachFiles().get(i);
			// 添加附件
			BodyPart attachPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachFile);
			// 添加附件的内容
			attachPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题
			// 这里很重要，通过下面的编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			attachPart.setFileName(javax.mail.internet.MimeUtility.encodeWord(attachFile.getName())); // 中文附件名先编码
			multipart.addBodyPart(attachPart);
		}

		// 将MiniMultipart对象设置为邮件内容
		message.setContent(multipart);
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendMail(MailSenderInfo mailInfo, boolean isHtml) {
		Session session = getSession(mailInfo);
		try {
			// 根据session创建一个邮件消息
			Message message = new MimeMessage(session);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			message.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			message.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			message.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			message.setSentDate(new Date());

			// 设置内容
			BodyPart part = new MimeBodyPart();
			if (isHtml) {
				part.setContent(mailInfo.getContent(), "text/html; charset=UTF-8");
			}
			if (!isHtml) {
				part.setText(mailInfo.getContent());
			}

			// 设置附件
			setAttachFile(message, part, mailInfo);// 添加附件

			// 发送邮件
			Transport.send(message);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}

class MyAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
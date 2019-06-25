package com.hawk.demo.util;

import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by Lenovo on 2019-06-25.
 */

@Component
public class MailUtil {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    private  String fromString="youyingke@qq.com";

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    public  void sendSimpleEmail(String to,String subject,String content)
    {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setFrom(fromString);
        try{
            javaMailSender.send(mailMessage);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**

     * 发送html邮件

     * @param to

     * @param subject

     * @param content

     */


    public  void sendHtmlMail(String to, String subject, String content) {

        MimeMessage message = javaMailSender.createMimeMessage();



        try {

            //true表示需要创建一个multipart message

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromString);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(content, true);



            javaMailSender.send(message);

            logger.info("html邮件发送成功");

        } catch (MessagingException e) {

            logger.error("发送html邮件时发生异常！", e);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**

     * 发送带附件的邮件

     * @param to

     * @param subject

     * @param content

     * @param filePath

     */

    public void sendAttachmentsMail(String to, String subject, String content, String filePath){

        MimeMessage message = javaMailSender.createMimeMessage();



        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromString);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(content, true);



            FileSystemResource file = new FileSystemResource(new File(filePath));

            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));

            helper.addAttachment(fileName, file);

            //helper.addAttachment("test"+fileName, file);



           javaMailSender.send(message);

            logger.info("带附件的邮件已经发送。");

        } catch (MessagingException e) {

            logger.error("发送带附件的邮件时发生异常！", e);

        }

    }





    /**

     * 发送正文中有静态资源（图片）的邮件

     * @param to

     * @param subject

     * @param content

     * @param rscPath

     * @param rscId

     */

    public  void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){

        MimeMessage message = javaMailSender.createMimeMessage();



        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromString);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(content, true);



            FileSystemResource res = new FileSystemResource(new File(rscPath));

            helper.addInline(rscId, res);



           javaMailSender.send(message);

            logger.info("嵌入静态资源的邮件已经发送。");

        } catch (MessagingException e) {

            logger.error("发送嵌入静态资源的邮件时发生异常！", e);

        }

    }


}

package org.bakka.userservice.Services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;




@Service
public class EmailService {

    static String expediteur="sekou.abgconsulting@yopmail.com";

    @Autowired
    public JavaMailSender mailSender;
    //mail sans PJ
    public String sendSimpleEmail(String to,String object,String text) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(object);
        message.setText(text);
        // Send Message!
        mailSender.send(message);
        return "Email Sent!";
    }



    //send email to Back Immo
    public String sendSimpleEmailToBo(String object,String text) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(expediteur);
        message.setSubject(object);
        message.setText(text);
        mailSender.send(message);
        return "Email Sent!";
        // Send Message!
    }

/*

    //end send email

    //mail avec PJ
    public String sendAttachmentEmail(String to,String object,String text,String path1,String path2) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(to);
        helper.setSubject(object);

        helper.setText(text);

        // Attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment("Txt file", file1);

        // Attachment 2
        FileSystemResource file2 = new FileSystemResource(new File(path2));
        helper.addAttachment("Readme", file2);

        mailSender.send(message);

        return "Email Sent!";
    }
    @Autowired
    private  JavaMailSender javaMailSender;

    */
/**
     * Cette methode permet d'envoyer un mail avec une piece jointe
     * @param panier
     * @param toEmail
     * @param body
     * @param subject
     * @param attachement
     * @throws MessagingException
     *//*

    public void sendMailWithAttachement(Panier panier, String toEmail,
                                        String body,
                                        String subject,
                                        String attachement
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(panier.getClient().getEmail());
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachement));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
        javaMailSender.send(mimeMessage);
        System.out.println("Mail envoyé");

    }
*/




}


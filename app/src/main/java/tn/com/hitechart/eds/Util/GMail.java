package tn.com.hitechart.eds.Util;

/**
 * Created by BARA' on 08/01/2017.
 */

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMail {

    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";

    String fromEmail;           // FromEmail
    String fromPassword;        // PassworddFrom
    //String toEmail;             // To Email
    List<String> toEmailList;           // To Email List

    String emailSubject;        // Email Subject
    String emailBody;           // Email Body

    String nameFileRp;          // Attached File rp
    String nameFileDs;          // Attached File ds
    String pdfFilename;         // Attached File pdf

    BodyPart messageBodyPart;   // Create the message part

    BodyPart messageFilePart1;  // Create message part for attaching file
    BodyPart messageFilePart2;  //                ..          ..
    BodyPart messageFilePart3;  //                ..          ..

    Multipart multipart;        // Create a multipart message


    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;


    public GMail() {

    }

    public GMail(String fromEmail, String fromPassword, List toEmailList,
                 String emailSubject, String emailBody, String nameFileRp, String nameFileDs, String pdfFilename) {

        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.nameFileRp = nameFileRp;
        this.nameFileDs = nameFileDs;
        this.pdfFilename = pdfFilename;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        Log.i("GMail", "Mail server properties set.");
    }


    public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        for (String toEmail : toEmailList) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }


        emailMessage.setSubject(emailSubject);

        // 3. Create MimeBodyPart object and set your message text
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(emailBody);

        // 4. Create new MimeBodyPart object and set DataHandler object to this
        // object - for attachment
        messageFilePart1 = new MimeBodyPart();
        messageFilePart2 = new MimeBodyPart();
        messageFilePart3 = new MimeBodyPart();


        String fileloc1 = "/data/user/0/tn.com.hitechart.eds/files/" + nameFileRp;
        String fileloc2 = "/data/user/0/tn.com.hitechart.eds/files/" + nameFileDs;
        String fileloc3 = "/data/user/0/tn.com.hitechart.eds/files/" + pdfFilename;


        DataSource source1 = new FileDataSource(fileloc1);
        DataSource source2 = new FileDataSource(fileloc2);
        DataSource source3 = new FileDataSource(fileloc3);


        messageFilePart1.setDataHandler(new DataHandler(source1));
        messageFilePart2.setDataHandler(new DataHandler(source2));
        messageFilePart3.setDataHandler(new DataHandler(source3));

        messageFilePart1.setFileName(nameFileRp);
        messageFilePart2.setFileName(nameFileDs);
        messageFilePart3.setFileName(pdfFilename);

        // 5. Create Multipart object and add MimeBodyPart objects to this
        // object

        multipart = new MimeMultipart();
// Set the body and file
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(messageFilePart1);
        multipart.addBodyPart(messageFilePart2);
        multipart.addBodyPart(messageFilePart3);



// 6. set the multiplart object to the message object

        emailMessage.setContent(multipart);
        Log.i("GMail", "Email Message created.");
        return emailMessage;
    }

    public void sendEmail() throws AddressException, MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        Log.i("GMail", "allrecipients: " + emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GMail", "Email sent successfully.");
    }

}
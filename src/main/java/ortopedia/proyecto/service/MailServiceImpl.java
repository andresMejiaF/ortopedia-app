package ortopedia.proyecto.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MailServiceImpl implements MailService{

    @Inject
    JavaMailSender javaMailSender;

    @Override
    public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
        System.out.println("Email enviado");
    }
}

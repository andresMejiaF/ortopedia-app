package ortopedia.proyecto.service;

public interface MailService {

    void sendMail(String from, String to, String subject, String body);
}

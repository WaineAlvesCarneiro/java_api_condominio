package com.wa.condominio.services.email;

import com.wa.condominio.model.email.EmailRemetente;
import com.wa.condominio.repositories.email.EmailRemetenteRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    private final EmailRemetenteRepository remetenteRepository;

    public EmailService(EmailRemetenteRepository remetenteRepository) {
        this.remetenteRepository = remetenteRepository;
    }

    public void enviarEmail(String para, String assunto, String mensagem) {
        EmailRemetente remetente = remetenteRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum remetente configurado no banco"));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(remetente.getHost());
        mailSender.setPort(remetente.getPort());
        mailSender.setUsername(remetente.getUsername());
        mailSender.setPassword(remetente.getSenha());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(remetente.getUsername());
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(mensagem, false); // false = texto simples (true = HTML)

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }
}


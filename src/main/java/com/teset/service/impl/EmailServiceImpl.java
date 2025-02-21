package com.teset.service.impl;

import com.teset.service.IEmailService;
import com.teset.utils.enums.PropositoCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    // Configuraciones reutilizables
    private static final String APP_NAME = "Teset";
    private static final String SUPPORT_EMAIL = "contacto@teset.com.ar";
    private static final String HELP_URL = "https://teset.com.ar/";


    // Plantilla HTML única
    private static final String EMAIL_TEMPLATE = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                .container { max-width: 600px; margin: 20px auto; padding: 20px; }
                .code { font-size: 24px; margin: 20px 0; padding: 10px; background: #f5f5f5; }
                .footer { margin-top: 30px; font-size: 12px; color: #666; }
            </style>
        </head>
        <body>
            <div class="container">
                <p>Hola,</p>
                <p>%s</p>
                <pre class="code">%s</pre>
                <p>Válido por: %s</p>
                <p><a href="%s">Centro de ayuda</a></p>
                <div class="footer">
                    <p>Este es un email automático, por favor no respondas directamente.</p>
                </div>
            </div>
        </body>
        </html>
        """;

    @Override
    public void generarCorreo(String destino, PropositoCode proposito, String codigo) {
        validateEmail(destino);

        EmailContent emailContent = buildEmailContent(proposito, codigo);
        enviar(destino, emailContent.subject(), emailContent.html(), emailContent.text());
    }

    private EmailContent buildEmailContent(PropositoCode proposito, String codigo) {
        return switch (proposito) {
            case LOGIN -> new EmailContent(
                    "[Seguridad] Código de acceso",
                    String.format("Usa este código para iniciar sesión: %s", codigo),
                    String.format(EMAIL_TEMPLATE,
                            "Tu código de verificación para iniciar sesión es:",
                            codigo,
                            "10 minutos",
                            HELP_URL)
            );

            case REST_PASSWORD -> new EmailContent(
                    "[Seguridad] Restablecer contraseña",
                    String.format("Usa este código para restablecer tu contraseña: %s", codigo),
                    String.format(EMAIL_TEMPLATE,
                            "Tu código para restablecer la contraseña es:",
                            codigo,
                            "2 minutos",
                            HELP_URL)
            );

            case REGISTER -> new EmailContent(
                    "[Verificación] Completa tu registro",
                    String.format("Tu código de verificación es: %s", codigo),
                    String.format(EMAIL_TEMPLATE,
                            "Para completar tu registro usa el código:",
                            codigo,
                            "10 minutos",
                            HELP_URL)
            );

            default -> throw new IllegalArgumentException("Propósito no válido");
        };
    }

    public void enviar(String to, String asunto, String htmlText, String textoPlano) {
        try {
            validateEmail(to);
            String emailFrom = System.getenv("EMAIL_SENDER");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Cabeceras principales
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(asunto);
            helper.setText(textoPlano, htmlText);
            helper.setReplyTo(SUPPORT_EMAIL);

            // Cabeceras anti-spam
            message.addHeader("List-Unsubscribe", "<mailto:" + SUPPORT_EMAIL + ">");
            message.addHeader("Precedence", "bulk");
            message.addHeader("X-Auto-Response-Suppress", "All");
            message.addHeader("Auto-Submitted", "auto-generated");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando email a: " + to, e);
        }
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }

    private record EmailContent(String subject, String text, String html) {}
}
package logic;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Servicio para el envío de correos electrónicos
 * Utiliza JavaMail API con SMTP de Gmail
 */
public class EmailService {
    
    /**
     * Envía un email de notificación cuando una solicitud de materia es aprobada
     * 
     * @param destinatario Email del usuario que solicitó la materia
     * @param nombreUsuario Nombre del usuario solicitante
     * @param nombreMateria Nombre de la materia aprobada
     * @param nombreCarrera Nombre de la carrera asociada
     * @return true si el email se envió correctamente, false en caso contrario
     */
    public static boolean enviarEmailAprobacion(String destinatario, String nombreUsuario, 
                                                String nombreMateria, String nombreCarrera) {
        
        String asunto = "Solicitud de Materia Aprobada - " + EmailConfig.APP_NAME;
        
        String cuerpoHtml = construirEmailAprobacion(nombreUsuario, nombreMateria, nombreCarrera);
        
        return enviarEmail(destinatario, asunto, cuerpoHtml);
    }
    
    /**
     * Envía un email de notificación cuando una solicitud de materia es rechazada
     * 
     * @param destinatario Email del usuario que solicitó la materia
     * @param nombreUsuario Nombre del usuario solicitante
     * @param nombreMateria Nombre de la materia rechazada
     * @param nombreCarrera Nombre de la carrera asociada
     * @param motivoRechazo Motivo por el cual se rechazó la solicitud
     * @return true si el email se envió correctamente, false en caso contrario
     */
    public static boolean enviarEmailRechazo(String destinatario, String nombreUsuario, 
                                            String nombreMateria, String nombreCarrera, 
                                            String motivoRechazo) {
        
        String asunto = "Solicitud de Materia Rechazada - " + EmailConfig.APP_NAME;
        
        String cuerpoHtml = construirEmailRechazo(nombreUsuario, nombreMateria, nombreCarrera, motivoRechazo);
        
        return enviarEmail(destinatario, asunto, cuerpoHtml);
    }
    
    /**
     * Método principal para enviar emails
     * 
     * @param destinatario Email del destinatario
     * @param asunto Asunto del email
     * @param cuerpoHtml Cuerpo del email en formato HTML
     * @return true si se envió correctamente, false en caso contrario
     */
    private static boolean enviarEmail(String destinatario, String asunto, String cuerpoHtml) {
        
        // Validar que la configuración esté correcta
        if (!EmailConfig.isConfiguracionValida()) {
            System.err.println("❌ Error: Configuración de email no válida. Verifica email.properties");
            return false;
        }
        
        // Validar destinatario
        if (destinatario == null || destinatario.trim().isEmpty()) {
            System.err.println("❌ Error: Email de destinatario vacío");
            return false;
        }
        
        // Configurar propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", String.valueOf(EmailConfig.USE_TLS));
        props.put("mail.smtp.host", EmailConfig.SMTP_HOST);
        props.put("mail.smtp.port", EmailConfig.SMTP_PORT);
        props.put("mail.smtp.ssl.trust", EmailConfig.SMTP_HOST);
        
        // Si se usa SSL (puerto 465)
        if (EmailConfig.USE_SSL) {
            props.put("mail.smtp.socketFactory.port", EmailConfig.SMTP_PORT);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        
        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.EMAIL_FROM, EmailConfig.EMAIL_PASSWORD);
            }
        });
        
        try {
            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.EMAIL_FROM, EmailConfig.APP_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            
            // Configurar contenido HTML
            message.setContent(cuerpoHtml, "text/html; charset=utf-8");
            
            // Enviar mensaje
            Transport.send(message);
            
            System.out.println("Email enviado exitosamente a: " + destinatario);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al enviar email a " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Construye el cuerpo HTML del email de aprobación
     */
    private static String construirEmailAprobacion(String nombreUsuario, String nombreMateria, String nombreCarrera) {
        return "<!DOCTYPE html>" +
                "<html lang='es'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; line-height: 1.6; color: #1e293b; background: #f8fafc; margin: 0; padding: 20px; }" +
                "        .email-wrapper { max-width: 600px; margin: 0 auto; background: white; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 40px 30px; text-align: center; position: relative; }" +
                "        .header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: url(\"data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E\"); opacity: 0.3; }" +
                "        .logo { position: relative; z-index: 1; font-size: 28px; font-weight: 700; color: white; margin-bottom: 10px; }" +
                "        .logo-icon { display: inline-block; width: 40px; height: 40px; background: rgba(255,255,255,0.2); border-radius: 8px; line-height: 40px; text-align: center; margin-right: 10px; vertical-align: middle; }" +
                "        .header-subtitle { position: relative; z-index: 1; color: rgba(255,255,255,0.9); font-size: 14px; }" +
                "        .content { padding: 40px 30px; }" +
                "        .success-badge { width: 80px; height: 80px; margin: 0 auto 30px; background: linear-gradient(135deg, #10b981 0%, #059669 100%); border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 10px 40px rgba(16, 185, 129, 0.3); animation: scaleIn 0.5s ease-out; }" +
                "        @keyframes scaleIn { from { transform: scale(0); opacity: 0; } to { transform: scale(1); opacity: 1; } }" +
                "        .success-icon { font-size: 48px; }" +
                "        .title { font-size: 28px; font-weight: 700; color: #10b981; text-align: center; margin-bottom: 20px; }" +
                "        .greeting { font-size: 16px; color: #64748b; margin-bottom: 20px; }" +
                "        .greeting strong { color: #1e293b; }" +
                "        .message { font-size: 16px; color: #475569; margin-bottom: 30px; line-height: 1.8; }" +
                "        .info-card { background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%); border-left: 4px solid #0ea5e9; border-radius: 12px; padding: 20px; margin: 30px 0; }" +
                "        .info-row { display: flex; margin-bottom: 12px; }" +
                "        .info-row:last-child { margin-bottom: 0; }" +
                "        .info-label { font-weight: 700; color: #0369a1; min-width: 90px; }" +
                "        .info-value { color: #0c4a6e; font-weight: 600; }" +
                "        .features { margin: 30px 0; }" +
                "        .feature-item { display: flex; align-items: flex-start; margin-bottom: 16px; padding: 12px; background: #f8fafc; border-radius: 8px; transition: transform 0.2s; }" +
                "        .feature-item:hover { transform: translateX(5px); background: #f1f5f9; }" +
                "        .feature-icon { width: 24px; height: 24px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 6px; display: flex; align-items: center; justify-content: center; margin-right: 12px; flex-shrink: 0; color: white; font-size: 14px; }" +
                "        .feature-text { color: #475569; font-size: 15px; line-height: 1.6; }" +
                "        .cta-section { text-align: center; margin: 40px 0; }" +
                "        .button { display: inline-block; padding: 16px 40px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 12px; font-weight: 600; font-size: 16px; box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4); transition: all 0.3s; }" +
                "        .button:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5); }" +
                "        .button-icon { display: inline-block; margin-right: 8px; }" +
                "        .divider { height: 1px; background: linear-gradient(to right, transparent, #e2e8f0, transparent); margin: 30px 0; }" +
                "        .footer { background: #f8fafc; padding: 30px; text-align: center; border-top: 1px solid #e2e8f0; }" +
                "        .footer-text { font-size: 13px; color: #64748b; margin-bottom: 8px; }" +
                "        .footer-brand { font-weight: 700; color: #1e293b; margin-top: 15px; font-size: 14px; }" +
                "        @media (max-width: 600px) { body { padding: 10px; } .content { padding: 30px 20px; } .title { font-size: 24px; } .button { padding: 14px 30px; font-size: 15px; } }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='email-wrapper'>" +
                "        <div class='header'>" +
                "            <div class='logo'><span class='logo-icon'>📚</span>" + EmailConfig.APP_NAME + "</div>" +
                "            <div class='header-subtitle'>Sistema de Gestión de Recursos Académicos</div>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <div class='success-badge'><span class='success-icon'>✓</span></div>" +
                "            <h1 class='title'>¡Solicitud Aprobada!</h1>" +
                "            <p class='greeting'>Hola <strong>" + nombreUsuario + "</strong>,</p>" +
                "            <p class='message'>Tenemos excelentes noticias. Tu solicitud de materia ha sido <strong style='color: #10b981;'>aprobada exitosamente</strong> por nuestro equipo de administración.</p>" +
                "            <div class='info-card'>" +
                "                <div class='info-row'><span class='info-label'>📖 Materia:</span><span class='info-value'>" + nombreMateria + "</span></div>" +
                "                <div class='info-row'><span class='info-label'>🎓 Carrera:</span><span class='info-value'>" + nombreCarrera + "</span></div>" +
                "                <div class='info-row'><span class='info-label'>✅ Estado:</span><span class='info-value'>Disponible en el sistema</span></div>" +
                "            </div>" +
                "            <p class='message' style='margin-top: 30px;'><strong>¿Qué puedes hacer ahora?</strong></p>" +
                "            <div class='features'>" +
                "                <div class='feature-item'><div class='feature-icon'>📤</div><div class='feature-text'><strong>Subir recursos</strong> relacionados con esta materia</div></div>" +
                "                <div class='feature-item'><div class='feature-icon'>🔍</div><div class='feature-text'><strong>Buscar materiales</strong> compartidos por otros estudiantes</div></div>" +
                "                <div class='feature-item'><div class='feature-icon'>🤝</div><div class='feature-text'><strong>Colaborar</strong> con la comunidad estudiantil</div></div>" +
                "            </div>" +
                "            <div class='cta-section'>" +
                "                <a href='" + EmailConfig.BASE_URL + "/signin' class='button'><span class='button-icon'>🚀</span>Acceder a la Plataforma</a>" +
                "            </div>" +
                "            <div class='divider'></div>" +
                "            <p style='text-align: center; color: #64748b; font-size: 14px;'>¡Gracias por contribuir al crecimiento de nuestra comunidad académica!</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p class='footer-text'>Este es un mensaje automático, por favor no respondas a este correo.</p>" +
                "            <p class='footer-text'>Si tienes alguna consulta, contáctanos en: <a href='mailto:" + EmailConfig.SUPPORT_EMAIL + "' style='color: #667eea; text-decoration: none;'>" + EmailConfig.SUPPORT_EMAIL + "</a></p>" +
                "            <p class='footer-brand'>© 2024 " + EmailConfig.APP_NAME + "</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
    
    /**
     * Construye el cuerpo HTML del email de rechazo
     */
    private static String construirEmailRechazo(String nombreUsuario, String nombreMateria, 
                                               String nombreCarrera, String motivoRechazo) {
        return "<!DOCTYPE html>" +
                "<html lang='es'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <style>" +
                "        body { font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #dc3545 0%, #c82333 100%); color: white; padding: 30px; text-align: center; }" +
                "        .header h1 { margin: 0; font-size: 24px; }" +
                "        .content { padding: 30px; }" +
                "        .warning-icon { font-size: 60px; text-align: center; margin: 20px 0; }" +
                "        .info-box { background: #f8f9fa; border-left: 4px solid #6c757d; padding: 15px; margin: 20px 0; border-radius: 5px; }" +
                "        .info-box strong { color: #495057; }" +
                "        .reason-box { background: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 20px 0; border-radius: 5px; }" +
                "        .button { display: inline-block; padding: 12px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 25px; margin: 20px 0; font-weight: bold; }" +
                "        .footer { background: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #666; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>📚 " + EmailConfig.APP_NAME + "</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <div class='warning-icon'>❌</div>" +
                "            <h2 style='color: #dc3545; text-align: center;'>Solicitud Rechazada</h2>" +
                "            <p>Hola <strong>" + nombreUsuario + "</strong>,</p>" +
                "            <p>Lamentamos informarte que tu solicitud de materia ha sido <strong style='color: #dc3545;'>rechazada</strong> por nuestro equipo de administración.</p>" +
                "            <div class='info-box'>" +
                "                <p style='margin: 5px 0;'><strong>Materia:</strong> " + nombreMateria + "</p>" +
                "                <p style='margin: 5px 0;'><strong>Carrera:</strong> " + nombreCarrera + "</p>" +
                "            </div>" +
                "            <div class='reason-box'>" +
                "                <p style='margin: 0;'><strong>Motivo del rechazo:</strong></p>" +
                "                <p style='margin-top: 10px;'>" + motivoRechazo + "</p>" +
                "            </div>" +
                "            <p>Si tienes alguna duda o deseas realizar una nueva solicitud con información adicional, puedes:</p>" +
                "            <ul>" +
                "                <li>Revisar si la materia ya existe en el sistema con otro nombre</li>" +
                "                <li>Verificar que la información proporcionada sea correcta</li>" +
                "                <li>Realizar una nueva solicitud con más detalles</li>" +
                "            </ul>" +
                "            <div style='text-align: center;'>" +
                "                <a href='" + EmailConfig.BASE_URL + "/Upload' class='button'>Explorar Materias Disponibles</a>" +
                "            </div>" +
                "            <p style='margin-top: 30px;'>Agradecemos tu interés en mejorar nuestra plataforma.</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>Este es un mensaje automático, por favor no respondas a este correo.</p>" +
                "            <p>Para consultas, contacta a: " + EmailConfig.SUPPORT_EMAIL + "</p>" +
                "            <p>© 2024 " + EmailConfig.APP_NAME + " - Sistema de Gestión de Recursos Académicos</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
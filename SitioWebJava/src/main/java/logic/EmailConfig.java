package logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de configuración para el servicio de email SMTP
 * Lee las credenciales desde un archivo externo (email.properties)
 * para evitar exponer información sensible en el código
 */
public class EmailConfig {
    
    private static Properties config = new Properties();
    private static boolean configuracionCargada = false;
    
    // Configuración SMTP
    public static final String SMTP_HOST;
    public static final String SMTP_PORT;
    
    // Credenciales del email
    public static final String EMAIL_FROM;
    public static final String EMAIL_PASSWORD;
    
    // Nombre de la aplicación
    public static final String APP_NAME;
    public static final String SUPPORT_EMAIL;
    public static final String BASE_URL;
    
    // Configuración SSL/TLS
    public static final boolean USE_TLS = true;
    public static final boolean USE_SSL = false;
    
    static {
        try {
            cargarConfiguracion();
            
            // Asignar valores desde el archivo de configuración
            SMTP_HOST = config.getProperty("email.smtp.host", "smtp.gmail.com");
            SMTP_PORT = config.getProperty("email.smtp.port", "587");
            EMAIL_FROM = config.getProperty("email.from");
            EMAIL_PASSWORD = config.getProperty("email.password");
            APP_NAME = config.getProperty("app.name", "NoteLift");
            SUPPORT_EMAIL = config.getProperty("app.support.email");
            BASE_URL = config.getProperty("app.base.url", "http://localhost:8080/SitioWebJava");
            
            // Validar que las credenciales se hayan cargado
            if (EMAIL_FROM == null || EMAIL_PASSWORD == null) {
                System.err.println("⚠️ ADVERTENCIA: Credenciales de email no configuradas correctamente");
                System.err.println("   Verifica que el archivo email.properties exista y contenga:");
                System.err.println("   - email.from");
                System.err.println("   - email.password");
            } else {
                System.out.println("✅ Configuración de email cargada correctamente");
                System.out.println("   Email configurado: " + EMAIL_FROM);
            }
            
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error al cargar configuración de email: " + e.getMessage());
        }
    }
    
    /**
     * Carga la configuración desde el archivo email.properties
     */
    private static void cargarConfiguracion() throws IOException {
        if (configuracionCargada) {
            return;
        }
        
        InputStream input = null;
        
        try {
            // Intentar múltiples ubicaciones posibles
            
            // 1. Intentar desde el classpath (resources)
            input = EmailConfig.class.getClassLoader().getResourceAsStream("email.properties");
            
            // 2. Si no está ahí, intentar desde la raíz del classpath
            if (input == null) {
                input = EmailConfig.class.getResourceAsStream("/email.properties");
            }
            
            // 3. Si tampoco, intentar carga directa desde archivo
            if (input == null) {
                try {
                    String userDir = System.getProperty("user.dir");
                    java.io.File file = new java.io.File(userDir + "/src/email.properties");
                    if (file.exists()) {
                        input = new java.io.FileInputStream(file);
                        System.out.println("📁 Archivo encontrado en: " + file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    // Ignorar y continuar
                }
            }
            
            if (input == null) {
                throw new IOException("No se encontró el archivo email.properties en ninguna ubicación. " +
                                    "Ubicaciones buscadas:\n" +
                                    "  1. Classpath: email.properties\n" +
                                    "  2. Raíz: /email.properties\n" +
                                    "  3. Directorio: src/email.properties\n" +
                                    "Asegúrate de que existe en src/email.properties");
            }
            
            config.load(input);
            configuracionCargada = true;
            
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private EmailConfig() {
        // Constructor privado para evitar instanciación
    }
    
    /**
     * Verifica si la configuración está correctamente cargada
     */
    public static boolean isConfiguracionValida() {
        return EMAIL_FROM != null && !EMAIL_FROM.isEmpty() 
            && EMAIL_PASSWORD != null && !EMAIL_PASSWORD.isEmpty();
    }
}
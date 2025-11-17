package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DataSolicitudMateria;
import entities.SolicitudMateria;
import entities.Usuario;
import logic.EmailService;

/**
 * Servlet implementation class ProcesarSolicitud
 * Procesa la aprobación o rechazo de solicitudes de materia
 * y envía notificaciones por email al usuario solicitante
 */
@WebServlet({ "/ProcesarSolicitud", "/procesarSolicitud", "/procesarsolicitud", "/PROCESARSOLICITUD" })
public class ProcesarSolicitud extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public ProcesarSolicitud() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No se usa GET
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Configurar encoding y respuesta JSON
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Verificar sesión y permisos
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                out.print("{\"success\": false, \"message\": \"Sesión expirada\"}");
                return;
            }
            
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            if (!"administrador".equalsIgnoreCase(usuario.getRol())) {
                out.print("{\"success\": false, \"message\": \"No tienes permisos\"}");
                return;
            }
            
            // Obtener parámetros
            String accion = request.getParameter("accion"); // "aprobar" o "rechazar"
            String idSolicitudStr = request.getParameter("idSolicitud");
            String motivoRechazo = request.getParameter("motivoRechazo");
            
            // Validaciones
            if (idSolicitudStr == null || idSolicitudStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"ID de solicitud inválido\"}");
                return;
            }
            
            if (accion == null || (!accion.equals("aprobar") && !accion.equals("rechazar"))) {
                out.print("{\"success\": false, \"message\": \"Acción inválida\"}");
                return;
            }
            
            int idSolicitud = Integer.parseInt(idSolicitudStr);
            int idAdministrador = usuario.getId();
            
            DataSolicitudMateria dataSolicitud = new DataSolicitudMateria();
            
            // NUEVO: Obtener los datos de la solicitud ANTES de procesarla
            SolicitudMateria solicitud = dataSolicitud.getSolicitudById(idSolicitud);
            
            if (solicitud == null) {
                out.print("{\"success\": false, \"message\": \"Solicitud no encontrada\"}");
                return;
            }
            
            boolean resultado = false;
            String mensaje = "";
            boolean emailEnviado = false;
            
            if ("aprobar".equals(accion)) {
                // APROBAR - Crea la materia y la asocia (o rechaza si es duplicado)
                resultado = dataSolicitud.aprobarSolicitud(idSolicitud, idAdministrador);
                
                if (resultado) {
                    // NUEVO: Verificar el estado final de la solicitud
                    SolicitudMateria solicitudFinal = dataSolicitud.getSolicitudById(idSolicitud);
                    
                    if ("APROBADA".equals(solicitudFinal.getEstado())) {
                        // La materia fue aprobada correctamente
                        mensaje = "Materia aprobada y creada exitosamente";
                        System.out.println("Solicitud " + idSolicitud + " APROBADA por admin " + idAdministrador);
                        
                        // Enviar email de aprobación
                        try {
                            emailEnviado = EmailService.enviarEmailAprobacion(
                                solicitud.getEmailUsuarioSolicitante(),
                                solicitud.getNombreUsuarioSolicitante(),
                                solicitud.getNombreMateria(),
                                solicitud.getNombreCarrera()
                            );
                            
                            if (emailEnviado) {
                                System.out.println("Email de aprobación enviado a: " + solicitud.getEmailUsuarioSolicitante());
                            } else {
                                System.out.println("No se pudo enviar el email de aprobación");
                            }
                        } catch (Exception e) {
                            System.err.println("Error al enviar email de aprobación: " + e.getMessage());
                            e.printStackTrace();
                        }
                        
                    } else if ("RECHAZADA".equals(solicitudFinal.getEstado())) {
                        // La materia fue rechazada automáticamente por duplicado
                        mensaje = "La materia ya existía en el sistema y fue rechazada automáticamente";
                        System.out.println("Solicitud " + idSolicitud + " rechazada automáticamente (duplicado)");
                        
                        // Enviar email de rechazo con el motivo del duplicado
                        String motivoDuplicado = solicitudFinal.getMotivoRechazo();
                        if (motivoDuplicado == null || motivoDuplicado.isEmpty()) {
                            motivoDuplicado = "La materia ya existe en el sistema para esta carrera.";
                        }
                        
                        try {
                            emailEnviado = EmailService.enviarEmailRechazo(
                                solicitud.getEmailUsuarioSolicitante(),
                                solicitud.getNombreUsuarioSolicitante(),
                                solicitud.getNombreMateria(),
                                solicitud.getNombreCarrera(),
                                motivoDuplicado
                            );
                            
                            if (emailEnviado) {
                                System.out.println("Email de rechazo enviado a: " + solicitud.getEmailUsuarioSolicitante());
                            } else {
                                System.out.println("No se pudo enviar el email de rechazo");
                            }
                        } catch (Exception e) {
                            System.err.println("Error al enviar email de rechazo: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    
                } else {
                    mensaje = "Error al procesar la solicitud";
                    System.out.println("Error al procesar solicitud " + idSolicitud);
                }
                
            } else if ("rechazar".equals(accion)) {
                // RECHAZAR
                if (motivoRechazo == null || motivoRechazo.trim().isEmpty()) {
                    out.print("{\"success\": false, \"message\": \"Debes especificar el motivo del rechazo\"}");
                    return;
                }
                
                resultado = dataSolicitud.rechazarSolicitud(idSolicitud, idAdministrador, motivoRechazo);
                
                if (resultado) {
                    mensaje = "Solicitud rechazada correctamente";
                    System.out.println("Solicitud " + idSolicitud + " RECHAZADA por admin " + idAdministrador);
                    
                    // NUEVO: Enviar email de rechazo
                    try {
                        emailEnviado = EmailService.enviarEmailRechazo(
                            solicitud.getEmailUsuarioSolicitante(),
                            solicitud.getNombreUsuarioSolicitante(),
                            solicitud.getNombreMateria(),
                            solicitud.getNombreCarrera(),
                            motivoRechazo
                        );
                        
                        if (emailEnviado) {
                            System.out.println("Email de rechazo enviado a: " + solicitud.getEmailUsuarioSolicitante());
                        } else {
                            System.out.println("No se pudo enviar el email de rechazo");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al enviar email de rechazo: " + e.getMessage());
                        e.printStackTrace();
                    }
                    
                } else {
                    mensaje = "Error al rechazar la solicitud";
                }
            }
            
            // Respuesta
            if (resultado) {
                // Agregar información sobre el email en la respuesta
                String mensajeFinal = mensaje;
                if (emailEnviado) {
                    mensajeFinal += ". Notificación enviada al usuario.";
                } else {
                    mensajeFinal += ". Advertencia: No se pudo enviar la notificación por email.";
                }
                out.print("{\"success\": true, \"message\": \"" + mensajeFinal + "\", \"emailEnviado\": " + emailEnviado + "}");
            } else {
                out.print("{\"success\": false, \"message\": \"" + mensaje + "\"}");
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Datos inválidos\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
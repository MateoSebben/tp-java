package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/welcome")
public class Welcome extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Obtener o crear sesión
        HttpSession session = request.getSession();

        // Obtener parámetros de la URL
        String userName = request.getParameter("name");
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Usuario";
        }

        // Obtener fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = now.format(formatter);
// Contador de visitas
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        // Establecer atributos para la JSP
        request.setAttribute("userName", userName);
        request.setAttribute("currentDateTime", currentDateTime);
        request.setAttribute("visitCount", visitCount);
        request.setAttribute("sessionId", session.getId());

        // Redirigir a la página JSP
        request.getRequestDispatcher("/WEB-INF/Welcome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}

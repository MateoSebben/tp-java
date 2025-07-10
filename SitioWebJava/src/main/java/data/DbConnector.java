package data;

import java.sql.*;

public class DbConnector {

    private static DbConnector instancia;

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String host = "localhost";
    private String port = "3306";
    private String user = "java";
    private String password = "himitsu";
    private String db = "tpjava";
    private int conectados = 0;
    private Connection conn = null;

    private DbConnector() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Esto es un error crítico si el driver no se encuentra.
            // Es buena práctica lanzar una RuntimeException.
            throw new RuntimeException("Error al cargar el driver JDBC: " + driver, e);
        }
    }

    public static DbConnector getInstancia() {
        if (instancia == null) {
            instancia = new DbConnector();
        }
        return instancia;
    }

    // *** CAMBIO CRÍTICO Y FUNDAMENTAL AQUÍ ***
    // Ahora el método getConn() declara que lanza SQLException
    public Connection getConn() throws SQLException {
        try {
            // Si la conexión es null o está cerrada, intenta abrir una nueva
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
            

                conectados = 0; // Reinicia el contador cuando se abre una nueva conexión
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la traza para depuración
            // *** ¡RELANZA LA EXCEPCIÓN! ***
            // Esto es CRUCIAL para que DataUsuario sepa que la conexión falló.
            throw e;
        }
        // Incrementa el contador solo si la conexión no es null (y por lo tanto, fue exitosa o ya estaba abierta)
        if (conn != null) {
            conectados++;
        }
        return conn; // Devuelve la conexión (ahora, si llega aquí, conn NO será null)
    }

    public void releaseConn() {
        // Solo decrementa si hay conexiones "activas" registradas
        if (conectados > 0) {
            conectados--;
        }

        try {
            // ¡VERIFICACIÓN CRÍTICA! Solo intenta cerrar si la conexión no es null Y no hay más "usuarios"
            if (conn != null && !conn.isClosed() && conectados <= 0) {
                conn.close();
                conn = null; // Establece a null después de cerrar para indicar que ya no está activa
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes loguear este error de cierre, pero generalmente no es crítico
            // para el flujo de la aplicación a menos que estés agotando recursos.
        }
    }
}





/*
package data;

import java.sql.*;

public class DbConnector {

    private static DbConnector instancia;

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String host = "localhost";
    private String port = "3306";
    private String user = "java";
    private String password = "himitsu";
    private String db = "tpjava";
    private int conectados = 0;
    private Connection conn = null; // Inicialmente null

    private DbConnector() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Considera lanzar una RuntimeException o loguear esto de forma más robusta
            // para indicar que la aplicación no puede iniciar sin el driver.
        }
    }

    public static DbConnector getInstancia() {
        if (instancia == null) {
            instancia = new DbConnector();
        }
        return instancia;
    }

    public Connection getConn() {
        try {
            // Si la conexión es null o está cerrada, intenta abrir una nueva
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
                conectados = 0; // Reinicia el contador cuando se abre una nueva conexión
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ¡IMPORTANTE! Si la conexión falla, conn permanece null.
            // Considera lanzar una excepción para que el código llamador sepa que no hay conexión.
            // throw new RuntimeException("Error al conectar a la base de datos", e);
        }
        // Incrementa el contador solo si la conexión no es null (y por lo tanto, fue exitosa o ya estaba abierta)
        if (conn != null) {
            conectados++;
        }
        return conn; // Devuelve la conexión (podría ser null si falló)
    }

    public void releaseConn() {
        // Solo decrementa si hay conexiones "activas" registradas
        if (conectados > 0) {
            conectados--;
        }

        try {
            // ¡VERIFICACIÓN CRÍTICA! Solo intenta cerrar si la conexión no es null Y no hay más "usuarios"
            if (conn != null && !conn.isClosed() && conectados <= 0) {
                conn.close();
                conn = null; // Establece a null después de cerrar para indicar que ya no está activa
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes loguear este error de cierre, pero generalmente no es crítico
            // para el flujo de la aplicación a menos que estés agotando recursos.
        }
    }
}*/

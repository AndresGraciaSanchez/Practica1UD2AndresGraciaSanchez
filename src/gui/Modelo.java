package gui;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Modelo {

    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    private Connection conexion;

    public Modelo() {
        getPropValues();
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAdminPassword() {
        return adminPassword;
    }


    void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/tienda_software", user, password);
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://" + ip + ":3306/", user, password);

                PreparedStatement statement = null;
                String code = leerFichero();
                String[] query = code.split("--");

                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basedatos_java.sql"));
        String linea;
        StringBuilder sb = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            sb.append(linea);
            sb.append(" ");
        }
        return sb.toString();
    }

    boolean desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void insertarDesarrollador(String nombre, String email, String pais, LocalDate fechaRegistro) {

        String sentenciaSql = "insert into desarrolladores (nombre, email, pais, fecharegistro) values (?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, email);
            sentencia.setString(3, pais);
            sentencia.setDate(4, Date.valueOf(fechaRegistro));
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }


    void insertarCategoria(String nombre, String descripcion, String nivel) {

        String sentenciaSql = "insert into categorias (nombre, descripcion, nivel) values (?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, descripcion);
            sentencia.setString(3, nivel);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }


    void insertarLicencia(String nombre, String tipo, String url, float coste) {

        String sentenciaSql = "insert into licencias (nombre, tipo, url, costesoporte) values (?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, tipo);
            sentencia.setString(3, url);
            sentencia.setFloat(4, coste);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }


    void insertarSoftware(String nombre, String version, float precio, LocalDate fechaLanzamiento,
                          boolean activo, String desarrollador, String categoria, String licencia) {

        String sentenciaSql =
                "insert into software (nombre, version, precio, fechalanzamiento, activo, " +
                        "iddesarrollador, idcategoria, idlicencia) values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        int iddesarrollador = Integer.valueOf(desarrollador.split(" ")[0]);
        int idcategoria = Integer.valueOf(categoria.split(" ")[0]);
        int idlicencia = Integer.valueOf(licencia.split(" ")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, version);
            sentencia.setFloat(3, precio);
            sentencia.setDate(4, Date.valueOf(fechaLanzamiento));
            sentencia.setBoolean(5, activo);
            sentencia.setInt(6, iddesarrollador);
            sentencia.setInt(7, idcategoria);
            sentencia.setInt(8, idlicencia);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarSoftware(String nombre, String version, float precio, LocalDate fechaLanzamiento,
                           boolean activo, int idsoftware) {

        String sentenciaSql =
                "update software set nombre = ?, version = ?, precio = ?, fechalanzamiento = ?, activo = ? " +
                        "where idsoftware = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, version);
            sentencia.setFloat(3, precio);
            sentencia.setDate(4, Date.valueOf(fechaLanzamiento));
            sentencia.setBoolean(5, activo);
            sentencia.setInt(6, idsoftware);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarDesarrollador(String nombre, String email, String pais, LocalDate fechaRegistro, int iddesarrollador) {

        String sentenciaSql = "update desarrolladores set nombre = ?, email = ?, pais = ?, fecharegistro = ? " +
                "where iddesarrollador = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, email);
            sentencia.setString(3, pais);
            sentencia.setDate(4, Date.valueOf(fechaRegistro));
            sentencia.setInt(5, iddesarrollador);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarCategoria(String nombre, String descripcion, String nivel, int idcategoria) {

        String sentenciaSql = "update categorias set nombre = ?, descripcion = ?, nivel = ? " +
                "where idcategoria = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, descripcion);
            sentencia.setString(3, nivel);
            sentencia.setInt(4, idcategoria);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarLicencia(String nombre, String tipo, String url, float coste, int idlicencia) {

        String sentenciaSql = "update licencias set nombre = ?, tipo = ?, url = ?, costesoporte = ? " +
                "where idlicencia = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, tipo);
            sentencia.setString(3, url);
            sentencia.setFloat(4, coste);
            sentencia.setInt(5, idlicencia);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarSoftware(int idsoftware) {

        String sentenciaSql = "delete from software where idsoftware = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idsoftware);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarDesarrollador(int iddesarrollador) {

        String sentenciaSql = "delete from desarrolladores where iddesarrollador = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, iddesarrollador);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarCategoria(int idcategoria) {

        String sentenciaSql = "delete from categorias where idcategoria = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idcategoria);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarLicencia(int idlicencia) {

        String sentenciaSql = "delete from licencias where idlicencia = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idlicencia);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    ResultSet consultarSoftware() throws SQLException {
        String sentenciaSql = "SELECT s.idsoftware as 'ID', " +
                "s.nombre as 'Nombre', " +
                "s.version as 'Versión', " +
                "s.precio as 'Precio', " +
                "s.fechalanzamiento as 'Fecha de lanzamiento', " +
                "s.activo as 'Activo', " +
                "concat(d.iddesarrollador, ' - ', d.nombre) as 'Desarrollador', " +
                "concat(c.idcategoria, ' - ', c.nombre) as 'Categoría', " +
                "concat(l.idlicencia, ' - ', l.nombre) as 'Licencia' " +
                "FROM software as s " +
                "INNER JOIN desarrolladores as d ON s.iddesarrollador = d.iddesarrollador " +
                "INNER JOIN categorias as c ON s.idcategoria = c.idcategoria " +
                "INNER JOIN licencias as l ON s.idlicencia = l.idlicencia";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarDesarrollador() throws SQLException {
        String sentenciaSql = "SELECT iddesarrollador as 'ID', " +
                "nombre as 'Nombre', " +
                "email as 'Email', " +
                "pais as 'País', " +
                "fecharegistro as 'Fecha de registro' " +
                "FROM desarrolladores";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarCategoria() throws SQLException {
        String sentenciaSql = "SELECT idcategoria as 'ID', " +
                "nombre as 'Nombre', " +
                "descripcion as 'Descripción', " +
                "nivel as 'Nivel' " +
                "FROM categorias";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarLicencia() throws SQLException {
        String sentenciaSql = "SELECT idlicencia as 'ID', " +
                "nombre as 'Nombre', " +
                "tipo as 'Tipo', " +
                "url as 'URL', " +
                "costesoporte as 'Coste' " +
                "FROM licencias";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    public boolean softwareNombreYaExiste(String nombre) {
        String sql = "SELECT existeSoftware(?)";
        PreparedStatement sentencia = null;
        boolean nameExists = false;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            rs.next();
            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return nameExists;
    }

    public boolean desarrolladorNombreYaExiste(String nombre) {
        String sql = "SELECT existeDesarrollador(?)";
        PreparedStatement sentencia = null;
        boolean nameExists = false;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            rs.next();
            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return nameExists;
    }

    public boolean categoriaNombreYaExiste(String nombre) {
        String sql = "SELECT existeCategoria(?)";
        PreparedStatement sentencia = null;
        boolean nameExists = false;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            rs.next();
            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return nameExists;
    }

    public boolean licenciaNombreYaExiste(String nombre) {
        String sql = "SELECT existeLicencia(?)";
        PreparedStatement sentencia = null;
        boolean nameExists = false;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();
            rs.next();
            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return nameExists;
    }

    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }

    boolean desarrolladorEnUso(int iddesarrollador) throws SQLException {

        String sql = "select count(*) from software where iddesarrollador = ?";
        PreparedStatement sentencia = null;
        ResultSet rs = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, iddesarrollador);
            rs = sentencia.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } finally {
            if (rs != null) rs.close();
            if (sentencia != null) sentencia.close();
        }
    }


    boolean categoriaEnUso(int idcategoria) throws SQLException {

        String sql = "select count(*) from software where idcategoria = ?";
        PreparedStatement sentencia = null;
        ResultSet rs = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idcategoria);
            rs = sentencia.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } finally {
            if (rs != null) rs.close();
            if (sentencia != null) sentencia.close();
        }
    }


    boolean licenciaEnUso(int idlicencia) throws SQLException {

        String sql = "select count(*) from software where idlicencia = ?";
        PreparedStatement sentencia = null;
        ResultSet rs = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idlicencia);
            rs = sentencia.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } finally {
            if (rs != null) rs.close();
            if (sentencia != null) sentencia.close();
        }
    }


}

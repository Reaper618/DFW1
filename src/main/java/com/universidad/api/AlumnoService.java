
package com.universidad.api;

import com.universidad.db.DatabaseConnection;
import com.universidad.model.Alumno;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/curso/{cursoId}/alumno")
public class AlumnoService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> getAlumnos(@PathParam("cursoId") Long cursoId) {
        List<Alumno> alumnos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Alumno WHERE curso_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, cursoId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setId(rs.getLong("id"));
                    alumno.setNombreAlumno(rs.getString("nombreAlumno"));
                    alumno.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    alumno.setNumeroTelefono(rs.getString("numeroTelefono"));
                    alumno.setEmail(rs.getString("email"));
                    alumno.setCursoId(rs.getLong("curso_id"));
                    alumnos.add(alumno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Alumno getAlumno(@PathParam("cursoId") Long cursoId, @PathParam("id") Long id) {
        Alumno alumno = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Alumno WHERE id = ? AND curso_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, id);
                pstmt.setLong(2, cursoId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    alumno = new Alumno();
                    alumno.setId(rs.getLong("id"));
                    alumno.setNombreAlumno(rs.getString("nombreAlumno"));
                    alumno.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    alumno.setNumeroTelefono(rs.getString("numeroTelefono"));
                    alumno.setEmail(rs.getString("email"));
                    alumno.setCursoId(rs.getLong("curso_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alumno createAlumno(@PathParam("cursoId") Long cursoId, Alumno alumno) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Alumno (nombreAlumno, fechaNacimiento, numeroTelefono, email, curso_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, alumno.getNombreAlumno());
                pstmt.setDate(2, new java.sql.Date(alumno.getFechaNacimiento().getTime()));
                pstmt.setString(3, alumno.getNumeroTelefono());
                pstmt.setString(4, alumno.getEmail());
                pstmt.setLong(5, cursoId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            alumno.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Alumno updateAlumno(@PathParam("cursoId") Long cursoId, @PathParam("id") Long id, Alumno alumno) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Alumno SET nombreAlumno = ?, fechaNacimiento = ?, numeroTelefono = ?, email = ? WHERE id = ? AND curso_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, alumno.getNombreAlumno());
                pstmt.setDate(2, new java.sql.Date(alumno.getFechaNacimiento().getTime()));
                pstmt.setString(3, alumno.getNumeroTelefono());
                pstmt.setString(4, alumno.getEmail());
                pstmt.setLong(5, id);
                pstmt.setLong(6, cursoId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAlumno(@PathParam("cursoId") Long cursoId, @PathParam("id") Long id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Alumno WHERE id = ? AND curso_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, id);
                pstmt.setLong(2, cursoId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
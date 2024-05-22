
package com.universidad.api;

import com.universidad.db.DatabaseConnection;
import com.universidad.model.Curso;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/curso")
public class CursoService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Curso> getCursos() {
        List<Curso> cursos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Curso";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setId(rs.getLong("id"));
                    curso.setNombreCurso(rs.getString("nombreCurso"));
                    curso.setNombreProfesor(rs.getString("nombreProfesor"));
                    curso.setNumeroTelefono(rs.getString("numeroTelefono"));
                    curso.setEmail(rs.getString("email"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Curso getCurso(@PathParam("id") Long id) {
        Curso curso = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Curso WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    curso = new Curso();
                    curso.setId(rs.getLong("id"));
                    curso.setNombreCurso(rs.getString("nombreCurso"));
                    curso.setNombreProfesor(rs.getString("nombreProfesor"));
                    curso.setNumeroTelefono(rs.getString("numeroTelefono"));
                    curso.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Curso createCurso(Curso curso) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Curso (nombreCurso, nombreProfesor, numeroTelefono, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, curso.getNombreCurso());
                pstmt.setString(2, curso.getNombreProfesor());
                pstmt.setString(3, curso.getNumeroTelefono());
                pstmt.setString(4, curso.getEmail());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            curso.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Curso updateCurso(@PathParam("id") Long id, Curso curso) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Curso SET nombreCurso = ?, nombreProfesor = ?, numeroTelefono = ?, email = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, curso.getNombreCurso());
                pstmt.setString(2, curso.getNombreProfesor());
                pstmt.setString(3, curso.getNumeroTelefono());
                pstmt.setString(4, curso.getEmail());
                pstmt.setLong(5, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCurso(@PathParam("id") Long id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Curso WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

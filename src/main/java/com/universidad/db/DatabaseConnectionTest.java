/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.universidad.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author angel
 */
public class DatabaseConnectionTest {
    private static final String URL = "jdbc:mysql://localhost:3306/universidad"; // Reemplaza 'tu_basededatos' con el nombre de tu base de datos
    private static final String USER = "root"; // Reemplaza 'tu_usuario' con tu nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Reemplaza 'tu_contraseña' con tu contraseña de la base de datos

    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("¡Conexión a la base de datos establecida exitosamente!");
            }
        } catch (SQLException e) {
            System.err.println("Error al intentar conectar a la base de datos: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error al intentar cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}

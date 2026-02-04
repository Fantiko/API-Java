/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.Database;


/**
 *
 * @author Rafael
 */



import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;

public class IniDataBase{

    public static void init() {
        try (Connection conn = SQLiteConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS departamento (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    gestor TEXT,
                    capacidade INTEGER NOT NULL,
                    ativo INTEGER NOT NULL
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



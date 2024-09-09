package com.example.demo_ddd_pattern.application.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CloseConnection {
    public static void close(Connection con, CallableStatement cs) {
        try {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

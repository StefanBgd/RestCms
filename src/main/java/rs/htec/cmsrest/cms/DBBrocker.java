/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cmsrest.cms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stefan
 */
public class DBBrocker {

    public Connection conn;

    // !!!!!
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readNews() {
        String url = "jdbc:mysql://localhost:3306/db12";
        String upit = "select * from news_db";
        String message = "";
        try (Connection conn = DriverManager.getConnection(url,
                "root", "root");
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(upit)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String body = rs.getString(3);
                message += "Vest br. : " + id
                        + ", Naslov: " + title
                        + ", Tekst: " + body + "\n";
            }
        } catch (SQLException ex) {
            message = "Greska";
            Logger.getLogger(DBBrocker.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return message;
    }

    public String readNewsAtIndex(int index) {
        String url = "jdbc:mysql://localhost:3306/db12";
        String upit = "select * from news_db where id =" + index;
        String message = "";
        try (Connection conn = DriverManager.getConnection(url,
                "root", "root");
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(upit)) {
            rs.next();
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String body = rs.getString(3);
            message += "Vest br. : " + id
                    + ", Naslov: " + title
                    + ", Tekst: " + body + "\n";

        } catch (SQLException ex) {
            Logger.getLogger(DBBrocker.class.getName()).log(Level.SEVERE,
                    null, ex);
            message = "Greska";
        }
        return message;
    }

    public void addNews(String title, String body) {
        String url = "jdbc:mysql://localhost:3306/db12";
        String upit = "insert into news_db (title, body) values (?,?)";
        try (Connection conn = DriverManager.getConnection(url,
                "root", "root");
                PreparedStatement stat
                = conn.prepareStatement(upit)) {
            conn.setAutoCommit(false);
            stat.setString(1, title);
            stat.setString(2, body);
            int count = stat.executeUpdate();
            if (count > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBrocker.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void changeNews(int index, String newBody) {
        String url = "jdbc:mysql://localhost:3306/db12";
        String upit = "UPDATE news_db SET body = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url,
                "root", "root");
                PreparedStatement stat
                = conn.prepareStatement(upit)) {
            stat.setString(1, newBody);
            stat.setInt(2, index);
            stat.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBBrocker.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public Connection openConn() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db12";
        return DriverManager.getConnection(url, "root", "root");
    }

}

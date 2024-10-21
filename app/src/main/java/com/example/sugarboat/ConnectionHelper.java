package com.example.sugarboat;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    Connection con;
    String uname, pass, ip, port, database;

    public Connection connectionclass() {
        ip = "localhost";  // Para teste no emulador Android, use 10.0.2.2
        database = "sugarboat";
        uname = "root";
        pass = "";
        port = "3306";  // Verifique se esta é a porta correta

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ConnectionURL = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            connection = DriverManager.getConnection(ConnectionURL, uname, pass);
            Log.i("DB Connection Status", "Conexão com a base de dados foi bem-sucedida!");
        } catch (Exception ex) {
            Log.e("Connection Error", "Erro na conexão: " + ex.getMessage());
        }

        return connection;
    }

}

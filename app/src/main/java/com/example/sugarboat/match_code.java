package com.example.sugarboat;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class match_code {

    public void calculateMatchScore() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection conn = connectionClass.CONN();

        if (conn != null) {
            try {
                int userId = 4;

                String userInterestsQuery = "SELECT interessessimples.interesse, categorias.nome AS categoria " +
                        "FROM usuario_interesses " +
                        "JOIN interessessimples ON usuario_interesses.interesse = interessessimples.interesse " +
                        "JOIN categorias ON interessessimples.categoria_id = categorias.id " +
                        "WHERE usuario_interesses.id_usuario = " + userId;

                String otherInterestsQuery = "SELECT usuario_interesses.id_usuario, interessessimples.interesse, categorias.nome AS categoria " +
                        "FROM usuario_interesses " +
                        "JOIN interessessimples ON usuario_interesses.interesse = interessessimples.interesse " +
                        "JOIN categorias ON interessessimples.categoria_id = categorias.id " +
                        "WHERE usuario_interesses.id_usuario != " + userId;

                Statement stmt = conn.createStatement();

                ResultSet userRs = stmt.executeQuery(userInterestsQuery);
                Map<String, String> userInterests = new HashMap<>();
                while (userRs.next()) {
                    String interesse = userRs.getString("interesse");
                    String categoria = userRs.getString("categoria");
                    userInterests.put(interesse, categoria);
                }
                userRs.close();

                ResultSet otherRs = stmt.executeQuery(otherInterestsQuery);
                Map<Integer, Map<String, String>> otherUsersInterests = new HashMap<>();
                while (otherRs.next()) {
                    int otherUserId = otherRs.getInt("id_usuario");
                    String interesse = otherRs.getString("interesse");
                    String categoria = otherRs.getString("categoria");

                    otherUsersInterests.putIfAbsent(otherUserId, new HashMap<>());
                    otherUsersInterests.get(otherUserId).put(interesse, categoria);
                }
                otherRs.close();

                Map<Integer, Integer> matchScores = new HashMap<>();

                for (Map.Entry<Integer, Map<String, String>> otherUserEntry : otherUsersInterests.entrySet()) {
                    int otherUserId = otherUserEntry.getKey();
                    Map<String, String> otherInterests = otherUserEntry.getValue();

                    int matchScore = 0;

                    Set<String> matchedInterests = new HashSet<>();

                    for (Map.Entry<String, String> userInterest : userInterests.entrySet()) {
                        String myInterest = userInterest.getKey();
                        String myCategory = userInterest.getValue();

                        for (Map.Entry<String, String> otherInterest : otherInterests.entrySet()) {
                            String theirInterest = otherInterest.getKey();
                            String theirCategory = otherInterest.getValue();

                            if (myInterest.equals(theirInterest)) {
                                matchScore += 20;
                                matchedInterests.add(myInterest);
                                break;
                            } else if (myCategory.equals(theirCategory) && !matchedInterests.contains(myInterest)) {

                                matchScore += 10;
                                matchedInterests.add(myInterest);
                            }
                        }
                    }

                    matchScores.put(otherUserId, Math.min(matchScore, 100));
                }

                for (Map.Entry<Integer, Integer> match : matchScores.entrySet()) {
                    Log.d("MATCH_SCORE", "Usuário ID " + match.getKey() + " tem uma pontuação de match de: " + match.getValue());
                }

                stmt.close();
                conn.close();

            } catch (Exception e) {
                Log.e("MATCH_SCORE", "Erro ao calcular o match: " + e.getMessage());
            }
        } else {
            Log.e("MATCH_SCORE", "Erro ao conectar à base de dados!");
        }
    }
}

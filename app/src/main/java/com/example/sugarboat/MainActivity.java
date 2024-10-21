package com.example.sugarboat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.sugarboat.view.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    exploreFragment ExploreFragment = new exploreFragment();
    homeFragment HomeFragment = new homeFragment();

    ConnectionClass connectionClass;

    Connection con;

    ResultSet rs;

    String name, str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*BottomNavigationView bottomNav = findViewById(R.id.bot_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);*/

        // Carregar o fragmento inicial
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();
        connectionClass = new ConnectionClass();
        connect();

        // Chamar o ConnectionHelper para verificar a conexão com o banco de dados
//        ConnectionHelper connectionHelper = new ConnectionHelper();
//        Connection connection = connectionHelper.connectionclass();

//        if (connection != null) {
//            Log.i("DB Connection Status", "Conexão com a base de dados foi bem-sucedida!");
//        } else {
//            Log.e("DB Connection Status", "Falha na conexão com a base de dados!");
//        }
    }

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                if(con == null){
                    str = "Error in connection with MySQL server";
                }else{
                    str = "Connected with MySQL server";
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Log.e("ActivityLOgin", e.getMessage());
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });

        });
    }


    /*private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            selectedFragment = new homeFragment();
        } else if (itemId == R.id.explore) {
            selectedFragment = new exploreFragment();
        } else if (itemId == R.id.premium) {
            selectedFragment = new premiumFragment();
        } else if (itemId == R.id.chat) {
            selectedFragment = new chatFragment();
        } else if (itemId == R.id.account) {
            selectedFragment = new AccountFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
        }

        return true;
    };*/
}

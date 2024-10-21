package com.example.sugarboat.view;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.view.KeyEvent;
import com.example.sugarboat.R;
import android.content.Context;
import android.content.ClipData;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.content.ClipboardManager;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.textfield.TextInputEditText;

/***
 * Classe para a Activity de confirmação de código recebido por email.
 * Sua função é confirmar o código de acordo com o que foi definido na hora de criação do usuário
 * validando assim o processo de Sign-in.
 *
 * @author GabrielRuneScape <gabrielfilipe@mail.ru>
 * @since 2024-09-21
 */
public class ConfirmActivity extends AppCompatActivity implements View.OnKeyListener {
    private static final String TAG = "ConfirmActivity";

    MaterialButton btnConfirm;
    MaterialTextView txtViewPaste;
    TextInputEditText[] inputConfirm = new TextInputEditText[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnConfirm = findViewById(R.id.btnConfirm);
        txtViewPaste = findViewById(R.id.txtViewPaste);

        inputConfirm[0] = findViewById(R.id.txtInputConfirm0);
        inputConfirm[1] = findViewById(R.id.txtInputConfirm1);
        inputConfirm[2] = findViewById(R.id.txtInputConfirm2);
        inputConfirm[3] = findViewById(R.id.txtInputConfirm3);
        inputConfirm[4] = findViewById(R.id.txtInputConfirm4);
        inputConfirm[5] = findViewById(R.id.txtInputConfirm5);

        // Define evento para percorrer os próprios
        inputConfirm[0].setOnKeyListener(this);
        inputConfirm[1].setOnKeyListener(this);
        inputConfirm[2].setOnKeyListener(this);
        inputConfirm[3].setOnKeyListener(this);
        inputConfirm[4].setOnKeyListener(this);
        inputConfirm[5].setOnKeyListener(this);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), getText(R.string.error_message_not_implemented), Toast.LENGTH_LONG).show();
            }
        });

        // Verifica e cola código de validação
        txtViewPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                    CharSequence clip = item.getText();

                    if (clip.length() == 6) {
                        int check = Integer.parseInt(clip.toString());

                        if (check > -1) {
                            for (int i = 0; i < clip.length(); i++) {
                                inputConfirm[i].setText(""+clip.charAt(i));
                            }
                        }
                    } else {
                        Toast.makeText(v.getContext(), getText(R.string.error_message_not_valid_code), Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException ex) {
                    Log.e(TAG, ex.getMessage());
                    Toast.makeText(v.getContext(), getText(R.string.error_message_clipboard_null), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException ex) {
                    Log.e(TAG, ex.getMessage());
                    Toast.makeText(v.getContext(), getText(R.string.error_message_not_valid_code), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Percore todos os TextInputEditText a medida que presiona-se as teclas físicas
     *
     * @param v The view the key has been dispatched to.
     * @param keyCode The code for the physical key that was pressed
     * @param event The KeyEvent object containing full information about
     *        the event.
     * @return Resultado sempre sera falso
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int viewPos = 0;

        // Verifica em qual TextInputEditText foi executado o comando
        for (int i = 0; i < inputConfirm.length; i++) {
            if (v.getId() == inputConfirm[i].getId()) {
                viewPos = i;
            }
        }

        // Percore TextInputEditText
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DEL:
                case KeyEvent.KEYCODE_FORWARD_DEL:
                    inputConfirm[viewPos].setText("");

                    if (v.getId() != inputConfirm[0].getId()) {
                        inputConfirm[(viewPos - 1)].requestFocus();
                    }
                    break;
                case KeyEvent.KEYCODE_0:
                case KeyEvent.KEYCODE_1:
                case KeyEvent.KEYCODE_2:
                case KeyEvent.KEYCODE_3:
                case KeyEvent.KEYCODE_4:
                case KeyEvent.KEYCODE_5:
                case KeyEvent.KEYCODE_6:
                case KeyEvent.KEYCODE_7:
                case KeyEvent.KEYCODE_8:
                case KeyEvent.KEYCODE_9:
                    inputConfirm[viewPos].setText(keyCodeToString(keyCode));

                    if (v.getId() != inputConfirm[5].getId()) {
                        inputConfirm[(viewPos + 1)].requestFocus();
                    }
                    break;
            }
        }

        return false;
    }

    /**
     * Converte código de KeyEvent em caracter.
     *
     * @param keyCode Código da tecla física pressionada
     * @return String refente a tecla digitada.
     */
    private String keyCodeToString(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                return "0";
            case KeyEvent.KEYCODE_1:
                return "1";
            case KeyEvent.KEYCODE_2:
                return "2";
            case KeyEvent.KEYCODE_3:
                return "3";
            case KeyEvent.KEYCODE_4:
                return "4";
            case KeyEvent.KEYCODE_5:
                return "5";
            case KeyEvent.KEYCODE_6:
                return "6";
            case KeyEvent.KEYCODE_7:
                return "7";
            case KeyEvent.KEYCODE_8:
                return "8";
            case KeyEvent.KEYCODE_9:
                return "9";
            default:
                return "";
        }
    }
}
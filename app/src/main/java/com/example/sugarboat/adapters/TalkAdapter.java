package com.example.sugarboat.adapters;

import java.util.*;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarboat.R;
import com.example.sugarboat.model.Message;

/***
 * Adaptador para o fragmento de chats.
 * Recarrega o layout e repassa os dados recebidos definindo assim cada aspecto de uma mensagem.
 *
 * @author GabrielRuneScape <gabrielfilipe@mail.ru>
 * @since 2024-09-14
 */
public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkHolder> {
    private ArrayList<Message> arrayList;

    /**
     * Construtor da classe que recebe um ArrayList com a informação a ser repassada
     *
     * @param data Todos os valores recebidos serão repassados para uma variavel interna.
     */
    public TalkAdapter(ArrayList<Message> data) {
        this.arrayList = data;
    }

    @Override
    /**
     * Inicia e carrega a RecyclerView.
     * @param parent View onde ela se encontra localizada.
     * @param viewTypo Variável não usada, mas necessária no metódo.
     */
    public TalkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_talk, parent, false);

        return new TalkHolder(view);
    }

    @Override
    /**
     * Define onde cada propriedade será aplicada.
     *
     * @param holder Classe filha.
     * @param position Posição que se encontra o valor no ArrayList.
     */
    public void onBindViewHolder(TalkHolder holder, int position) {
        Message mensagem = arrayList.get(position);

        holder.sender.setText(mensagem.getSender());
        holder.imageView.setImageResource(mensagem.getImage());
        holder.message.setText(mensagem.getMessage());
        holder.time.setText(mensagem.getTime());
    }

    @Override
    /**
     * Sobrescreve variável obrigatória retornando tamanho do ArrayList.
     */
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * Classe interna para definir onde irá cada informação nas view.
     */
    class TalkHolder extends RecyclerView.ViewHolder {
        TextView sender, message, time;
        ImageView imageView;

        /**
         * Construtor que irá receber um RecyclerView
         *
         * @param view O RecyclerView deve ser definido na classe pai.
         */
        public TalkHolder(View view) {
            super(view);

            this.sender = (TextView) view.findViewById(R.id.txtName);
            this.imageView = (ImageView) view.findViewById(R.id.imgProfile);
            this.message = (TextView) view.findViewById(R.id.txtMessage);
            this.time = (TextView) view.findViewById(R.id.txtTime);
        }
    }
}

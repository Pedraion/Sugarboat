package com.example.sugarboat;

import java.util.*;
import android.view.*;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.example.sugarboat.adapters.TalkAdapter;
import com.example.sugarboat.model.Message;

/***
 * Fragmentação da guia de chats.
 */
public class chatFragment extends Fragment {
    // SIMULAÇÃO DE CHATS
    private ArrayList<Message> data;
    private RecyclerView listView;

    public chatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Recarrega dados para o ListView
        data = new ArrayList<>();

        data.add(new Message("Alexandre Magno", "Bora expandir a Macedônia?", R.drawable.gold2,  Calendar.getInstance().getTime()));
        data.add(new Message("General Ulysses Grant", "Matei mais um pretinho KKKKK", R.drawable.profile_picture, new Date(2024, 2, 11, 4, 20)));
        data.add(new Message("Afonso I", "Estou farto desses mouros!", R.drawable.logosb6, new Date(2024, 9, 14, 22, 40)));
        data.add(new Message("Salah al-Deen", "Al-Quds é nossa!!", R.drawable.logosb1, new Date(2008, 3, 9, 13, 56)));
        data.add(new Message("Ernesto Guevara", "Acho que os gringos estão me seguindo...", R.drawable.logosb3, new Date(2008, 3, 9, 8, 47)));
        data.add(new Message("Átila, o Huno", "Meu irmão é um filho da puta, isso sim.", R.drawable.logosb5, new Date(2014, 5, 1, 7, 35)));


        TalkAdapter adapter = new TalkAdapter(data);

        listView = (RecyclerView) view.findViewById(R.id.listChats);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listView.setAdapter(adapter);

        return view;
    }
}
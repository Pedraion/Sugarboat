package com.example.sugarboat.view;

import java.io.Serializable;
import java.util.*;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.*;
import android.view.ViewGroup.LayoutParams;

import com.example.sugarboat.R;
import com.example.sugarboat.model.Interesting;
import com.example.sugarboat.model.Usuario;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;

public class AccountFragment extends Fragment {
    private Usuario usuario;

    private ImageView profileImage;
    private TextView profileInfo, profileBio, profileInterests;
    private ImageView editarPerfilBtn;
    private FlexboxLayout interestings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.imgProfileEdit);
        profileInfo = view.findViewById(R.id.txtUserBasicInfo);
        profileBio = view.findViewById(R.id.txtUserBasicBio);
        profileInterests = view.findViewById(R.id.txtUserBasicInterests);
        editarPerfilBtn = view.findViewById(R.id.floatingEditButton);
        interestings = view.findViewById(R.id.flexBoxInteresting);

        try {
           usuario = (Usuario) this.getArguments().getSerializable("usuarioData");

           profileImage.setImageBitmap(usuario.getImagem());
        } catch (Exception ex) {
            String loremipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum " +
                    "vulputate odio a consequat tempus. Nulla vulputate, enim ut lacinia vulputate, " +
                    "enim augue dapibus neque, vel sollicitudin purus turpis sed velit. Aliquam erat " +
                    "volutpat. Morbi posuere aliquet justo interdum congue.";

            String url = "https://encrypted-tbn1.gstatic.com/licensed-image?q=tbn:ANd9GcQ6i34GXt1wbpAmCDmEZ3lcmGSeDhYgf80ja2xNLlbUKSyrUhIcK-aIj1LpeaL0gDLT2gbPTpPof5H00cI";

            Calendar data = Calendar.getInstance();
            data.set(2001, 9, 11);

            usuario = new Usuario(1001, "João Sim", loremipsum, url, data);
            profileImage.setImageBitmap(usuario.getImagemFromURL());
        }

        profileInfo.setText(String.format("%s, %d anos - Faro", usuario.getNome(), 2024 - usuario.getData_nascimento().get(Calendar.YEAR)));
        profileBio.setText(usuario.getBiografia());

        if (usuario.getGenero_interesse().length() > 1) {
            profileInterests.setText(usuario.getGenero_interesse().substring(1, usuario.getGenero_interesse().length()));
        }

        getChips();

        editarPerfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuarioData", (Serializable) usuario);

                AccountEditFragment editFragment = new AccountEditFragment();
                editFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, editFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private ArrayList<Interesting> getChips() {
        ArrayList<Interesting> array = new ArrayList<>();

        if (usuario.getInteresses().isEmpty()) {
            array.add(new Interesting("Água", "#3090C7", "\uD83D\uDF04"));
            array.add(new Interesting("Fogo", "#E25822", "\uD83D\uDF02"));
            array.add(new Interesting("Ar", "#FBCE21", "\uD83D\uDF01"));
            array.add(new Interesting("Terra", "#149C5C", "\uD83D\uDF03"));
            array.add(new Interesting("Ouro", "#FDD017", "\u2609"));
            array.add(new Interesting("Prata", "#625D5D", "\u263D"));
            array.add(new Interesting("Mercúrio", "#FCA311", "\u236f"));
            array.add(new Interesting("Enxofre", "#F1DD38", "\uD83D\uDF0D"));
            array.add(new Interesting("Sal", "#6D597A", "\uD83D\uDF14"));
        } else {
            array = usuario.getInteresses();
        }

        for (Interesting index : array) {
            float dp = this.getContext().getResources().getDisplayMetrics().density;
            int height = (int) dp * 29;

            Chip chip = new Chip(this.getContext());

            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
            layoutParams.setMargins(8,8,8,8);

            chip.setLayoutParams(layoutParams);
            chip.setEnsureMinTouchTargetSize(false);

            chip.setTextSize(11);

            chip.setTextColor(Color.WHITE);
            chip.setChipStrokeColorResource(android.R.color.transparent);
            chip.setChipBackgroundColor(ColorStateList.valueOf(index.getColour()));

            chip.setChipCornerRadius(50);

            chip.setText(String.format("%s\t\t%s", index.getSimbolo(), index.getDescricao() ));

            interestings.addView(chip);
        }

        return array;
    }
}

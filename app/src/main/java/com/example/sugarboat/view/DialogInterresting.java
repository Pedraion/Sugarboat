package com.example.sugarboat.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sugarboat.R;
import com.example.sugarboat.interfaces.ParseInterrestings;
import com.example.sugarboat.model.Interesting;
import com.example.sugarboat.model.Usuario;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DialogInterresting extends Fragment implements ChipGroup.OnCheckedStateChangeListener {
    private static final String TAG = "DialogInterresting";
    private Usuario usuario;

    private ChipGroup flexbox;
    private List<Integer> selectionedPos;
    private ArrayList<Interesting> interestings;

    /*public DialogInterresting(@NonNull Activity context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_interestings_choose, new FlexboxLayout(context));

        this.setView(view);

        flexbox = view.findViewById(R.id.flexBoxDialogInteresting);
        Button confirmar = view.findViewById(R.id.bntDialogConfirm);

        interestings = getChips();
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }*/

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            usuario = (Usuario) this.getArguments().getSerializable("usuarioData");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            ex.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_interestings_choose, container, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth((Activity) getContext()) * 400, getScreenHeight((Activity) getContext()) * 400));

        Button confirmar = view.findViewById(R.id.bntDialogConfirm);
        flexbox = view.findViewById(R.id.flexBoxDialogInteresting);

        interestings = getChips();
        selectionedPos = flexbox.getCheckedChipIds();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        return view;
    }

    private void confirm() {
        Log.d(TAG, "onClick: transmiting inputted data");

        Bundle bundle = new Bundle();
        AccountEditFragment fragment = new AccountEditFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);

        try {
            String format = " Selected: {\n";
            ArrayList<Interesting> interesses = new ArrayList<>();

            for (Integer id : selectionedPos) {
                int pos = id;
                format += String.format("\t%d : {\n\t\tSimbolo: %s, Descrição: %s,\n\t}, \n", id, interestings.get(pos).getSimbolo(), interestings.get(pos).getDescricao());

                interesses.add(interestings.get(pos));
            }

            usuario.setInteresses(interesses);
            System.out.println("Está chegado: " + flexbox.getCheckedChipIds());

            bundle.putSerializable("usuarioData", (Serializable) usuario);
            fragment.setArguments(bundle);
            transaction.commit();
        } catch (Throwable ex) {
            Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    private ArrayList<Interesting> getChips() {
        Log.i(TAG, "Creating chips...");
        ArrayList<Interesting> array = new ArrayList<>();

        array.add(0, new Interesting("Água", "#3090C7", "#26739F", "\uD83D\uDF04"));
        array.add(1, new Interesting("Fogo", "#E25822", "#B4461B","\uD83D\uDF02"));
        array.add(2, new Interesting("Ar", "#FBCE21", "#C8A41A","\uD83D\uDF01"));
        array.add(3, new Interesting("Terra", "#149C5C", "#107c49", "\uD83D\uDF03"));
        array.add(4, new Interesting("Ouro", "#FDD017", "#CAA612", "\u2609"));
        array.add(5, new Interesting("Prata", "#625D5D", "#4e4a4a", "\u263D"));
        array.add(6, new Interesting("Mercúrio", "#FCA311", "#C9820D", "\u236f"));
        array.add(7, new Interesting("Enxofre", "#F1DD38", "#C0B02C", "\uD83D\uDF0D"));
        array.add(8, new Interesting("Sal", "#6D597A", "#574761", "\uD83D\uDF14"));

        for (Interesting index : array) {
            float dp = this.getContext().getResources().getDisplayMetrics().density;
            int height = (int) dp * 36;

            Chip chip = new Chip(this.getContext());
            chip.setId(array.indexOf(index));
            boolean checkedChip = false;

            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
            layoutParams.setMargins((int) dp * 8,16,(int) dp * 8,8);

            chip.setLayoutParams(layoutParams);
            chip.setEnsureMinTouchTargetSize(false);
            chip.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            chip.setTextSize(16);
            chip.setChipCornerRadius(50);
            chip.setText(String.format("%s\t\t%s", index.getSimbolo(), index.getDescricao()));

            chip.setTextColor(Color.WHITE);
            chip.setChipIconTint(ColorStateList.valueOf(Color.WHITE));
            chip.setChipStrokeColorResource(android.R.color.transparent);
            chip.setChipBackgroundColor(ColorStateList.valueOf(index.getColour()));

            for (Interesting pos : usuario.getInteresses()) {
                if (pos.getDescricao().equals(index.getDescricao())) {
                    checkedChip = true;

                    chip.setChipIcon(chip.getResources().getDrawable(R.drawable.baseline_done_24));
                    chip.setChipBackgroundColor(ColorStateList.valueOf(index.getPressedColour()));
                }
            }

            chip.setCheckable(true);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        chip.setChipIcon(chip.getResources().getDrawable(R.drawable.baseline_done_24));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(index.getPressedColour()));

                        chip.setChecked(true);
                    } else {
                        chip.setChipIcon(null);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(index.getColour()));

                        chip.setChecked(false);
                    }
                }
            });

            flexbox.addView(chip);

            if (checkedChip) {
                flexbox.check(chip.getId());
            }
        }

        flexbox.setOnCheckedStateChangeListener(this);


        return array;
    }

    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        String format = " Selected: {\n";

        for (Integer id : checkedIds) {
            format += String.format("\tID: %d, \n", id);
        }

        selectionedPos = checkedIds;
        System.out.println(format + "}");

    }
}

package com.example.sugarboat.view;

import java.io.*;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.*;
import android.net.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.provider.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

import com.example.sugarboat.R;
import com.example.sugarboat.interfaces.ParseInterrestings;
import com.example.sugarboat.model.Interesting;
import com.example.sugarboat.model.Usuario;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;

public class AccountEditFragment extends Fragment {
    private static final String TAG = "AccountEditFragment";
    private Usuario usuario;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImage;
    private EditText editPresentationName, editBiography, editLookingFor;
    private Spinner spinnerLookingFor;
    private Button saveButton, selectImageButton;
    private FlexboxLayout editInterests;

    private ArrayList<Interesting> interesses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profileImage);
        selectImageButton = view.findViewById(R.id.selectImageButton);
        editPresentationName = view.findViewById(R.id.editPresentationName);
        editBiography = view.findViewById(R.id.editBiography);
        editInterests = view.findViewById(R.id.editInterests);
        spinnerLookingFor = view.findViewById(R.id.editLookingFor);
        saveButton = view.findViewById(R.id.saveButton);

        TextView labelFlexboxInteresses = view.findViewById(R.id.labelFlexboxInteresses);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveData();
            }
        });

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sexual_orientation_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_popup_item);

        spinnerLookingFor.setAdapter(adapter);
        spinnerLookingFor.setSelection(0);*/

        try {
            usuario = (Usuario) this.getArguments().getSerializable("usuarioData");

            if (usuario != null) {
                editPresentationName.setText(usuario.getNome());
                editBiography.setText(usuario.getBiografia());
                //editInterests.setText(usuario.getGenero_interesse());

                if (!usuario.getInteresses().isEmpty()) {
                    labelFlexboxInteresses.setActivated(false);
                    labelFlexboxInteresses.setVisibility(View.GONE);
                    setChipsToFlexbox();
                } else {
                    labelFlexboxInteresses.setVisibility(View.VISIBLE);
                }

                profileImage.setImageBitmap(usuario.getImagem());

                if (usuario.getGenero_interesse().length() > 2) {
                    int pos = Integer.parseInt(usuario.getGenero_interesse().charAt(0)+"");
                    spinnerLookingFor.setSelection(pos);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

            ex.printStackTrace();
        }

        editInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening new fragment.");


                /*AlertDialog.Builder builder = new AlertDialog.Builder(AccountEditFragment.this.getContext(), R.style.Theme_Sugarboat);
                View layout = LayoutInflater.from(AccountEditFragment.this.getContext()).inflate(R.layout.dialog_interestings_choose, (FlexboxLayout) v.findViewById(R.id.flexBoxDialogInteresting));
                builder.setView(layout);
                builder.setTitle("Qual titulo aparecerá aqui");*/

                //dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog_bg);
                //dialog.getWindow().setLayout(getScreenWidth(v.getContext()), ViewGroup.LayoutParams.WRAP_CONTENT);

                //dialog.show();

                //builder.getSelectionedPos().forEach(id -> System.out.println("ID: " + id));

                /*DialogInterresting dialog = new DialogInterresting();

                FragmentManager fragmentManager =  getParentFragmentManager();
                dialog.show(fragmentManager, "DialogInterresting");*/

                DialogInterresting fragment = new DialogInterresting();
                Bundle bundle = new Bundle();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(this.getClass().toString());

                bundle.putSerializable("usuarioData", (Serializable) usuario);
                fragment.setArguments(bundle);
                transaction.commit();
            }
        }); /*  .setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editInterests.setText("Está em foco");
                    editInterests.setShowSoftInputOnFocus(false);

                    Dialog_Interresting dialog = new Dialog_Interresting(view.getContext());
                    dialog.show();
                } else {
                    editInterests.setText("Foi focado");
                    editInterests.setShowSoftInputOnFocus(true);
                }
            }
        });*/

        spinnerLookingFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedView = (TextView) parent.getChildAt(0);

                if (selectedView != null) {
                    if (position == 0) {
                        selectedView.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    } else {
                        selectedView.setTextColor(getResources().getColor(android.R.color.black));
                        usuario.setGenero_interesse(position+selectedView.getText().toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static int getScreenWidth(Context activity) {
        Rect displayRectangle = new Rect();
        Window window =((Activity) activity).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        return (int) (displayRectangle.width() * 0.8f);
    }

    public static int getScreenHeight(Context activity) {
        Rect displayRectangle = new Rect();
        Window window =((Activity) activity).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        return (int) (displayRectangle.width() * 0.7f);
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
    }

    private void onSaveData() {
        usuario.setNome(editPresentationName.getText().toString().trim());
        usuario.setBiografia(editBiography.getText().toString().trim());
        //usuario.setGenero_interesse(editInterests.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putSerializable("usuarioData", usuario);

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
                usuario.setImagem(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setChipsToFlexbox() {
        for (Interesting pos: usuario.getInteresses()) {
            float dp = this.getContext().getResources().getDisplayMetrics().density;
            int height = (int) dp * 32;
            int marginY = (int) dp * 4;

            Chip chip = new Chip(this.getContext());

            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
            layoutParams.setMargins(12,marginY,12,marginY);

            chip.setLayoutParams(layoutParams);
            chip.setEnsureMinTouchTargetSize(false);

            chip.setTextSize(13);
            chip.setClickable(false);

            chip.setTextColor(Color.WHITE);
            chip.setChipStrokeColorResource(android.R.color.transparent);
            chip.setChipBackgroundColor(ColorStateList.valueOf(pos.getColour()));

            chip.setChipCornerRadius(50);

            chip.setText(String.format("%s\t\t%s", pos.getSimbolo(), pos.getDescricao() ));

            editInterests.addView(chip);
        }
    }
}

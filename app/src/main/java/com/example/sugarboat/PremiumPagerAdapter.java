package com.example.sugarboat;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class PremiumPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public PremiumPagerAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 3; // Número de páginas
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.premium_page, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        Button aderirButton = view.findViewById(R.id.aderir_button); // Assumindo que você deu um ID ao botão

        final String fullDescription; // Declare a variável como final

        switch (position) {
            case 0:
                view.setBackgroundResource(R.drawable.sb_gold_bg);
                imageView.setImageResource(R.drawable.gold2);

                String goldText = "Sugarboat Premium Gold";
                SpannableString goldSpannable = new SpannableString(goldText);
                int goldStart = goldText.indexOf("Gold");
                int goldEnd = goldStart + "Gold".length();
                ForegroundColorSpan goldSpan = new ForegroundColorSpan(Color.parseColor("#FFD500"));
                goldSpannable.setSpan(goldSpan, goldStart, goldEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                title.setText(goldSpannable);

                // Aplica animação de brilho
                Animation blinkAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.blink);
                title.setAnimation(blinkAnimation);

                description.setText("• Gostos ilimitados\n\n• No-Ads\n\n• Turn Back\n\n• 3 Super Likes");

                // Todas as características do plano Gold
                fullDescription = "• Gostos ilimitados\n\n• No-Ads\n\n• Turn Back\n\n• 3 Super Likes";
                break;
            case 1:
                view.setBackgroundResource(R.drawable.sb_platin);
                imageView.setImageResource(R.drawable.logosb3);

                String platText = "Sugarboat Premium Platinum";
                SpannableString platSpannable = new SpannableString(platText);
                int platStart = platText.indexOf("Platinum");
                int platEnd = platStart + "Platinum".length();
                ForegroundColorSpan platSpan = new ForegroundColorSpan(Color.parseColor("#9E9E9E"));
                platSpannable.setSpan(platSpan, platStart, platEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                title.setText(platSpannable);

                // Aplica animação de brilho
                blinkAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.blink);
                title.setAnimation(blinkAnimation);

                description.setText("• 5 Super Likes\n\n• Reward's\n\n• Contacto/s match -5xs\n\n• Ver quem gosta de si");

                // Todas as características do plano Platinum
                fullDescription = "• Gostos ilimitados\n\n• No-Ads\n\n• Turn back\n\n• 5 Super Likes\n\n• 10 min profile boost\n\n• Reward's\n\n• Contacto/s match -5xs\n\n• Ver quem gosta de si";
                break;
            case 2:
                view.setBackgroundResource(R.drawable.sb_club);
                imageView.setImageResource(R.drawable.logosb6);
                title.setText("Sugarboat Premium Club");

                String clubText = "Sugarboat Premium Club";
                SpannableString clubSpannable = new SpannableString(clubText);
                int clubStart = clubText.indexOf("Club");
                int clubEnd = clubStart + "Club".length();
                ForegroundColorSpan clubSpan = new ForegroundColorSpan(Color.parseColor("#A962FF"));
                clubSpannable.setSpan(clubSpan, clubStart, clubEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                title.setText(clubSpannable);

                // Aplica animação de brilho
                blinkAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.blink);
                title.setAnimation(blinkAnimation);

                description.setText("• 10 Super Likes\n\n• Contacto/s match -ilmt\n\n• Ver quem gosta de ti\n\n• Selo exclusivo SB Club");

                // Todas as características do plano Club
                fullDescription = "• Gostos ilimitados\n\n• No-Ads\n\n• Turn back\n\n• 15 Super Likes\n\n• 10 min profile boost seminal\n\n• Reward's\n\n• Contacto/s match -ilmt\n\n• Ver quem gosta de si\n\n• Reward's (exclusivos)\n\n• Selo exclusivo SB Club\n\n• Eventos Limitados";
                break;
            default:
                fullDescription = ""; // Padrão para evitar erros
                break;
        }

        aderirButton.setOnClickListener(v -> showPlanDetails(fullDescription, position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void showPlanDetails(String details, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = layoutInflater;
        View dialogView = inflater.inflate(R.layout.dialog_premium_details, null);
        builder.setView(dialogView);

        ImageView dialogImageView = dialogView.findViewById(R.id.dialogImageView);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        LinearLayout dialogRootLayout = dialogView.findViewById(R.id.dialogRootLayout);

        Animation blinkAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.blink);

        switch (position) {
            case 0:
                dialogImageView.setImageResource(R.drawable.gold2);
                dialogRootLayout.setBackgroundResource(R.drawable.sb_gold_bg2);

                String goldText = "Sugarboat Premium Gold";
                SpannableString goldSpannable = new SpannableString(goldText);
                int goldStart = goldText.indexOf("Gold");
                int goldEnd = goldStart + "Gold".length();
                ForegroundColorSpan goldSpan = new ForegroundColorSpan(Color.parseColor("#FFD500"));
                goldSpannable.setSpan(goldSpan, goldStart, goldEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialogTitle.setText(goldSpannable);
                dialogTitle.setAnimation(blinkAnimation);
                break;
            case 1:
                dialogImageView.setImageResource(R.drawable.logosb3);
                dialogRootLayout.setBackgroundResource(R.drawable.sb_platin2);

                String platText = "Sugarboat Premium Platinum";
                SpannableString platSpannable = new SpannableString(platText);
                int platStart = platText.indexOf("Platinum");
                int platEnd = platStart + "Platinum".length();
                ForegroundColorSpan platSpan = new ForegroundColorSpan(Color.parseColor("#9E9E9E"));
                platSpannable.setSpan(platSpan, platStart, platEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialogTitle.setText(platSpannable);
                dialogTitle.setAnimation(blinkAnimation);
                break;
            case 2:
                dialogImageView.setImageResource(R.drawable.logosb6);
                dialogRootLayout.setBackgroundResource(R.drawable.sb_club2);

                String clubText = "Sugarboat Premium Club";
                SpannableString clubSpannable = new SpannableString(clubText);
                int clubStart = clubText.indexOf("Club");
                int clubEnd = clubStart + "Club".length();
                ForegroundColorSpan clubSpan = new ForegroundColorSpan(Color.parseColor("#A962FF"));
                clubSpannable.setSpan(clubSpan, clubStart, clubEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialogTitle.setText(clubSpannable);
                dialogTitle.setAnimation(blinkAnimation);
                break;
        }

        dialogDescription.setText(details);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

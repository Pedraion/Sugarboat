package com.example.sugarboat.utils;

import java.io.*;
import java.net.*;
import android.os.*;
import android.widget.*;
import android.graphics.*;

/***
 * Recarrega imagem de um repositório web, e converte para Bitmap
 *
 * @author kalyan pvs <https://stackoverflow.com/questions/18953632/how-to-set-image-from-url-for-imageview>
 * @since 2024-09-21
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private ImageView imageView;

    /**
     * Construtor padrão
     *
     * @param url Link da imagem a ser convertida
     */
    public ImageLoadTask(String url) {
        this.url = url;
    }

    /**
     * Construtor com recarga de ImageView
     *
     * @param url Link da imagem a ser convertida
     * @param imageView ImageView a ser carregado a imagem;
     */
    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    /**
     * Converte imagem de forma assincrona
     *
     * @param params The parameters of the task.
     *
     * @return Imagem convertida e pronta ser carregada
     */
    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Tarefa realizada após a operação de conversão
     *
     * @param result The result of the operation computed by {@link #doInBackground}.     *
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (imageView != null) {
            imageView.setImageBitmap(result);
        }
    }

}

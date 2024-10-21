package com.example.sugarboat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.sugarboat.utils.ImageLoadTask;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/***
 * Classe modelo baseado na tabela perfil_usuario do banco de dados.
 * Define getters de cada valor recebido no construtor.
 *
 * @author GabrielRuneScape <gabrielfilipe@mail.ru>
 * @since 2024-09-21
 */
public class Usuario implements Serializable {
    private long ID;
    private String nome;
    private String telefone;
    private String sexo;
    private Calendar data_nascimento;
    private String biografia;
    private String orientacao_sexual;
    private String genero_interesse;
    private String imagemURL;
    private ArrayList<Interesting> interesses;

    private transient Bitmap imagem; // Esta variável não está no banco de dados

    /**
     * Construtor básico
     *
     * @param ID Código identificador
     * @param nome Nome de exibição
     */
    public Usuario(long ID, String nome) {
        this.ID = ID;
        this.nome = nome;

        // Inicia valores padrões
        this.telefone = "";
        this.sexo = "";
        this.data_nascimento = Calendar.getInstance();
        this.biografia = "";
        this.orientacao_sexual = "";
        this.genero_interesse = "";
        this.imagemURL = "https://s3.amazonaws.com/org.kellenberg.www-media/wp-content/uploads/2019/10/04112751/facebook-user-icon-19.jpg";
        this.interesses = new ArrayList<Interesting>();
        this.imagem = getImagemFromURL();
    }

    /**
     * Construtor básico
     *
     * @param ID Código identificador
     * @param nome Nome de exibição
     * @param biografia Descrição de perfil
     */
    public Usuario(long ID, String nome, String biografia) {
        this.ID = ID;
        this.nome = nome;
        this.biografia = biografia;

        // Inicia valores padrões
        this.telefone = "";
        this.sexo = "";
        this.data_nascimento = Calendar.getInstance();
        this.orientacao_sexual = "";
        this.genero_interesse = "";
        this.imagemURL = "https://s3.amazonaws.com/org.kellenberg.www-media/wp-content/uploads/2019/10/04112751/facebook-user-icon-19.jpg";
        this.interesses = new ArrayList<Interesting>();
        this.imagem = getImagemFromURL();
    }

    /**
     * Construtor com informações básicas
     *
     * @param ID Código identificador
     * @param nome Nome de exibição
     * @param biografia Descrição de perfil
     * @param imagemURL Link web de imagem de perfil
     */
    public Usuario(long ID, String nome, String biografia, String imagemURL) {
        this.ID = ID;
        this.nome = nome;
        this.biografia = biografia;
        this.imagemURL = imagemURL;

        // Inicia valores padrões
        this.telefone = "";
        this.sexo = "";
        this.data_nascimento = Calendar.getInstance();
        this.orientacao_sexual = "";
        this.genero_interesse = "";
        this.interesses = new ArrayList<Interesting>();
        this.imagem = getImagemFromURL();
    }

    /**
     * Construtor com informações básicas
     *
     * @param ID Código identificador
     * @param nome Nome de exibição
     * @param biografia Descrição de perfil
     * @param imagemURL Link web de imagem de perfil
     * @param data_nascimento Data de nascimento
     */
    public Usuario(long ID, String nome, String biografia, String imagemURL, Calendar data_nascimento) {
        this.ID = ID;
        this.nome = nome;
        this.biografia = biografia;
        this.imagemURL = imagemURL;
        this.data_nascimento = data_nascimento;

        // Inicia valores padrões
        this.telefone = "";
        this.sexo = "";
        this.orientacao_sexual = "";
        this.genero_interesse = "";
        this.interesses = new ArrayList<Interesting>();
        this.imagem = getImagemFromURL();
    }

    /**
     * Construtor completo
     *
     * @param nome Nome de exibição
     * @param biografia Descrição de perfil
     * @param imagemURL Link web de imagem de perfil
     * @param data_nascimento Data de nascimento
     * @param telefone Número de telefone
     * @param sexo Organismo biologico
     * @param orientacao_sexual Identificação de gênero
     * @param genero_interesse Preferência sexual
     */
    public Usuario(long ID, String nome, String biografia, String imagemURL, Calendar data_nascimento, String telefone, String sexo, String orientacao_sexual, String genero_interesse) {
        this.ID = ID;
        this.nome = nome;
        this.biografia = biografia;
        this.imagemURL = imagemURL;
        this.data_nascimento = data_nascimento;
        this.telefone = telefone;
        this.sexo = sexo;
        this.orientacao_sexual = orientacao_sexual;
        this.genero_interesse = genero_interesse;

        // Inicia valores padrões
        this.interesses = new ArrayList<Interesting>();
        this.imagem = getImagemFromURL();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Calendar getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Calendar data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getOrientacao_sexual() {
        return orientacao_sexual;
    }

    public void setOrientacao_sexual(String orientacao_sexual) {
        this.orientacao_sexual = orientacao_sexual;
    }

    public String getGenero_interesse() {
        return genero_interesse;
    }

    public void setGenero_interesse(String genero_interesse) {
        this.genero_interesse = genero_interesse;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    /**
     * Pega e retorna uma imagem da internet
     *
     * @return Endereço, se valido, com imagem em formato Bitmap
     */
    public Bitmap getImagemFromURL() {
        try {
            ImageLoadTask task = new ImageLoadTask(imagemURL);
            task.execute();

            imagem = task.get();

        } catch (CancellationException | ExecutionException | InterruptedException ex) {
            Log.e(Usuario.class.toString(), ex.getMessage());
            ex.printStackTrace();

            imagem = null;
        }

        return imagem;
    }

    public ArrayList<Interesting> getInteresses() {
        return interesses;
    }

    public void setInteresses(ArrayList<Interesting> interesses) {
        this.interesses = interesses;
    }

    public String getImagemURL() {
        return imagemURL;
    }

    public void setImagemURL(String imagemURL) {
        this.imagemURL = imagemURL;
    }
}

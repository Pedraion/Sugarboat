package com.example.sugarboat.model;

import java.io.Serializable;
import android.graphics.Color;
/***
 * Classe modelo baseado na tabela ________ do banco de dados.
 * Define getters de cada valor recebido no construtor.
 *
 * @author GabrielRuneScape <gabrielfilipe@mail.ru>
 * @since 2024-09-21
 */
public class Interesting implements Serializable {
    private long ID;
    private String descricao;
    private String simbolo;
    private String categoria;
    private int colour;
    private int pressedColour;

    /**
     * Construtor básico
     *
     * @param descricao Tipo de interesse
     * @param colour Cor de identificação
     */
    public Interesting(String descricao, int colour) {
        this.descricao = descricao;
        this.colour = colour;
    }
    /**
     * Construtor básico
     *
     * @param descricao Tipo de interesse
     * @param colour Cor de identificação
     */
    public Interesting(String descricao, String colour) {
        this.descricao = descricao;
        this.colour = Color.parseColor(colour);
    }

    /**
     * Construtor intermediário
     *
     * @param descricao Tipo de interesse
     * @param colour Cor de identificação
     * @param simbolo Emojis e afins em unicode
     */
    public Interesting(String descricao, String colour, String simbolo) {
        this.descricao = descricao;
        this.colour = Color.parseColor(colour);
        this.simbolo = simbolo;
    }

    /**
     * Construtor intermediário
     *
     * @param descricao Tipo de interesse
     * @param colour Cor de identificação
     * @parma presed Cor ao estar seleccionado
     * @param simbolo Emojis e afins em unicode
     */
    public Interesting(String descricao, String colour, String pressed, String simbolo) {
        this.descricao = descricao;
        this.colour = Color.parseColor(colour);
        this.pressedColour = Color.parseColor(pressed);
        this.simbolo = simbolo;
    }

    /**
     * Construtor completo
     *
     * @param descricao Tipo de interesse
     * @param colour Cor de identifação
     * @param simbolo Emojis e afins em formato unicode
     * @param categoria Tipo onde se enquadra o interesse
     * @param ID Código de identifação
     */
    public Interesting(String descricao, String colour, String simbolo, String categoria, long ID) {
        this.descricao = descricao;
        this.colour = Color.parseColor(colour);
        this.simbolo = simbolo;
        this.categoria = categoria;
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getPressedColour() {
        if (pressedColour == 0) {
            return this.colour;
        } else {
            return this.pressedColour;
        }
    }

    public void setPressedColour(int pressedColour) {
        this.pressedColour = pressedColour;
    }

    /**
     * Convert Hex to Integer colour
     *
     * @param colour string colour
     */
    public  void setColourHex(String colour) {
        this.colour = Color.parseColor(colour);
    }

    /**
     * Convert Hex to Integer colour
     *
     * @param colour string colour
     */
    public  void setPressedColourHex(String colour) {
        this.pressedColour = Color.parseColor(colour);
    }
}

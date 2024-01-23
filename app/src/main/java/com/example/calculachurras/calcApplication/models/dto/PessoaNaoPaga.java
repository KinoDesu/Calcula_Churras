package com.example.calculachurras.calcApplication.models.dto;

import com.example.calculachurras.calcApplication.models.Pessoa;

import java.text.DecimalFormat;

public class PessoaNaoPaga {
    private String nome;
    private double valorGasto =0;
    private double valorReceber=0;

    DecimalFormat df = new DecimalFormat("0.00");

    public PessoaNaoPaga() {
    }

    public PessoaNaoPaga(Pessoa pessoa) {
        this.nome = pessoa.getNome();
        this.valorGasto = pessoa.getValorGasto();
        this.valorReceber = pessoa.getValorReceber();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(double valorGasto) {
        this.valorGasto = valorGasto;
    }

    public double getValorReceber() {
        return valorReceber;
    }

    public void setValorReceber(double valorReceber) {
        this.valorReceber = valorReceber;
    }

    @Override
    public String toString() {
        return nome +
                "\n-> Valor Gasto: R$" + df.format(valorGasto) +
                "\n-> Valor a Receber: R$ " + df.format(valorReceber);
    }
}

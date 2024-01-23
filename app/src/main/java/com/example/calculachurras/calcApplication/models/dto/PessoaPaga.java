package com.example.calculachurras.calcApplication.models.dto;

import com.example.calculachurras.calcApplication.models.Pessoa;

import java.text.DecimalFormat;

public class PessoaPaga {
    private String nome;
    private double valorGasto =0;
    private double valorPagar=0;

    DecimalFormat df = new DecimalFormat("0.00");

    public PessoaPaga() {
    }

    public PessoaPaga(Pessoa pessoa) {
        this.nome = pessoa.getNome();
        this.valorGasto = pessoa.getValorGasto();
        this.valorPagar = pessoa.getValorPagar();
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

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    @Override
    public String toString() {
        return nome +
                "\n-> Valor Gasto: R$" + df.format(valorGasto) +
                "\n-> Valor a Pagar: R$ " + df.format(valorPagar);
    }
}

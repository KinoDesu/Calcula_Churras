package com.example.calculachurras.calcApplication.models;

import java.text.DecimalFormat;

public class Pessoa {
    private int id;
    private String nome;
    private double valorGasto =0;
    private double valorReceber=0;
    private double valorPagar=0;
    private boolean bancario=false;

    public Pessoa() {
    }

    public Pessoa(String nome, double valorGasto) {
        this.nome = nome;
        this.valorGasto = valorGasto;
        this.valorReceber = 0;
        this.valorPagar = 0;
        this.bancario = false;
    }


    public Pessoa(String nome, double valorGasto, double valorReceber, double valorPagar, boolean bancario) {
        this.nome = nome;
        this.valorGasto = valorGasto;
        this.valorReceber = valorReceber;
        this.valorPagar = valorPagar;
        this.bancario = bancario;
    }

    public Pessoa(int id, String nome, double valorGasto, double valorReceber, double valorPagar, boolean bancario) {
        this.id = id;
        this.nome = nome;
        this.valorGasto = valorGasto;
        this.valorReceber = valorReceber;
        this.valorPagar = valorPagar;
        this.bancario = bancario;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public boolean isBancario() {
        return bancario;
    }

    public void setBancario(boolean bancario) {
        this.bancario = bancario;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        if(!bancario){
            return  nome +
                    "\n-> Valor Gasto: R$ " + df.format(valorGasto) +
                    "\n-> Valor a Receber: R$ " + df.format(valorReceber) +
                    "\n-> Valor a Pagar: R$ " + df.format(valorPagar)+
                    "\n-> Total Pago: R$ " + df.format(valorPagar+valorGasto-valorReceber);
        }else {
            return  nome +
                    "\n-> Valor Gasto: R$ " + df.format(valorGasto) +
                    "\n-> Deve Manter: R$ " + df.format(valorReceber) +
                    "\n-> Total Pago: R$ " + df.format(valorPagar+valorGasto-valorReceber);
        }
    }
}

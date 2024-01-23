package com.example.calculachurras.calcApplication.models.dto;

import com.example.calculachurras.calcApplication.models.Pessoa;

public class PessoaSpinnerDto {
    private int id;
    private String nome;

    public PessoaSpinnerDto(){}

    public PessoaSpinnerDto(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
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

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}

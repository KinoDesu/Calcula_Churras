package com.example.calculachurras.calcApplication.Repository;

import com.example.calculachurras.calcApplication.models.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaRepository {

    private static PessoaRepository instance;
    private int id = 1;
    private List<Pessoa> listaPessoas;

    private PessoaRepository() {
        listaPessoas = new ArrayList<>();
    }

    public static synchronized PessoaRepository getInstance() {
        if (instance == null) {
            instance = new PessoaRepository();
        }
        return instance;
    }

    public List<Pessoa> getListaDePessoas() {
        return listaPessoas;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        pessoa.setId(id++);
        listaPessoas.add(pessoa);
    }

    public void setBancario(int id){
        for (Pessoa p:listaPessoas) {
            if(p.getId() == id){
                p.setBancario(true);
                break;
            }
        }
    }

    public void clearList(){
        listaPessoas.clear();
        id = 1;
    }
}

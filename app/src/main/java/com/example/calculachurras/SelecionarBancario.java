package com.example.calculachurras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calculachurras.calcApplication.Repository.PessoaRepository;
import com.example.calculachurras.R;
import com.example.calculachurras.calcApplication.models.Pessoa;
import com.example.calculachurras.calcApplication.models.dto.PessoaSpinnerDto;

import java.util.ArrayList;
import java.util.List;

public class SelecionarBancario extends AppCompatActivity {

    private Spinner spinner;
    PessoaRepository pessoaRepository = PessoaRepository.getInstance();
    private List<Pessoa> listaPessoas = pessoaRepository.getListaDePessoas();
    private PessoaSpinnerDto pessoaDtoSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_bancario);

        spinner = findViewById(R.id.spinner);

        List<PessoaSpinnerDto> listaSpinner = criarListaSpinner(listaPessoas);

        ArrayAdapter<PessoaSpinnerDto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ação a ser realizada quando uma pessoa é selecionada no Spinner
                pessoaDtoSelecionada = (PessoaSpinnerDto) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(SelecionarBancario.this, "Selecione alguém", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<PessoaSpinnerDto> criarListaSpinner(List<Pessoa> pessoas){
        List<PessoaSpinnerDto> listaSpinner = new ArrayList<>();
        for (Pessoa p:pessoas) {
            listaSpinner.add(new PessoaSpinnerDto(p));
        }

        return listaSpinner;
    }

    public void confirmar(View view){

        Pessoa pessoaSelecionada = new Pessoa();

        for (Pessoa p:listaPessoas) {
            if(p.getId()==pessoaDtoSelecionada.getId()){
                pessoaRepository.setBancario(pessoaDtoSelecionada.getId());
                pessoaSelecionada = p;
                break;
            }
        }
        Toast.makeText(SelecionarBancario.this, "Selecionado: " + pessoaSelecionada.getNome(), Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
    }
}
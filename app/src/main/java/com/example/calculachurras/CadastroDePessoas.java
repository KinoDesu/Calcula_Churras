package com.example.calculachurras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculachurras.calcApplication.Repository.PessoaRepository;
import com.example.calculachurras.calcApplication.models.Pessoa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CadastroDePessoas extends AppCompatActivity {

    private EditText nomePessoa;
    private EditText valorGasto;
    private TextView totalChurras;
    private TextView totalPessoas;
    private TextView valorCada;
    private Button btnSalvar;
    private Button btnFinalizar;

    PessoaRepository repository = PessoaRepository.getInstance();

    private List<Pessoa> listaPessoas = new ArrayList<>();
    private double infoValorCada = 0;
    private double infoTotalChurras = 0;

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_pessoas);


        listaPessoas = repository.getListaDePessoas();
        getValorChurras();
        getValorCada();

        nomePessoa = findViewById(R.id.editNomePessoa);
        valorGasto = findViewById(R.id.editValorGasto);
        totalChurras = findViewById(R.id.txtTotalChurras);
        totalPessoas = findViewById(R.id.txtQuantidadePessoa);
        valorCada = findViewById(R.id.txtValorCada);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        totalChurras.setText(totalChurras.getText().toString() + df.format(infoTotalChurras));
        totalPessoas.setText(totalPessoas.getText().toString() + String.valueOf(listaPessoas.size()) + " pessoas");
        valorCada.setText(valorCada.getText().toString() + df.format(infoValorCada));
    }

    private void getValorChurras() {
        for (Pessoa p : listaPessoas) {
            infoTotalChurras += p.getValorGasto();
        }
    }

    private void getValorCada() {
        if (listaPessoas.size() != 0) {
            infoValorCada = infoTotalChurras / listaPessoas.size();
        }
    }

    public void salvarPessoa(View view) {

        if (nomePessoa.getText().toString().trim().length() != 0 && valorGasto.getText().toString().trim().length() != 0) {
            String nome = nomePessoa.getText().toString();
            double gasto = Double.parseDouble(valorGasto.getText().toString());

            Pessoa pessoa = new Pessoa(nome, gasto);
            repository.adicionarPessoa(pessoa);

            Toast.makeText(CadastroDePessoas.this, nome + " foi salvo(a)!", Toast.LENGTH_SHORT).show();

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(this, CadastroDePessoas.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
            finish();
        } else {
            Toast.makeText(CadastroDePessoas.this, "Preencha todas as informações!", Toast.LENGTH_LONG).show();
        }
    }

    public void finalizarCadastros(View view) {

        if (listaPessoas.size() >= 2 || (listaPessoas.size() >= 1 && nomePessoa.getText().toString().trim().length() != 0 && valorGasto.getText().toString().trim().length() != 0)) {

            if (nomePessoa.getText().toString().trim().length() != 0 && valorGasto.getText().toString().trim().length() != 0) {
                String nome = nomePessoa.getText().toString();
                double gasto = Double.parseDouble(valorGasto.getText().toString());

                Pessoa pessoa = new Pessoa(nome, gasto);
                repository.adicionarPessoa(pessoa);

                Toast.makeText(CadastroDePessoas.this, nome + " foi salvo(a)!", Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Intent intent = new Intent(this, SelecionarBancario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
                finish();
            } else {
                Intent intent = new Intent(this, SelecionarBancario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
                finish();
            }
        } else {
            Toast.makeText(CadastroDePessoas.this, "Deve ter no mínimo 2 participantes!", Toast.LENGTH_SHORT).show();
        }

    }
}
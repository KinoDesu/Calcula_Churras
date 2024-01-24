package com.example.calculachurras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculachurras.calcApplication.Repository.PessoaRepository;
import com.example.calculachurras.calcApplication.models.Pessoa;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private String ultimoCaracterDigitado = "";


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

        valorGasto.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text = valorGasto.getText().toString();

                // Remover qualquer vírgula existente
                text = text.replace(",", "");
                text = text.replace("R$ ", "");

                if (text.length() > 0 && text.charAt(0) == '0') {
                    text = text.substring(1);
                }

                // Adicionar vírgula antes dos dois últimos dígitos
                int length = text.length();
                if (length > 2) {
                    text = "R$ " + text.substring(0, length - 2) + "," + text.substring(length - 2);
                } else {
                    // Caso tenha menos de dois dígitos, adicione a vírgula no início
                    text = "R$ " + "," + text;
                }
                // Atualizar o texto do EditText
                valorGasto.removeTextChangedListener(this);
                valorGasto.setText(text);


                // Mover o cursor para o final
                int selectionIndex = text.length();
                valorGasto.setSelection(selectionIndex);

                valorGasto.addTextChangedListener(this);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
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
            double gasto = Double.parseDouble(
                    String.valueOf(
                            getDoubleString(valorGasto.getText().toString()
                            )
                    )
            );

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

    private String getDoubleString(String texto){
        texto = texto.replace("R$ ","");
        texto = texto.replace(",",".");

        valorCada.setText(texto);

        return texto;
    }

    public void finalizarCadastros(View view) {

        if (listaPessoas.size() >= 2 || (listaPessoas.size() >= 1 && nomePessoa.getText().toString().trim().length() != 0 && valorGasto.getText().toString().trim().length() != 0)) {

            if (nomePessoa.getText().toString().trim().length() != 0 && valorGasto.getText().toString().trim().length() != 0) {
                String nome = nomePessoa.getText().toString();
                double gasto = Double.parseDouble(
                        String.valueOf(
                                getDoubleString(valorGasto.getText().toString()
                                )
                        )
                );

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
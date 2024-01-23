package com.example.calculachurras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calculachurras.calcApplication.Repository.PessoaRepository;
import com.example.calculachurras.R;
import com.example.calculachurras.calcApplication.models.Pessoa;
import com.example.calculachurras.calcApplication.models.dto.PessoaNaoPaga;
import com.example.calculachurras.calcApplication.models.dto.PessoaPaga;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    PessoaRepository repository = PessoaRepository.getInstance();
    private List<Pessoa> listaPessoas = repository.getListaDePessoas();
    DecimalFormat df = new DecimalFormat("0.00");

    private TextView viewTotalChurras;
    private TextView viewValorCada;
    private TextView viewBancario;
    private TextView viewRecebido;
    private TextView viewDeveManter;
    private LinearLayout linearLayout;

    private double infoValorCada = 0;
    private double infoTotalChurras = 0;
    private double infoDeveManter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        linearLayout = findViewById(R.id.linear_layout);

        getValorChurras();
        getValorCada();

        viewTotalChurras = findViewById(R.id.txtTotalChurrascoResult);
        viewValorCada = findViewById(R.id.txtValorCadaResult);
        viewBancario = findViewById(R.id.txtBancarioNome);
        viewRecebido = findViewById(R.id.txtRecebido);
        viewDeveManter = findViewById(R.id.txtDeveManter);

        viewTotalChurras.setText("R$ " + df.format(infoTotalChurras));
        viewValorCada.setText("R$ " + df.format(infoValorCada));

        Pessoa bancario = listaPessoas.get(0);
        int indBancario = 0;

        for (Pessoa p : listaPessoas) {
            if (p.isBancario()) {
                bancario = p;
                break;
            }
            indBancario++;
        }

        viewBancario.setText(bancario.getNome().toUpperCase());

        bancario.setValorReceber(bancario.getValorGasto() - infoValorCada);
        listaPessoas.remove(indBancario);

        infoDeveManter = bancario.getValorReceber();
        viewDeveManter.setText(viewDeveManter.getText().toString() + df.format(infoDeveManter));

        List<PessoaNaoPaga> naoPaga = new ArrayList<>();
        List<PessoaPaga> paga = new ArrayList<>();

        for (Pessoa pessoa : listaPessoas) {
            double aPagar = infoValorCada - pessoa.getValorGasto();
            if (aPagar <= 0) {
                pessoa.setValorReceber(pessoa.getValorGasto() - infoValorCada);
                PessoaNaoPaga pessoaNaoPaga = new PessoaNaoPaga(pessoa);
                naoPaga.add(pessoaNaoPaga);
            } else {
                pessoa.setValorPagar(aPagar);
                PessoaPaga pessoaPaga = new PessoaPaga(pessoa);
                paga.add(pessoaPaga);
            }
        }

        double recebido = infoValorCada * (listaPessoas.size() + 1 - (naoPaga.size() + 1));
        viewRecebido.setText(viewRecebido.getText() + df.format(recebido));

        for (Pessoa pessoa : listaPessoas) {
            double aPagar = infoValorCada - pessoa.getValorGasto();
            if (aPagar <= 0) {
                recebido -= infoValorCada;
            }
        }
        escreverNaoPagantes(naoPaga);
        escreverPagantes(paga);
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

    private void escreverNaoPagantes(List<PessoaNaoPaga> pessoas) {
        if (pessoas.size() != 0) {

            StringBuilder sb = new StringBuilder();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView title = new TextView(this);
            title.setText("\n\nQuem não precisa pagar:");

            title.setTextAlignment(viewTotalChurras.getTextAlignment());
            title.setTextColor(viewTotalChurras.getCurrentTextColor());
            title.setTextSize(28);
            title.setTypeface(viewTotalChurras.getTypeface());
            title.setGravity(viewBancario.getGravity());
            linearLayout.addView(title, params);

            for (PessoaNaoPaga p : pessoas) {
                sb.append("\n" + p.toString());
                sb.append("\n");
            }

            String texto = sb.toString();

            TextView tv = new TextView(this);
            tv.setText(texto);
            tv.setTextColor(viewRecebido.getCurrentTextColor());
            tv.setTextSize(20);
            tv.setGravity(viewBancario.getGravity());
            linearLayout.addView(tv, params);
        }
    }

    private void escreverPagantes(List<PessoaPaga> pessoas) {
        if (pessoas.size() != 0) {

            StringBuilder sb = new StringBuilder();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView title = new TextView(this);
            title.setText("\nQuem precisa pagar:");

            title.setTextAlignment(viewTotalChurras.getTextAlignment());
            title.setTextColor(viewTotalChurras.getCurrentTextColor());
            title.setTextSize(28);
            title.setTypeface(viewTotalChurras.getTypeface());
            title.setGravity(viewBancario.getGravity());
            linearLayout.addView(title, params);

            for (PessoaPaga p : pessoas) {
                sb.append("\n" + p.toString());
                sb.append("\n");
            }

            String texto = sb.toString();

            TextView tv = new TextView(this);
            tv.setText(texto);
            tv.setTextColor(viewRecebido.getCurrentTextColor());
            tv.setTextSize(20);
            tv.setGravity(viewBancario.getGravity());
            linearLayout.addView(tv, params);
        }
    }
}

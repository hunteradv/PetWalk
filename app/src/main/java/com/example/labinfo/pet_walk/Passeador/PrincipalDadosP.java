package com.example.labinfo.pet_walk.Passeador;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.TelaInicial;
import com.example.labinfo.pet_walk.Usuario.TelaLoginUsuario;
import com.orm.SugarContext;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalDadosP extends Fragment {

    EditText edt_nome, edt_senha, edt_email, edt_idade, edt_rg, edt_cpf, edt_telefone, edt_endereco;
    TextView tv_nome, tv_email, tv_senha,tv_idade, tv_rg, tv_endereco, tv_cpf, tv_telefone;
    Button btn_salvar, btn_excluir;

    View tela;

    public PrincipalDadosP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tela = inflater.inflate(R.layout.fragment_principal_dados2, container, false);

        btn_salvar = (Button)tela.findViewById(R.id.btn_salvar);
        btn_excluir = (Button)tela.findViewById(R.id.btn_excluir);

        edt_nome = (EditText)tela.findViewById(R.id.edt_nome);
        edt_email = (EditText)tela.findViewById(R.id.edt_email);
        edt_endereco = (EditText)tela.findViewById(R.id.edt_endereco);
        edt_idade = (EditText)tela.findViewById(R.id.edt_idade);
        edt_senha = (EditText)tela.findViewById(R.id.edt_senha);
        edt_rg = (EditText)tela.findViewById(R.id.edt_rg);
        edt_cpf = (EditText)tela.findViewById(R.id.edt_cpf);
        edt_telefone = (EditText)tela.findViewById(R.id.edt_telefone);
        tv_nome = (TextView)tela.findViewById(R.id.tv_nome);
        tv_email = (TextView)tela.findViewById(R.id.tv_email);
        tv_endereco = (TextView)tela.findViewById(R.id.tv_endereco);
        tv_idade = (TextView)tela.findViewById(R.id.tv_idade);
        tv_senha = (TextView)tela.findViewById(R.id.tv_senha);
        tv_rg = (TextView)tela.findViewById(R.id.tv_rg);
        tv_cpf = (TextView)tela.findViewById(R.id.tv_cpf);
        tv_telefone = (TextView)tela.findViewById(R.id.tv_telefone);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/antonregular.ttf");
        tv_nome.setTypeface(font);
        tv_email.setTypeface(font);
        tv_endereco.setTypeface(font);
        tv_idade.setTypeface(font);
        tv_senha.setTypeface(font);
        tv_rg.setTypeface(font);
        tv_cpf.setTypeface(font);
        tv_telefone.setTypeface(font);

        SugarContext.init(getContext());

        final Passeador passe = TelaLoginPasseador.passeadorlogado;

        edt_nome.setText(passe.getNome());
        edt_email.setText(passe.getEmail());
        edt_endereco.setText(passe.getEndereco());
        edt_idade.setText(passe.getIdade());
        edt_senha.setText(passe.getSenha());
        edt_rg.setText(passe.getRg());
        edt_cpf.setText(passe.getCpf());
        edt_telefone.setText(passe.getTelefone());

        /******TESTE DE DATANASC*****************/

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edt_idade.setText(current);
                    edt_idade.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        edt_idade.addTextChangedListener(tw);
        /***************FIMTESTE********************/

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inicia uma String para mostrar se tem campos vazios
                String vazios="";

                //Lista para verificar quais campos estao vazios
                String[][] verifica = {
                        {edt_senha.getText().toString(), edt_nome.getText().toString(), edt_email.getText().toString(), edt_endereco.getText().toString(), edt_telefone.getText().toString(), edt_cpf.getText().toString()},
                        {"SENHA", "NOME", "EMAIL", "ENDERECO", "CELULAR", "CPF"}};

                int cont=0;
                for(int i=0; i<verifica[0].length; i++){
                    if(verifica[0][i].equals("")){
                        cont++;
                        vazios = vazios + verifica[1][i] + "\n ";
                    }
                }
                //se tiver algum vazio o cont é maior que 0 e manda o alerta
                if(cont>0){
                    //criar alerta
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                    alerta.setTitle("CAMPO(S) VAZIO(S) NÃO PERMITIDOS:");
                    alerta.setMessage(vazios);
                    alerta.show();
                }
                else {
                    passe.setNome(edt_nome.getText().toString());
                    passe.setEmail(edt_email.getText().toString());
                    passe.setEndereco(edt_endereco.getText().toString());
                    passe.setDatanasc(edt_idade.getText().toString());
                    passe.setSenha(edt_senha.getText().toString());
                    passe.setRg(edt_rg.getText().toString());
                    passe.setCpf(edt_cpf.getText().toString());
                    passe.setTelefone(edt_telefone.getText().toString());

                    passe.save();
                    Toast.makeText(getContext(), "PASSEADOR ALTERADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar alerta
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("ATENÇÃO:");
                alerta.setMessage("DESEJA MESMO EXCLUIR SUA CONTA?");
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //aparece a tela com um sim e um nao perguntando
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passe.delete();
                        Toast.makeText(getContext(), "PASSEADOR EXCLUIDO", Toast.LENGTH_SHORT).show();

                        Intent tela = new Intent (getContext(), TelaInicial.class);
                        startActivity(tela);
                    }
                });
                alerta.setNegativeButton("Não", null);
                alerta.show();
            }
        });

        return tela;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Conecta no banco
        SugarContext.init(tela.getContext());

        Passeador passe = TelaLoginPasseador.passeadorlogado;

        edt_nome.setText(passe.getNome());
        edt_email.setText(passe.getEmail());
        edt_endereco.setText(passe.getEndereco());
        edt_idade.setText(passe.getDatanasc());
        edt_senha.setText(passe.getSenha());
        edt_rg.setText(passe.getRg());
        edt_cpf.setText(passe.getCpf());
        edt_telefone.setText(passe.getTelefone());
    }
}

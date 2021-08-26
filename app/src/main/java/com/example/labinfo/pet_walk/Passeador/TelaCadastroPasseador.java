package com.example.labinfo.pet_walk.Passeador;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.TelaLoginUsuario;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Calendar;
import java.util.List;

public class TelaCadastroPasseador extends AppCompatActivity {

    EditText edt_nome, edt_senha, edt_email, edt_idade, edt_rg, edt_cpf, edt_telefone, edt_endereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_passeador);

        edt_nome = (EditText)findViewById(R.id.edt_nome);
        edt_senha = (EditText)findViewById(R.id.edt_senha);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_idade = (EditText)findViewById(R.id.edt_idade);
        edt_rg = (EditText)findViewById(R.id.edt_rg);
        edt_cpf = (EditText)findViewById(R.id.edt_cpf);
        edt_telefone = (EditText)findViewById(R.id.edt_telefone);
        edt_endereco = (EditText)findViewById(R.id.edt_endereco);

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

        //conecta
        SugarContext.init(this);
    }

    public void cadastrar_clique (View v){
        //Pego os dados
        String nome = edt_nome.getText().toString();
        String senha = edt_senha.getText().toString();
        String email = edt_email.getText().toString();
        String idade = edt_idade.getText().toString();
        String rg = edt_rg.getText().toString();
        String cpf = edt_cpf.getText().toString();
        String telefone = edt_telefone.getText().toString();
        String endereco = edt_endereco.getText().toString();

        //inicia uma String para mostrar se tem campos vazios
        String vazios="";

        //Lista para verificar quais campos estao vazios
        String[][] verifica = {{senha, nome, email, endereco, telefone, cpf}, {"SENHA", "NOME", "EMAIL", "ENDERECO", "CELULAR", "CPF"}};
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
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("CAMPO(S) VAZIO(S) NÃO PERMITIDOS:");
            alerta.setMessage(vazios);
            alerta.show();
        }
        else{ //se nao tiver campos vazios faz a verificacao final de email ja registrado
            //necessario verificar se já existe algum usuario com o email
            //cria a lista pra ver se ja tem o email escolhido
            List<Passeador> verificapasseador = Select.from(Passeador.class)
                    .where(Condition.prop("email").eq(email))
                    .list();

            if(verificapasseador.size()==0){
                // nome,  senha,  email, idade, rg, cpf, telefone, endereco
                new Passeador(nome, senha, email, idade, rg, cpf,  telefone, endereco).save();
                Toast.makeText(this, "PASSEADOR CADASTRADO", Toast.LENGTH_SHORT).show();

                Intent tela = new Intent (this, TelaLoginPasseador.class);
                startActivity(tela);
            }
            else{
                //criar alerta
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setTitle("Mensagem:");
                alerta.setMessage("Email já cadastrado!");
                alerta.show();
            }
        }
    }
}

package com.example.labinfo.pet_walk.Usuario;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class TelaCadastroUsuario extends AppCompatActivity {

    EditText edt_nome, edt_email, edt_endereco, edt_celular, edt_senha, edt_pet, edt_raca, edt_porte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_usuario);

        edt_nome = (EditText)findViewById(R.id.edt_nome);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_endereco = (EditText)findViewById(R.id.edt_endereco);
        edt_celular = (EditText)findViewById(R.id.edt_celular);
        edt_senha = (EditText)findViewById(R.id.edt_senha);
        edt_pet = (EditText)findViewById(R.id.edt_pet);
        edt_raca = (EditText)findViewById(R.id.edt_raca);
        edt_porte = (EditText)findViewById(R.id.edt_porte);

        //conecta
        SugarContext.init(this);
    }

    public void cadastrar_clique (View v){
        //pego os dados
        String nome = edt_nome.getText().toString();
        String email = edt_email.getText().toString();
        String endereco = edt_endereco.getText().toString();
        String celular = edt_celular.getText().toString();
        String senha = edt_senha.getText().toString();
        String pet = edt_pet.getText().toString();
        String raca = edt_raca.getText().toString();
        String porte = edt_porte.getText().toString();



        //inicia uma String para mostrar se tem campos vazios
        String vazios="";

        //Lista para verificar quais campos estao vazios
        String[][] verifica = {{senha, nome, email, endereco, celular}, {"SENHA", "NOME", "EMAIL", "ENDERECO", "CELULAR"}};
        int cont=0;
        for(int i=0; i<verifica[0].length; i++){ //passa pelos valores de 0 a 5 em {senha, nome, email, endereco, celular}
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
            List<Usuario> verificausuario = Select.from(Usuario.class)
                    .where(Condition.prop("email").eq(email))
                    .list();

            if(verificausuario.size()==0){
                new Usuario(nome, email, endereco, celular, senha, pet, raca, porte).save();
                Toast.makeText(this, "USUARIO CADASTRADO", Toast.LENGTH_SHORT).show();

                Intent tela = new Intent (this, TelaLoginUsuario.class);
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

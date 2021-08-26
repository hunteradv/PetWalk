package com.example.labinfo.pet_walk.Passeador;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.TelaNavDrawer;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class TelaLoginPasseador extends AppCompatActivity {

    EditText edt_email, edt_senha;
    public static Passeador passeadorlogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_passeador);

        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_senha = (EditText)findViewById(R.id.edt_senha);

        TextView textView = (TextView)findViewById(R.id.tv_passeador);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/lobsterregular.ttf");
        textView.setTypeface(typeface);
    }

    /***************TESTES*******************************************/
    /*
    public void login_clique (View v){
        String email = "teste";
        String senha = "123";

        List<Passeador> passeador = Select.from(Passeador.class)
                .where(Condition.prop("email").eq(email),
                        Condition.prop("senha").eq(senha))
                .list();

        //vejo se encontrou
        if(passeador.size() > 0){

            //armazenar os dados do usuario logado
            TelaLoginPasseador.passeadorlogado = passeador.get(0);

            Intent telap = new Intent (this, TelaPasseadorNav.class);
            startActivity(telap);

        }else{
            Toast.makeText(this,"USUARIO NÃO CADASTRADO",Toast.LENGTH_LONG).show();
        }
    }
    */

    /******************OFICIAL*****************************************/

    public void login_clique (View v){
        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        List<Passeador> passeador = Select.from(Passeador.class)
                .where(Condition.prop("email").eq(email),
                        Condition.prop("senha").eq(senha))
                .list();

        //vejo se encontrou
        if(passeador.size() > 0){
            Intent tela = new Intent (this, TelaPasseadorNav.class);
            //armazenar os dados do usuario logado
            TelaLoginPasseador.passeadorlogado = passeador.get(0);
            startActivity(tela);
        }else{
            Toast.makeText(this,"USUARIO NÃO CADASTRADO",Toast.LENGTH_LONG).show();
        }
    }


    public void cadastrar_clique (View v){
        Intent tela = new Intent(this, TelaCadastroPasseador.class);
        startActivity(tela);
    }
}

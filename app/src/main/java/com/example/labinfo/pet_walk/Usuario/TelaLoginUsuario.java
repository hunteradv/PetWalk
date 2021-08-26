package com.example.labinfo.pet_walk.Usuario;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class TelaLoginUsuario extends AppCompatActivity {


    EditText edt_email, edt_senha;
    //TextView tv_teste;

    public static Usuario usuariologado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_usuario);

        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_senha=(EditText)findViewById(R.id.edt_senha);

        //Conecta
        SugarContext.init(this);

        //Coloco Font
        TextView textView = (TextView)findViewById(R.id.tv_usuario);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/lobsterregular.ttf");
        textView.setTypeface(typeface);
    }

   /*************************************************************************************/
    //oficial

    public void login_clique(View v){
        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        //Consulta
        List<Usuario> usuario = Select.from(Usuario.class)
                .where(Condition.prop("email").eq(email),
                        Condition.prop("senha").eq(senha))
                .list();


        //vejo se encontrou
        if(usuario.size() > 0){
            Intent tela = new Intent (this, TelaNavDrawer.class);
            //armazenar os dados do usuario logado
            TelaLoginUsuario.usuariologado = usuario.get(0);
            //iniciar tela
            startActivity(tela);


        }else{
            Toast.makeText(this, "USUARIO NÃO CADASTRADO",Toast.LENGTH_SHORT).show();
        }
    }


    public void cadastrar_clique (View v){
        Intent tela = new Intent(this, TelaCadastroUsuario.class);
        startActivity(tela);
    }

}

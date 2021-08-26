package com.example.labinfo.pet_walk;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.labinfo.pet_walk.Dados.Banco;
import com.example.labinfo.pet_walk.Passeador.TelaLoginPasseador;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.TelaLoginUsuario;

//Essa é a primeira tela e que pede login
public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //teste criando banco de dados ao abrir o app
        Banco.Criar(this);

        TextView textView = (TextView)findViewById(R.id.tv_bemvindo);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/lobsterregular.ttf");
        textView.setTypeface(typeface);
    }

    public void usuario_clique(View v){
        Intent tela = new Intent (this, TelaLoginUsuario.class);
        startActivity(tela);
    }

    public void passeador_clique(View v){
        Intent tela = new Intent (this, TelaLoginPasseador.class);
        startActivity(tela);
    }
}

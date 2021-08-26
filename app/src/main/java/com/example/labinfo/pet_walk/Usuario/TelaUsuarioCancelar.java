package com.example.labinfo.pet_walk.Usuario;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TelaUsuarioCancelar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario_cancelar);

        TextView tv_nome_passeador, tv_nome_usuario, tv_data, tv_titulo, tv_titulo2, tv_titulo3;


        tv_nome_passeador = (TextView) findViewById(R.id.tv_nome_passeador);
        tv_nome_usuario = (TextView) findViewById(R.id.tv_nome_usuario);
        tv_data = (TextView) findViewById(R.id.tv_data);
        tv_titulo = (TextView)findViewById(R.id.tv_titulo);
        tv_titulo2 = (TextView)findViewById(R.id.tv_titulo2);
        tv_titulo3 = (TextView)findViewById(R.id.tv_titulo3);

        //colocando font
        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/antonregular.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/acmeregular.ttf");

        tv_titulo.setTypeface(font2);
        tv_nome_usuario.setTypeface(font);
        tv_titulo2.setTypeface(font2);
        tv_nome_passeador.setTypeface(font);
        tv_titulo3.setTypeface(font2);
        tv_data.setTypeface(font);

        //conecta com o banco
        SugarContext.init(this);

        //cria a lista de chamados feitos pelo usuario sem resposta do passeador ainda
        List<Chamado> chamesp = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("0"),
                        Condition.prop("usuarioid").eq(TelaLoginUsuario.usuariologado.getId()))
                .list();
        //se tiver chamado em espera
        if(chamesp.size()!=0) {
            Chamado chamadocan = chamesp.get(0);
            Long idusuario = chamadocan.getUsuarioid();
            Long idpasseador = chamadocan.getPasseadorid();
            String datac = chamadocan.getData();
            //format data
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try{
                //passa para formato Date
                Date date1 = format.parse(datac);
                //pra depois formatar em br
                String datetime = formatbr.format(date1);
                tv_data.setText(datetime);

            }catch(ParseException e){
                e.printStackTrace();
            }

            Passeador nomepasseador = Passeador.findById(Passeador.class, idpasseador);
            Usuario nomeusuario = Usuario.findById(Usuario.class, idusuario);

            //mostrar
            tv_nome_passeador.setText(nomepasseador.getNome());
            tv_nome_usuario.setText(nomeusuario.getNome());

        }
    }
    public void cancelar_clique(View v) {

        //necessario verificar se já existe algum chamado
        //cria a lista de chamados feitos pelo usuario sem resposta do passeador ainda
        List<Chamado> chamesp = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("0"),
                        Condition.prop("usuarioid").eq(TelaLoginUsuario.usuariologado.getId()))
                .list();

        if(chamesp.size()!=0) {
            Chamado chamadocan = chamesp.get(0);

            Long chamadocancelarid = chamadocan.getId(); //recebe o id
            chamadocan.setChamadousuario("0"); // recebe o novo valor
            //chamadocan.setChamadocancelado("1");//muda o valor de cancelado para verdadeiro
            chamadocan.save(); // updates the previous entry with new values.

            //TelaLoginUsuario.usuariologado.setChamadocan("2");//2 para quando usuario cancela


            Toast.makeText(this, "Chamado Cancelado!", Toast.LENGTH_SHORT).show();

            finish();

        } else {
            Toast.makeText(this, "Nenhum chamado encontrado!", Toast.LENGTH_SHORT).show();
            finish();

        }

    }
}

package com.example.labinfo.pet_walk.Usuario;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TelaChamaPasseador extends AppCompatActivity {

    //testar codigo
    ListView lst_testechamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_chama_passeador);

        TextView tv_nome, tv_endereco, tv_telefone;

        //testar codigo
        //lst_testechamado = (ListView) findViewById(R.id.lst_testechamado);

        tv_nome = (TextView) findViewById(R.id.tv_nome);
        tv_endereco = (TextView) findViewById(R.id.tv_endereco);
        tv_telefone = (TextView) findViewById(R.id.tv_telefone);

        //Adicionando os tipos de font
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/acmeregular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/antonregular.ttf");

        tv_nome.setTypeface(font2);
        tv_endereco.setTypeface(font);
        tv_telefone.setTypeface(font);


        //mostrando nome, endereco e telefone
        tv_nome.setText(PrincipalUsuario.passeador_escolhido.getNome());
        tv_endereco.setText(PrincipalUsuario.passeador_escolhido.getEndereco());
        tv_telefone.setText(PrincipalUsuario.passeador_escolhido.getTelefone());

        //conecta com o banco
        SugarContext.init(this);

        //teste para ver id
        //tv_telefone.setText(Long.toString(TelaPrincipalUsuario.passeador_escolhido.getId()));


    }

    public void chamar_clique(View v) {

        Long usuarioid = TelaLoginUsuario.usuariologado.getId();
        Long passeadorid = PrincipalUsuario.passeador_escolhido.getId();

        //pegar a data e hora atual
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //formantando a data pq no sqlite é : YYYY-MM-DD HH:MM:SS
        Date data = Calendar.getInstance().getTime(); //recebendo a data do celular atual
        String dataFormatada = dateFormat.format(data); //passado de date para string pq é como o banco entende

        //para passar byte como verdadeiro ou falso, ja que o sqlite nao aceita boolean, apenas inteiros ou no caso byte
        String verdadeiro="1", falso="0";

        //necessario verificar se já existe algum chamado
        //cria a lista de chamados feitos pelo usuario sem resposta do passeador ainda
        List<Chamado> chamadosespera = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("0"),
                        Condition.prop("usuarioid").eq(TelaLoginUsuario.usuariologado.getId()))
                .list();

        if(chamadosespera.size()!=0){
            //se ja tiver algum valor
            Toast.makeText(this, "Já tem um chamado em espera!",Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, dataFormatada + usuarioid + passeadorid + verdadeiro + falso,Toast.LENGTH_SHORT).show();
            new Chamado(usuarioid, passeadorid, dataFormatada, verdadeiro, falso).save();

            Toast.makeText(this, "CHAMADO FEITO!",Toast.LENGTH_SHORT).show();

            notificacao();

            finish();
        }

    }

    public void teste_clique(View v) {
        //teste de codigo
        List<Chamado> chamados = Chamado.listAll(Chamado.class);

        final ArrayAdapter<Chamado> adaptador = new ArrayAdapter<Chamado>(getApplicationContext(),
                android.R.layout.simple_list_item_1, chamados);

        //Mostrando
        lst_testechamado.setAdapter(adaptador);

    }

    public void notificacao(){
        //notificação
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this,0,new Intent(this,NotificationActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Enviado");
        builder.setContentTitle("O pedido foi enviado ao passeador");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Notification n = builder.build();
        n.vibrate = new long[]{150,300,150,600};
        nm.notify(R.mipmap.ic_launcher, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();
        }
        catch (Exception e ){

        }
    }
}
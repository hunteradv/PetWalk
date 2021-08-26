package com.example.labinfo.pet_walk.Passeador;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.NotificationActivity;
import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaConfirmaChamado extends AppCompatActivity {

    TextView tv_nome, tv_endereco, tv_telefone, tv_data, telefone, endereco, data;
    public static Usuario usuariocan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirma_chamado);

        tv_nome = (TextView) findViewById(R.id.tv_nome);
        tv_endereco = (TextView) findViewById(R.id.tv_endereco);
        tv_telefone = (TextView) findViewById(R.id.tv_telefone);
        tv_data = (TextView) findViewById(R.id.tv_data);

        //Adicionar Font
        telefone = (TextView)findViewById(R.id.telefone);
        endereco = (TextView)findViewById(R.id.endereco);
        data = (TextView)findViewById(R.id.data);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/acmeregular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/antonregular.ttf");
            tv_nome.setTypeface(font2);
            telefone.setTypeface(font2);
            tv_telefone.setTypeface(font);
            endereco.setTypeface(font2);
            tv_endereco.setTypeface(font);
            data.setTypeface(font2);
            tv_data.setTypeface(font);

        //conecta com o banco
        SugarContext.init(this);

        //mostrando nome, endereco e telefone
        tv_nome.setText(PrincipalPasseador.chamadoescolhidop.getUsuarioNome());
        tv_endereco.setText(PrincipalPasseador.chamadoescolhidop.getUsuarioEndereco());
        tv_telefone.setText(PrincipalPasseador.chamadoescolhidop.getUsuarioTelefone());
        //mostrar data no formato correto
        String datac=PrincipalPasseador.chamadoescolhidop.getData();
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
    }

    public void confirmar_clique(View v){
        Chamado chamado = SugarRecord.findById(Chamado.class, (long) PrincipalPasseador.chamadoescolhidop.getId());
        chamado.setChamadopasseador("1");
        chamado.save();
        Toast.makeText(getApplicationContext(), "CHAMADO CONFIRMADO", Toast.LENGTH_SHORT).show();

        //pega id do usuario e seta o chamadocan
        long usuid = chamado.getUsuarioid();
        usuariocan = Usuario.findById(Usuario.class,usuid);
        usuariocan.setChamadocan("2");
        usuariocan.save();

        //notificação
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this,0,new Intent(this,NotificationActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Confirmado!");
        builder.setContentTitle("O pedido foi confirmado ao usuario");
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

        finish();
    }

    public void cancelar_clique(View v){
        Chamado chamado = SugarRecord.findById(Chamado.class, (long) PrincipalPasseador.chamadoescolhidop.getId());
        chamado.setChamadousuario("0");
        chamado.setChamadousuario("0");
        //pega id do usuario e seta o chamadocan
        long usuid = chamado.getUsuarioid();
        usuariocan = Usuario.findById(Usuario.class, usuid);
        usuariocan.setChamadocan("1");
        chamado.save();
        usuariocan.save();
        Toast.makeText(getApplicationContext(), "Passeio CANCELADO!", Toast.LENGTH_SHORT).show();

        //notificação
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this,0,new Intent(this,NotificationActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Cancelado!");
        builder.setContentTitle("O passeio foi cancelado.");
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

        finish();
    }


}

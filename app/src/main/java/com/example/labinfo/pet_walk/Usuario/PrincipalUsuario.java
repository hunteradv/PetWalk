package com.example.labinfo.pet_walk.Usuario;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class PrincipalUsuario extends Fragment {

    ListView lst_passeadores, lst_cham_espera;
    TextView tv_nome_usuario, tv_estado, txt1, txt2;
    View tela;

    public static Passeador passeador_escolhido;
    public static Chamado chamadocancelar;

    public PrincipalUsuario(){
        //required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Obtenho o fragmento
        tela = inflater.inflate(R.layout.fragment_principal_usuario, container, false);

        //Coloco Fonte
        tv_estado = (TextView) tela.findViewById(R.id.tv_estado);
        txt1 = (TextView) tela.findViewById(R.id.tv_titulo1);
        txt2 = (TextView) tela.findViewById(R.id.tv_titulo2);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/acmeregular.ttf");
        txt2.setTypeface(font);
        txt1.setTypeface(font);
        tv_estado.setTypeface(font);


        lst_passeadores = (ListView) tela.findViewById(R.id.lst_passeadores);
        lst_cham_espera = (ListView) tela.findViewById(R.id.lst_cham_espera);
        tv_nome_usuario = (TextView) tela.findViewById(R.id.tv_nome_usuario);


        //Mostrando o nome do usuario logado
        tv_nome_usuario.setText(TelaLoginUsuario.usuariologado.getNome());

        //Conecta no banco
        SugarContext.init(tela.getContext());

        return tela;
    }



    @Override
    public void onResume() {
        super.onResume();

        List<Passeador> passeadores = Passeador.listAll(Passeador.class);

        final AdaptadorListarPasseadores adaptador = new
                AdaptadorListarPasseadores(getContext(), passeadores);

        //Mostrando
        lst_passeadores.setAdapter(adaptador);

        //Tratando o clique
        lst_passeadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Obtendo o passeador escolhido
                Passeador passeador = adaptador.getItem(position);
                //Coloco o passeador escolhido na memória
                PrincipalUsuario.passeador_escolhido = passeador;
                //Chamo a outra tela
                Intent tela = new Intent(getActivity(), TelaChamaPasseador.class);
                startActivity(tela);
            }

        });


        //fazer a lista de chamados feitos pelo usuario sem resposta do passeador ainda
        List<Chamado> chamadosespera = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("0"),
                        Condition.prop("usuarioid").eq(TelaLoginUsuario.usuariologado.getId()))
                .list();
        if(chamadosespera.size()!=0){
            lst_cham_espera.setVisibility(View.VISIBLE);
            tv_estado.setText("Chamado em espera:");
            final ArrayAdapter<Chamado> adaptadorconf = new ArrayAdapter<Chamado>(getContext(),
                    android.R.layout.simple_list_item_1, chamadosespera);

            //Mostrando
            lst_cham_espera.setAdapter(adaptadorconf);

            //tratar o clique
            lst_cham_espera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Obtendo o chamado escolhido
                    Chamado chamado = adaptadorconf.getItem(position);
                    //Coloco o passeador escolhido na memória
                    PrincipalUsuario.chamadocancelar = chamado;
                    //Chamo a outra tela
                    Intent tela = new Intent(getActivity(), TelaUsuarioCancelar.class);
                    startActivity(tela);
                }

            });
        }
        else{
            lst_cham_espera.setVisibility(View.INVISIBLE);
            tv_estado.setText("Não possui nenhum chamado em espera!");

            //verificar se o usuario está com chamadocancelado
            if(TelaLoginUsuario.usuariologado.getChamadocan().equals("1")){
                //criar alerta
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("ATENÇÃO:");
                alerta.setMessage("Infelizmente o passeador cancelou o passeio. Dê uma olhada na lista para chamar outra pessoa!");
                //muda status do usuario
                TelaLoginUsuario.usuariologado.setChamadocan("0");
                TelaLoginUsuario.usuariologado.save();

                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() { //aparece a tela com um sim e um nao perguntando
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alerta.show();
            }
            if(TelaLoginUsuario.usuariologado.getChamadocan().equals("2")){
                //criar alerta
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("ATENÇÃO:");
                alerta.setMessage("SEU CHAMADO FOI CONFIRMADO.");
                TelaLoginUsuario.usuariologado.setChamadocan("0");
                TelaLoginUsuario.usuariologado.save();

                tv_estado.setText("Chamado Confirmado!");
                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() { //aparece a tela com um sim e um nao perguntando
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alerta.show();
            }
        }

    }
}

package com.example.labinfo.pet_walk.Passeador;


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
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.PrincipalChamados;
import com.example.labinfo.pet_walk.Usuario.TelaChamaPasseador;
import com.example.labinfo.pet_walk.Usuario.TelaLoginUsuario;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalPasseador extends Fragment {

    View telap;
    ListView lst_cha_espera, lst_conf;
    TextView tv_nome_pass, tv_cha_conf, tv_cha_espera, txt1, txt2;
    public static Chamado chamadoescolhidop;

    public PrincipalPasseador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Obtenho o fragmento
        telap = inflater.inflate(R.layout.fragment_principal_passeador, container, false);


        tv_cha_espera = (TextView) telap.findViewById(R.id.tv_cha_espera);
        lst_cha_espera = (ListView) telap.findViewById(R.id.lst_cha_espera);
        lst_conf = (ListView) telap.findViewById(R.id.lst_conf);
        tv_nome_pass = (TextView) telap.findViewById(R.id.tv_nome_pass);
        tv_cha_conf = (TextView) telap.findViewById(R.id.tv_cha_conf);
        txt1 = (TextView) telap.findViewById(R.id.tv_titulo1);
        txt2 = (TextView) telap.findViewById(R.id.tv_cha_espera);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/acmeregular.ttf");
        txt2.setTypeface(font);
        txt1.setTypeface(font);
        tv_cha_conf.setTypeface(font);

        //Mostrando o nome do passeador logado
        tv_nome_pass.setText(TelaLoginPasseador.passeadorlogado.getNome());

        //Conecta no banco
        SugarContext.init(telap.getContext());

        return telap;
    }

    @Override
    public void onResume() {
        super.onResume();

        //listando os cahamdos em espera com o id do passeador
        List<Chamado> chamadosp = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("0"),
                        Condition.prop("passeadorid").eq(TelaLoginPasseador.passeadorlogado.getId()))
                .list();

        if (chamadosp.size() != 0) {
            final ArrayAdapter<Chamado> adaptadorp = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, chamadosp);

            //Mostrando
            lst_cha_espera.setAdapter(adaptadorp);

            //Tratando o clique
            lst_cha_espera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Obtendo o chamado escolhido
                    Chamado chamadop = adaptadorp.getItem(position);
                    //Coloco o chamado escolhido na memória
                    PrincipalPasseador.chamadoescolhidop = chamadop;
                    //Chamo a outra tela
                    Intent tela = new Intent(getActivity(), TelaConfirmaChamado.class);
                    startActivity(tela);
                }

            });

            //alerta
            //criar alerta
            AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
            alerta.setTitle("ATENÇÃO:");
            alerta.setMessage("Chamado em espera!");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() { //aparece a tela com um sim e um nao perguntando
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alerta.show();

        } else {
            tv_cha_espera.setText("Não há chamados em espera.");

        }

        //para fazer a lista de chamados confirmados entre as duas partes
        List<Chamado> chamadosconfp = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("1"),
                        Condition.prop("passeadorid").eq(TelaLoginPasseador.passeadorlogado.getId()))
                .list();

        if (chamadosconfp.size() != 0) {
            final ArrayAdapter<Chamado> adaptadorconfp = new ArrayAdapter<Chamado>(getContext(),
                    android.R.layout.simple_list_item_1, chamadosconfp);

            //Mostrando
            lst_conf.setAdapter(adaptadorconfp);
        }
    }

}

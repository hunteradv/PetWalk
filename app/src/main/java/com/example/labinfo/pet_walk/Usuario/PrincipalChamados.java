package com.example.labinfo.pet_walk.Usuario;

import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class PrincipalChamados extends Fragment{

    TextView tv_chamadosu;

    ListView lst_chamados_u;

    View telac;

    public PrincipalChamados(){
        //precisa de construtor vazio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Obtenho o fragmento
        telac = inflater.inflate(R.layout.fragment_principal_chamados, container, false);

        lst_chamados_u = (ListView) telac.findViewById(R.id.lst_chamados_u);
        tv_chamadosu = (TextView)telac.findViewById(R.id.tv_chamadosu);

        //colocando font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/antonregular.ttf");
        tv_chamadosu.setTypeface(font);

        //Conecta no banco
        SugarContext.init(telac.getContext());

        return telac;
    }

    @Override
    public void onResume() {
        super.onResume();

        //lista os chamados do usuario
        List<Chamado> chamados = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("usuarioid").eq(TelaLoginUsuario.usuariologado.getId()))
                .list();


        if(chamados.size()!= 0){
            lst_chamados_u.setVisibility(View.VISIBLE);
            final ArrayAdapter<Chamado> adaptadorlista = new ArrayAdapter<Chamado>(getContext(),
                    android.R.layout.simple_list_item_1, chamados);

            //Mostrando
            lst_chamados_u.setAdapter(adaptadorlista);

        }
        else{
            lst_chamados_u.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Não há nenhum chamado no histórico!", Toast.LENGTH_LONG).show();
            //teste
            PrincipalUsuario fragment = new PrincipalUsuario();
            FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
            fragmentos.replace(R.id.frame_principal, fragment).commit();

        }
    }

}

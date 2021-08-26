package com.example.labinfo.pet_walk.Passeador;


import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.PrincipalUsuario;
import com.example.labinfo.pet_walk.Usuario.TelaLoginUsuario;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalChamadosP extends Fragment {

    TextView tv_historicop;

    ListView lst_chamadosp;

    View tela;

    public PrincipalChamadosP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Obtenho o fragmento
        tela = inflater.inflate(R.layout.fragment_principal_chamados_p, container, false);

        lst_chamadosp = (ListView) tela.findViewById(R.id.lst_chamadosp);
        tv_historicop = (TextView)tela.findViewById(R.id.tv_historicop);


        //colocando Font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/antonregular.ttf");
        tv_historicop.setTypeface(font);

        //Conecta no banco
        SugarContext.init(tela.getContext());

        return tela;
    }

    @Override
    public void onResume() {
        super.onResume();

        //lista os confirmados
        List<Chamado> chamadoespera = Select.from(Chamado.class)
                .where(Condition.prop("chamadousuario").eq("1"),
                        Condition.prop("chamadopasseador").eq("1"),
                        Condition.prop("passeadorid").eq(TelaLoginPasseador.passeadorlogado.getId()))
                .list();


        if(chamadoespera.size()== 0){
            Toast.makeText(getContext(), "Não há nenhum chamado confirmado no histórico!", Toast.LENGTH_LONG).show();
            //teste
            PrincipalPasseador fragment = new PrincipalPasseador();
            FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
            fragmentos.replace(R.id.frame_principalp, fragment).commit();
        }
        else{
            final ArrayAdapter<Chamado> adaptadorlistap = new ArrayAdapter<Chamado>(getContext(),
                    android.R.layout.simple_list_item_1, chamadoespera);

            //Mostrando
            lst_chamadosp.setAdapter(adaptadorlistap);
        }
    }


}

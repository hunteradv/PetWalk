package com.example.labinfo.pet_walk.Usuario;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.labinfo.pet_walk.Dados.Passeador;
import com.example.labinfo.pet_walk.R;

import java.util.List;
import java.util.Random;

/**
 * Created by felip on 24/06/2020.
 */
public class AdaptadorListarPasseadores extends ArrayAdapter<Passeador> {

    private List<Passeador> passeadores;
    private Context mContext;

    public AdaptadorListarPasseadores(@NonNull Context context, @LayoutRes List<Passeador> list){
        super(context, 0, list);
        mContext = context;
        passeadores = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.lista_personalizada, parent, false);

        Passeador passeadorAtual = passeadores.get(position);

        //para colocar imagens aleatorias:
        int aleatorio[] = {0,1,2,3,4,5,6,7};
        Random r = new Random();
        int int_random = r.nextInt(8);


        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        if(int_random==0)image.setImageResource(R.drawable.dogoface);
        else if(int_random==1) image.setImageResource(R.drawable.dogoface1);
        else if(int_random==2) image.setImageResource(R.drawable.dogoface2);
        else if(int_random==3) image.setImageResource(R.drawable.dogoface3);
        else if(int_random==4) image.setImageResource(R.drawable.dogoface4);
        else if(int_random==5) image.setImageResource(R.drawable.dogoface5);
        else if(int_random==6) image.setImageResource(R.drawable.dogoface6);
        else if(int_random==7) image.setImageResource(R.drawable.dogoface7);


        TextView name = (TextView) listItem.findViewById(R.id.tv_titulo1);
        name.setText(passeadorAtual.getNome());

        TextView telefone = (TextView) listItem.findViewById((R.id.textView_telefone));
        telefone.setText(passeadorAtual.getTelefone());

        TextView endereco = (TextView) listItem.findViewById((R.id.textView_endereco));
        endereco.setText(passeadorAtual.getEndereco());

        return listItem;
    }
}

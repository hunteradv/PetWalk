package com.example.labinfo.pet_walk.Usuario;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labinfo.pet_walk.Dados.Chamado;
import com.example.labinfo.pet_walk.Dados.Usuario;
import com.example.labinfo.pet_walk.Passeador.TelaLoginPasseador;
import com.example.labinfo.pet_walk.R;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class PrincipalDados extends Fragment {

    EditText edt_nome, edt_email, edt_endereco, edt_celular, edt_senha, edt_pet, edt_raca, edt_porte;
    TextView tv_nome, tv_email, tv_endereco, tv_celular, tv_senha, tv_pet, tv_raca, tv_porte;
    Button btn_salvar, btn_excluir;
    Usuario usu;
    View tela;

    public PrincipalDados(){
        //necessario construtor vazio
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Obtenho o fragmento
        tela = inflater.inflate(R.layout.fragment_principal_dados, container, false);

        btn_salvar = (Button)tela.findViewById(R.id.btn_salvar);
        btn_excluir = (Button)tela.findViewById(R.id.btn_excluir);

        edt_nome = (EditText)tela.findViewById(R.id.edt_nome);
        edt_email = (EditText)tela.findViewById(R.id.edt_email);
        edt_endereco = (EditText)tela.findViewById(R.id.edt_endereco);
        edt_celular = (EditText)tela.findViewById(R.id.edt_celular);
        edt_senha = (EditText)tela.findViewById(R.id.edt_senha);
        edt_pet = (EditText)tela.findViewById(R.id.edt_pet);
        edt_raca = (EditText)tela.findViewById(R.id.edt_raca);
        edt_porte = (EditText)tela.findViewById(R.id.edt_porte);
        tv_nome = (TextView)tela.findViewById(R.id.tv_nome);
        tv_email = (TextView)tela.findViewById(R.id.tv_email);
        tv_endereco = (TextView)tela.findViewById(R.id.tv_endereco);
        tv_celular = (TextView)tela.findViewById(R.id.tv_celular);
        tv_senha = (TextView)tela.findViewById(R.id.tv_senha);
        tv_pet = (TextView)tela.findViewById(R.id.tv_pet);
        tv_raca = (TextView)tela.findViewById(R.id.tv_raca);
        tv_porte = (TextView)tela.findViewById(R.id.tv_porte);

        //colocando font
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/antonregular.ttf");
        tv_nome.setTypeface(font);
        tv_email.setTypeface(font);
        tv_endereco.setTypeface(font);
        tv_celular.setTypeface(font);
        tv_senha.setTypeface(font);
        tv_pet.setTypeface(font);
        tv_raca.setTypeface(font);
        tv_porte.setTypeface(font);


        //Conecta no banco
        SugarContext.init(tela.getContext());

        final Usuario usu = TelaLoginUsuario.usuariologado;

        //Mostra os dados do usuario
        edt_nome.setText(usu.getNome());
        edt_email.setText(usu.getEmail());
        edt_endereco.setText(usu.getEndereco());
        edt_celular.setText(usu.getCelular());
        edt_senha.setText(usu.getSenha());
        edt_pet.setText(usu.getPet());
        edt_raca.setText(usu.getRaca());
        edt_porte.setText(usu.getPorte());

        //criando listener para os botoes
        btn_salvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //FAZER VERIFICACOES - TENTEI CRIAR UMA FUNCAO SEPARADA PRA DEIXAR O CODIGO MAIS LIMPO,
                //  MAS AI A FUNCAO NAO FUNCIONAVA
                //inicia uma String para mostrar se tem campos vazios
                String vazios = "";

                //Lista para verificar quais campos estao vazios
                String[][] verifica = {
                        {
                                edt_senha.getText().toString(),
                                edt_nome.getText().toString(),
                                edt_email.getText().toString(),
                                edt_endereco.getText().toString(),
                                edt_celular.getText().toString()
                        },
                        {
                                "SENHA", "NOME", "EMAIL", "ENDERECO", "CELULAR"
                        }
                };

                int cont = 0;
                for (int i = 0; i < verifica[0].length; i++) { //passa pelos valores de 0 a 5 em {senha, nome, email, endereco, celular}
                    if (verifica[0][i].equals("")) {
                        cont++;
                        vazios = vazios + verifica[1][i] + "\n ";
                    }
                }
                //se tiver algum vazio o cont é maior que 0 e manda o alerta
                if (cont > 0) {
                    //criar alerta
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                    alerta.setTitle("CAMPO(S) VAZIO(S) NÃO PERMITIDOS:");
                    alerta.setMessage(vazios);
                    alerta.show();
                } else {
                    //necessario verificar se já existe algum usuario com o email
                    //cria a lista pra ver se ja tem o email escolhido
                    List<Usuario> verificausuario = Select.from(Usuario.class)
                            .where(Condition.prop("email").eq(edt_email.getText().toString()))
                            .list();

                    if (verificausuario.size() != 0 && !verificausuario.get(0).getEmail().equals(TelaLoginUsuario.usuariologado.getEmail())) {
                        //criar alerta
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                        alerta.setTitle("Mensagem:");
                        alerta.setMessage("Email já cadastrado!");
                        alerta.show();
                    } else {
                        usu.setNome(edt_nome.getText().toString());
                        usu.setEmail(edt_email.getText().toString());
                        usu.setEndereco(edt_endereco.getText().toString());
                        usu.setCelular(edt_celular.getText().toString());
                        usu.setSenha(edt_senha.getText().toString());
                        usu.setPet(edt_pet.getText().toString());
                        usu.setRaca(edt_raca.getText().toString());
                        usu.setPorte(edt_pet.getText().toString());

                        usu.save();
                        Toast.makeText(getContext(), "USUARIO ALTERADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar alerta
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setTitle("ATENÇÃO:");
                alerta.setMessage("DESEJA MESMO EXCLUIR SUA CONTA?");
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //aparece a tela com um sim e um nao perguntando
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usu.delete();
                        Toast.makeText(getContext(), "USUARIO EXCLUIDO", Toast.LENGTH_SHORT).show();

                        Intent tela = new Intent (getContext(), TelaLoginUsuario.class);
                        startActivity(tela);
                    }
                });
                alerta.setNegativeButton("Não", null);
                alerta.show();
            }
        });

        return tela;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Conecta no banco
        SugarContext.init(tela.getContext());

        Usuario usu = TelaLoginUsuario.usuariologado;
        //Mostra os dados do usuario
        edt_nome.setText(usu.getNome());
        edt_email.setText(usu.getEmail());
        edt_endereco.setText(usu.getEndereco());
        edt_celular.setText(usu.getCelular());
        edt_senha.setText(usu.getSenha());
        edt_pet.setText(usu.getPet());
        edt_raca.setText(usu.getRaca());
        edt_porte.setText(usu.getPorte());

    }


}

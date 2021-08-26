package com.example.labinfo.pet_walk.Dados;

import android.content.Context;

import com.orm.SugarContext;

import java.util.List;

/**
 * Created by felip on 25/05/2020.
 */
public class Banco {
    public static void Criar(Context c){
        //Conecto
        SugarContext.init(c);

        //Verifico se o banco já foi criado
        List<Passeador> passeador = Passeador.listAll(Passeador.class);
        if(passeador.size() <= 0 ){
            //String nome, String senha, String email, String datanasc,String rg,String cpf,String telefone,String endereco
            //cadastro os NOME / SENHA / EMAIL / datanasc / rg / cpf / telefone / endereco
            new Passeador("Passeador Teste", "123", "teste", "01/01/1950","RG","40040040010","17 999998888","Rua Teste").save();
            new Passeador("João Ricardo", "123", "pass2@teste.com", "01/01/1950","RG","ss","99854-1212","Rua Macieira 222").save();

            new Passeador("Fernando Aleixo", "123", "pass3@teste.com", "01/01/1950","RG","ss","3226-6554","Rua Diacomo 567").save();
            new Passeador("Joana Machado", "123", "pass4@teste.com", "01/01/1950","RG","ss","3220-5526","Rua Tridez 1010").save();
        }

        //Verifico se o banco já foi criado
        List<Usuario> usuarios = Usuario.listAll(Usuario.class);
        if(usuarios.size() <= 0 ) {
            //nome email endereco celular senha pet raca porte chamadocan
            new Usuario("Ricardo Dono Teste", "teste", "Rua teste", "17 99999999", "123", "", "", "").save();
            new Usuario("Mariângela Prado", "usu2@teste.com", "98986565", "rua", "123", "hh", "hh", "hh").save();

            new Usuario("Charles da Macieira", "usu3@teste.com", "98876565", "rua", "123", "hh", "hh", "ff").save();
            new Usuario("Bruna Luisa", "usu4@teste.com", "95556662", "rua", "123", "ff", "ff", "ff").save();
        }
    }
}

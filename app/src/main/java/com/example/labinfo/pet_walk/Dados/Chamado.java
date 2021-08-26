package com.example.labinfo.pet_walk.Dados;

import com.orm.SugarRecord;

/**
 * Created by felip on 29/05/2020.
 */
public class Chamado extends SugarRecord{

    Long id, usuarioid, passeadorid;
    String chamadousuario="0", chamadopasseador="0";
    String data;
    //String chamadocancelado="0";

    public Chamado(){

    }


    public Chamado(Long usuarioid, Long passeadorid, String data,
                   String chamadousuario, String chamadopasseador){
        this.usuarioid = usuarioid;
        this.passeadorid = passeadorid;
        this.data = data;
        this.chamadopasseador = chamadopasseador;
        this.chamadousuario = chamadousuario;
        //this.chamadocancelado = chamadocancelado;

    }


    @Override
    public String toString(){
        Usuario usuario = SugarRecord.findById(Usuario.class, (long) usuarioid);
        Passeador passeador = SugarRecord.findById(Passeador.class, (long) passeadorid);

        return "Dono de pet: "+ usuario +"\nPasseador: " + passeador.getNome() + "\nData: " +data;

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Long getPasseadorid() {
        return passeadorid;
    }

    public void setPasseadorid(Long passeadorid) {
        this.passeadorid = passeadorid;
    }

    public String getChamadousuario() {
        return chamadousuario;
    }

    public void setChamadousuario(String chamadousuario) {
        this.chamadousuario = chamadousuario;
    }

    public String getChamadopasseador() {
        return chamadopasseador;
    }

    public void setChamadopasseador(String chamadopasseador) {
        this.chamadopasseador = chamadopasseador;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /*
    public String getChamadocancelado() {
        return chamadocancelado;
    }

    public void setChamadocancelado(String chamadocancelado) {
        this.chamadocancelado = chamadocancelado;
    }
    */

    //Relativo ao dados do usuario
    public String getUsuarioNome(){
        Usuario usuario = SugarRecord.findById(Usuario.class, (long) usuarioid);
        return usuario.getNome();
    }

    public String getUsuarioTelefone(){
        Usuario usuario = SugarRecord.findById(Usuario.class, (long) usuarioid);
        return usuario.getCelular();
    }
    public String getUsuarioEndereco(){
        Usuario usuario = SugarRecord.findById(Usuario.class, (long) usuarioid);
        return usuario.getEndereco();
    }


}

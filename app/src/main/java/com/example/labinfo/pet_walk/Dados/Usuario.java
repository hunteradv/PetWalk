package com.example.labinfo.pet_walk.Dados;

import com.orm.SugarRecord;

/**
 * Created by Gustavo on 19/04/2020.
 */
public class Usuario extends SugarRecord {
    Long id;
    String nome, email, endereco, celular, senha, pet, raca, porte;
    String chamadocan="0";//0 para default, 1 para passeador cancela, 2 para confirma

    public Usuario(){

    }

    public Usuario(String nome, String email, String endereco, String celular, String senha, String pet, String raca, String porte){
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.celular = celular;
        this.senha = senha;
        this.pet = pet;
        this.raca = raca;
        this.porte = porte;
        //this.chamadocan = chamadocan;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getChamadocan() {
        return chamadocan;
    }

    public void setChamadocan(String chamadocan) {
        this.chamadocan = chamadocan;
    }
}


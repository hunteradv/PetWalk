package com.example.labinfo.pet_walk.Dados;

import com.orm.SugarRecord;

/**
 * Created by Gustavo on 19/04/2020.
 */
public class Passeador extends SugarRecord {
    Long id;
    String nome, senha, email, idade, rg, cpf, telefone, endereco, datanasc;

    public Passeador(){

    }

    public Passeador(String nome, String senha, String email, String datanasc,String rg,String cpf,String telefone,String endereco ){
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.datanasc = datanasc;
    }

    @Override
    public String toString() {
        return nome + "\n" + endereco;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(String datanasc) {
        this.datanasc = datanasc;
    }
}

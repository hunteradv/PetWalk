package com.example.labinfo.pet_walk.Usuario;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

//import com.example.labinfo.pet_walk.CustomTypefaceSpan;

import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.TelaInicial;

public class TelaNavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_nav_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        //criando o fragment PrincipalUsuario para mostrar como default
        PrincipalUsuario fragment = new PrincipalUsuario();
        FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
        fragmentos.replace(R.id.frame_principal, fragment).commit();
    }

    @Override
    public void onBackPressed() {

        //tentativa 2: addtobackstack
        if(getFragmentManager().getBackStackEntryCount()!=0){
            getFragmentManager().popBackStack();

        }else {
            super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        FragmentTransaction fragmentos = getFragmentManager().beginTransaction();

        if (id == R.id.passeadores) {
            PrincipalUsuario tela = new PrincipalUsuario();
            fragmentos.replace(R.id.frame_principal, tela);//.addToBackStack("pusu");
        } else if (id == R.id.historico) {
            PrincipalChamados tela = new PrincipalChamados();
            fragmentos.replace(R.id.frame_principal, tela);//.addToBackStack("pcham");
        } else if (id == R.id.meusdados) {
            PrincipalDados tela = new PrincipalDados();
            fragmentos.replace(R.id.frame_principal, tela);//.addToBackStack("pdado");
        }
        else if (id==R.id.sair){
            //Intent tela = new Intent(getApplicationContext(), TelaInicial.class);
            finish();
            //startActivity(tela);
        }

        //mostrar a tela
        fragmentos.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //criando o fragment PrincipalUsuario para mostrar como default
        PrincipalUsuario fragment = new PrincipalUsuario();
        FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
        fragmentos.replace(R.id.frame_principal, fragment).commit();
    }

    //Metodo para Adicionar font que devia funcionar
    /*private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "acmeregular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }*/
}

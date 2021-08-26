package com.example.labinfo.pet_walk.Passeador;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.labinfo.pet_walk.R;
import com.example.labinfo.pet_walk.Usuario.PrincipalChamados;
import com.example.labinfo.pet_walk.Usuario.PrincipalUsuario;

public class TelaPasseadorNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_passeador_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        /*
        //criando a PrincipalPasseador para mostra como default
        PrincipalPasseador fragment = new PrincipalPasseador();
        FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
        fragmentos.replace(R.id.frame_principalp, fragment).commit();
        */
    }

    @Override
    public void onBackPressed() {

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
        getMenuInflater().inflate(R.menu.tela_passeador_nav, menu);
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

        if (id == R.id.usuarios) {
            PrincipalPasseador tela = new PrincipalPasseador();
            fragmentos.replace(R.id.frame_principalp, tela);//.addToBackStack("pass");
        } else if (id == R.id.chamadosP) {
            PrincipalChamadosP tela = new PrincipalChamadosP();
            fragmentos.replace(R.id.frame_principalp, tela);//.addToBackStack("champ");

        } else if (id == R.id.meusdadosP) {
            PrincipalDadosP tela = new PrincipalDadosP();
            fragmentos.replace(R.id.frame_principalp, tela);//.addToBackStack("dadosp");
        }
        else if (id==R.id.sairP){
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

        //criando o fragment P`rincipalPasseador para mostrar como default
        PrincipalPasseador fragment = new PrincipalPasseador();
        FragmentTransaction fragmentos = getFragmentManager().beginTransaction();
        fragmentos.replace(R.id.frame_principalp, fragment).commit();
    }

}

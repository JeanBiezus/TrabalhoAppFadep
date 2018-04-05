package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fadep.android.pos.trabalhoapp.sqlite.FeedProduto;
import fadep.android.pos.trabalhoapp.sqlite.FeedReaderDbHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<FeedProduto> produtosRoom;
    private FeedReaderDbHelper reader;
    ListView lista;

    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SETA O LAYOUT PARA activity_main
        setContentView(R.layout.activity_main);

        //PEGA A REFERÊNCIA DO TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SIDE MENU
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        reader = new FeedReaderDbHelper(this);
        lista = findViewById(R.id.listaProdutos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaMain();
    }

    public void atualizarListaMain(){
        produtosRoom = reader.read();
        List<Produto> produtos = new ArrayList<>();
        for (FeedProduto feedProduto : produtosRoom) {
            produtos.add(feedProduto.getProduto());
        }
        ProdutoListaAdapter listaAdapter = new ProdutoListaAdapter(produtos, this);
        lista.setAdapter(listaAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    //QUANDO SELECIONA UM MENU NO SIDE MENU
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //SE O MENU FOR O DE LOGIN, ABRE A ACTIVITY DE LOGIN
        if (id == R.id.nav_login) {
            intent = new Intent(this, LoginActivity.class);
        }else if(id == R.id.nav_cadastro_produto ){
            intent = new Intent(this,ProdutoActivity.class);
        }

        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

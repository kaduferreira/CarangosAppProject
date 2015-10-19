package br.com.caelum.fj59.carangos.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {

    private ListView listView;
    private List<Publicacao> publicacoes;
    private PublicacaoAdapter adapter;
    private EstadoMainActivity estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.publicacoes = new ArrayList<Publicacao>();

        ProgressFragment progress = ProgressFragment.comMensagem(R.string.carregando);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_principal, progress);
        ft.commit();

        this.estado = EstadoMainActivity.INICIO;
        this.estado.executa(this);
    }

    @Override
    public void lidaComRetorno(List<Publicacao> retorno) {
        this.publicacoes.clear();
        this.publicacoes.addAll(retorno);

        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    public void lidaComErro(Exception e){
        e.printStackTrace();
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public CarangosApplication getCarangosApplication(){
        return (CarangosApplication) getApplication();
    }

    public void atualizaListaCom(List<Publicacao> publicacoes) {
        this.publicacoes.clear();
        this.publicacoes.addAll(publicacoes);
        this.adapter.notifyDataSetChanged();
    }

    public List<Publicacao> getPublicacoes() {
        return this.publicacoes;
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }

    public void buscaPublicacoes(){
        new BuscaMaisPublicacoesTask(this).execute();
    }
}

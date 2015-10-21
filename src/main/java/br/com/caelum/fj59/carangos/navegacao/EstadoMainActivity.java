package br.com.caelum.fj59.carangos.navegacao;

import android.app.Fragment;
import android.app.FragmentTransaction;

import java.util.jar.Manifest;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

/**
 * Created by kadu on 19/10/15.
 */
public enum EstadoMainActivity {
    INICIO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
            activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PUBLICACOES);
        }
    }, AGUARDANDO_PUBLICACOES {
        @Override
        public void executa(MainActivity activity) {
            ProgressFragment progress = ProgressFragment.comMensagem(R.string.carregando);
            this.colocaFragmentNaTela(activity, progress);
        }
    }, PRIMEIRAS_PUBLICACOES_RECEBIDAS {
        @Override
        public void executa(MainActivity activity) {
            ListaDePublicacoesFragment publicacoesFragment = new ListaDePublicacoesFragment();
            this.colocaFragmentNaTela(activity, publicacoesFragment);
        }
    }, PULL_TO_REFRESH_REQUISITADO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
        }
    };

    void colocaFragmentNaTela(MainActivity activity, Fragment fragment){
        FragmentTransaction tx = activity.getFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_principal, fragment);
        tx.commit();
    }

    public abstract void executa(MainActivity activity);
}

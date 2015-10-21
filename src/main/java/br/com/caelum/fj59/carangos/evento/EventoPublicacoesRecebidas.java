package br.com.caelum.fj59.carangos.evento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;

/**
 * Created by kadu on 20/10/15.
 */
public class EventoPublicacoesRecebidas extends BroadcastReceiver {

    private static final String RETORNO = "retorno";
    private static final String SUCESSO = "sucesso";
    private static final String PUBLICACOES_RECEBIDAS = "publicacoes_recebidas";

    private BuscaMaisPublicacoesDelegate delegate;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean deuCerto = intent.getBooleanExtra(SUCESSO, false);
        MyLog.i("RECEBI O EVENTO!!! DEU CERTO? " + deuCerto);

        if(deuCerto){
            delegate.lidaComRetorno((List< Publicacao>) intent.getSerializableExtra(RETORNO));
        } else {
            delegate.lidaComErro((Exception) intent.getSerializableExtra(RETORNO));
        }
    }

    public static EventoPublicacoesRecebidas registraObservador(BuscaMaisPublicacoesDelegate delegate){
        EventoPublicacoesRecebidas received = new EventoPublicacoesRecebidas();
        received.delegate = delegate;

        LocalBroadcastManager
                .getInstance(delegate.getCarangosApplication())
                .registerReceiver(received, new IntentFilter(PUBLICACOES_RECEBIDAS));

        return received;
    }

    public static void notifica(Context context, Serializable resultado, boolean sucesso){
        Intent intent = new Intent(PUBLICACOES_RECEBIDAS);

        intent.putExtra(RETORNO, resultado);
        intent.putExtra(SUCESSO, sucesso);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(CarangosApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }
}
package br.com.caelum.fj59.carangos.app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by kadu on 19/10/15.
 */
public class CarangosApplication extends Application {

    private List<Publicacao> publicacoes = new ArrayList<Publicacao>();
    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();

    @Override
    public void onTerminate(){
        super.onTerminate();

        for (AsyncTask task : this.tasks){
            task.cancel(true);
        }
    }

    public void registra(AsyncTask<?,?,?> task){
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?,?,?> task){
        tasks.remove(task);
    }

    public List<Publicacao> getPublicacoes(){
        return publicacoes;
    }
}

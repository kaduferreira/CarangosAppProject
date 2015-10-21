package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by kadu on 21/10/15.
 */
public class RegistraAparelhoTask extends AsyncTask<Void, Void, String> {

    private CarangosApplication app;

    public RegistraAparelhoTask(CarangosApplication app){
        this.app = app;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;

        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);

            MyLog.i("Aparelho registrado com o ID: " + registrationId);
            String email = InformacoesDoUsuario.getEmail(this.app);

            String url = "device/register/" + email + "/" + registrationId;
            WebClient client = new WebClient(url);
            client.post();
        } catch (Exception ex){
            MyLog.e("Problema no registro do aparelho no server!" + ex.getMessage());
        }
        return registrationId;
    }

    @Override
    protected void onPostExecute(String result) {
        app.lidaComRespostaDoRegistrationNoServidor(result);
    }
}
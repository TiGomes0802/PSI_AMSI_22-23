package com.example.ecstasyclub.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import  com.example.ecstasyclub.listeners.EventosListener;

import  com.example.ecstasyclub.utils.EventosJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonEcstasyClub {
    private static SingletonEcstasyClub instance = null;
    private static RequestQueue volleyQueue=null;

    private static final String mUrlAPI="http://192.168.1.6:8080/ecstasyclub/backend/web/index.php?r=api/";

    private EventosListener eventosListener;
    private ArrayList<Eventos> eventos;

    public void setEventosListener(EventosListener eventosListener) {
        this.eventosListener = eventosListener;
    }

    public static synchronized SingletonEcstasyClub getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonEcstasyClub(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    public SingletonEcstasyClub(Context context) {

    }

    public void getAllEventosAPI (final Context context){
        if (!EventosJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "eventos/viewalleventosdesativos", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    eventos = EventosJsonParser.parseJsonEventos(response);

                    if(eventosListener!=null)
                        eventosListener.onRefreshListaEventos(eventos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "erro", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
}

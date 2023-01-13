package com.example.ecstasyclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ecstasyclub.adaptadores.ListaEventosAdaptador;
import com.example.ecstasyclub.listeners.EventosListener;
import com.example.ecstasyclub.modelo.Eventos;
import com.example.ecstasyclub.modelo.SingletonEcstasyClub;

import java.util.ArrayList;

public class EventosFragment extends Fragment implements EventosListener {
    private ListView listaEventos;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_eventos, container, false);
        setHasOptionsMenu(true);

        listaEventos=view.findViewById(R.id.listaEventos);
        SingletonEcstasyClub.getInstance(getContext()).setEventosListener(this);
        SingletonEcstasyClub.getInstance(getContext()).getAllEventosAPI(getContext());

        /*listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(getContext(),DetalhesEventos.class);
                intent.putExtra("ID_EVENTO",(int)id);
                startActivity(intent);
                //startActivityForResult(intent,REQ_CODE_EDIT);
            }
        });*/

        return view;
    }

    @Override
    public void onRefreshListaEventos(ArrayList<Eventos> eventos) {
        if(eventos != null)
            listaEventos.setAdapter(new ListaEventosAdaptador(eventos,getContext()));
    }

}
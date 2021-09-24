package com.example.almacen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Adactador extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Equipo> ListaEquipos;

    Adactador(Context contexto,ArrayList<Equipo> listaequipo){
        this.context = contexto;
        this.ListaEquipos =listaequipo;
        //se llena el contexto que se conecta con el xml personalizado
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        //se asigna la vista del xml al componente list view de la ventana

        final Equipo equipo=(Equipo) getItem(i);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_view_lista_equipo,null);

        //le enlanzan los componente de la vista del xml personalisado
        TextView codigo = (TextView) convertView.findViewById(R.id.textcodigo);
        TextView cliente = (TextView) convertView.findViewById(R.id.textcliente);
        TextView tipoEquipo = (TextView) convertView.findViewById(R.id.tVTipoEquipo);

        //se le pasan los dato de la clase para llenar los campos
        codigo.setText("\t\t"+equipo.getCodigo());
        cliente.setText(equipo.getCliente());
        tipoEquipo.setText(equipo.getTipoEquipo());

        return convertView;
    }

    @Override
    public int getCount() {
        return ListaEquipos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaEquipos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

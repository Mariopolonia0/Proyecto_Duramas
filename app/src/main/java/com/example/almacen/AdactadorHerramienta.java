package com.example.almacen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class AdactadorHerramienta extends BaseAdapter {

        private LayoutInflater inflater;
        private Context context;
        private ArrayList<Herramienta> ListaHerramienta;

        AdactadorHerramienta(Context contexto,ArrayList<Herramienta> listaherramienta){
            this.context = contexto;
            this.ListaHerramienta =listaherramienta;
            //se llena el contexto que se conecta con el xml personalizado
            inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            final Herramienta herramienta=(Herramienta) getItem(i);

            //se asigna la vista del xml al componente list view de la ventana
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_lista_herramientas,null);


            //le enlanzan los componente de la vista del xml personalisado
            TextView codigoHerramienta = (TextView) convertView.findViewById(R.id.textCodigoHerramienta);
            TextView descripcionHerramienta = (TextView) convertView.findViewById(R.id.textdescripcionHerramienta);
            TextView cantidadHerramienta = (TextView) convertView.findViewById(R.id.textCantidadHerramienta);

            //se le pasan los dato de la clase para llenar los campos
            codigoHerramienta.setText(String.valueOf(herramienta.getCodigoHerramienta()));
            descripcionHerramienta.setText(herramienta.getDescripcionHerramienta());
            cantidadHerramienta.setText(String.valueOf(herramienta.getCantidadHerramienta()));

            return convertView;
        }

        @Override
        public int getCount() {
            return ListaHerramienta.size();
        }

        @Override
        public Object getItem(int position) {
            return ListaHerramienta.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

}



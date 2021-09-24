package com.example.almacen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdactadorMaterial extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<DetalleMaterial> ListaMaterial;

    AdactadorMaterial(Context contexto,ArrayList<DetalleMaterial> listamaterial){
        this.context = contexto;
        this.ListaMaterial =listamaterial;
        //se llena el contexto que se conecta con el xml personalizado
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final DetalleMaterial material=(DetalleMaterial) getItem(i);
        //se asigna la vista del xml al componente list view de la ventana
        convertView = LayoutInflater.from(context).inflate(R.layout.list_view_lista_materiales,null);


        //le enlanzan los componente de la vista del xml personalisado
        TextView descripcion = (TextView) convertView.findViewById(R.id.textdescripcion);
        TextView precio = (TextView) convertView.findViewById(R.id.textprecio);
        TextView cantidad =(TextView) convertView.findViewById(R.id.textCantidad);
        TextView total =(TextView) convertView.findViewById(R.id.textTotal);

        //se le pasan los dato de la clase para llenar los campos
        descripcion.setText(material.getDescripcion());
        precio.setText(material.getPrecio());
        cantidad.setText(material.getCantidad());
        total.setText(material.getTotal());

        return convertView;
    }

    @Override
    public int getCount() {
        return ListaMaterial.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaMaterial.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

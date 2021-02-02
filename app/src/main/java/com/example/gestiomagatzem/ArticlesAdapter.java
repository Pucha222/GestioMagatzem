package com.example.gestiomagatzem;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ArticlesAdapter extends CursorAdapter {

    private MainActivity mainActivity;

    public ArticlesAdapter(Context context, Cursor cursor) {
        super(context, cursor);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cela_article, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout celaContainer = view.findViewById(R.id.cela_background);
        TextView codeTV = (TextView) view.findViewById(R.id.codiTV);
        TextView descripcioTV = (TextView) view.findViewById(R.id.descripcioTV);
        TextView estocTV = (TextView) view.findViewById(R.id.estocTV);
        TextView preuTV = (TextView) view.findViewById(R.id.preuTV);
        TextView preuIVATV= (TextView) view.findViewById(R.id.preuIVATV);
        ImageView esborrarBtn = (ImageView) view.findViewById(R.id.btn_delete);

        esborrarBtn.setOnClickListener(v -> {
            View row = (View) v.getParent().getParent();
            ListView lv = (ListView) row.getParent();
            int p = lv.getPositionForView(row);
            Cursor c = (Cursor) getItem(p);

            mainActivity = (MainActivity) context;
            mainActivity.eliminar(c.getString(c.getColumnIndexOrThrow(DbHelper.COL_CODI)));

        });


        String codi = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COL_CODI));
        String descripcio = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COL_DESCRIPCIO));
        float estoc = cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COL_ESTOC));
        float pvp = cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COL_PREU));


        double preuIVA = pvp * 0.21 + pvp;
        codeTV.setText("Codi: " + codi);
        descripcioTV.setText("Descripció: " + descripcio);
        estocTV.setText("Estoc: " + estoc);
        preuTV.setText("Preu sense IVA: " + pvp);
        preuIVATV.setText("Preu amb IVA: " + preuIVA);

        if (estoc <= 0.0) {
            celaContainer.setBackgroundColor(Color.parseColor("#a83232"));
        } else {
            celaContainer.setBackgroundColor(Color.parseColor("#d1d1d1"));
        }

    }
}

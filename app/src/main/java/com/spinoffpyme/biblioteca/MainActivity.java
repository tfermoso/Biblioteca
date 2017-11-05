package com.spinoffpyme.biblioteca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.spinoffpyme.biblioteca.Datos.*;

public class MainActivity extends Activity {
    static public String KEY_LIST="numlist";
    static public String KEY_RTN_VAL1="valor1";
    private static int COD_RTN_ACT=0;
    Context ctx;
    int numLista=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;

        if(savedInstanceState!=null){
            //Estamos recreando la actividad, hay un Bundle de vuelta
            numLista=savedInstanceState.getInt(KEY_LIST,0);
        }
        //Ponemos los datos en la lista
        ListView lv=(ListView)this.findViewById(R.id.lstLibros);
        String[] dat = getNombres();
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getNombres()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //nos quedamos con el id pulsando para recrear
                numLista=position; //la posición es el índice del array
                //lanzar la otra actividad desde un intent
                Intent intent=new Intent(ctx, DetailActivity.class);
                intent.putExtra(KEY_LIST,numLista); //mandamos el número del libro a visualizar
                startActivityForResult(intent,COD_RTN_ACT); //abrimos y esperamos resultado
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode==RESULT_OK && requestCode==COD_RTN_ACT){
            //cuando vuelva la actiidad de forma correcta recogemos los valores y los mostramos
            StringBuffer sb = new StringBuffer();
            sb.append("De vuelta:\n");
            sb.append("Valor 1:"+intent.getStringExtra(KEY_RTN_VAL1)+"\n");
            sb.append("Valor 2:"+intent.getData().toString());
            Toast.makeText(ctx,sb.toString(),Toast.LENGTH_LONG).show();//mostramos en pantalla
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(KEY_LIST,numLista); //grabamos los datos para recrear la actividad
    }
}

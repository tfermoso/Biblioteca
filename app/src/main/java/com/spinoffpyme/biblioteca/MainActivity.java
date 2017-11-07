package com.spinoffpyme.biblioteca;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements DetailsFragment.InterfaceDatos {
    static public final String KEY_LIST="numlist"; //clave para acceder al parámetro para mostrar
    //Añadimos
    static public final int FRAGMENT_ID=0; //id para el fragmento, solo habrá uno
    static public final int NO_DETALLE=-1;

    Fragment fragmentAct=null; //puntero al fragmento, evitar buscarlo para borrar
    ///
    static public String KEY_RTN_VAL1="valor1"; //
    private static int COD_RTN_ACT=0;
    Context ctx;
    int numLista=NO_DETALLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;

        if(savedInstanceState!=null){
            //Estamos recreando la actividad, hay un Bundle de vuelta
            numLista=savedInstanceState.getInt(KEY_LIST,-1);
        }
        //Ponemos los datos en la lista
        ListView lv=(ListView)this.findViewById(R.id.lstLibros);

        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Datos.getNombres()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //nos quedamos con el id pulsando para recrear
                numLista=position; //la posición es el índice del array
                System.out.println(numLista);

                /**
                 * Ahora en vez de lanzar la actividad cargaremos el fragment.
                 */
                /*
                //lanzar la otra actividad desde un intent
                Intent intent=new Intent(ctx, DetailActivity.class);
                intent.putExtra(KEY_LIST,numLista); //mandamos el número del libro a visualizar
                startActivityForResult(intent,COD_RTN_ACT); //abrimos y esperamos resultado
                */

                //Para simplificar creamos una función
                /*
                Vamos a detectar si estamos en portrait o landscape,

                 */
                if(getResources().getConfiguration().orientation== ORIENTATION_PORTRAIT){
                    System.out.println("Pantalla en portrait");
                }
                addDetalles(numLista);
            }
        });
        //si tenemos que recrear la actividad se carga el detalle que había
        //la primera vez noo cargará nada
        System.out.println("Cargamos numlista: "+numLista);
        if(numLista>NO_DETALLE)addDetalles(numLista);
    }

    //función de ayuda para la gestión de los fragmentos
    private void addDetalles(int numLista) {
        Fragment fragmentOld=fragmentAct;
        //gestionamos el fragmento
        //paso1
        FragmentManager fm=getFragmentManager();
        //paso2
        FragmentTransaction transicion=fm.beginTransaction();
        //paso3
        Fragment fragment=new DetailsFragment();
        fragmentAct=fragment; //nos quedamos para evitar la búsqueda para borrar
        //pasamos los parámetros
        Bundle data=new Bundle();
        data.putInt(KEY_LIST,numLista);
        fragment.setArguments(data);
        if(fragmentOld==null){ //si no hay fragmento se añade, sino se reemplaza
            transicion.add(R.id.frmDetalles,fragment);
        }else{
            transicion.replace(R.id.frmDetalles,fragment);
        }
        //paso4
        transicion.commit();
    }

    /*
    Ahora ya no tenemos onActivityResult
     */
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

    //método de la interfaz del fragmento para la comunicación de vuelta
    @Override
    public void onComunicateData(String val1, String val2) {
        StringBuffer sb=new StringBuffer();
        sb.append("De vuelta:\n");
        sb.append("Valor 1: "+val1+"\n");
        sb.append("Valor 2: "+val2);
        Toast.makeText(ctx,sb.toString(),Toast.LENGTH_LONG).show(); //mostramos en pantalla
        //Cerramos el detalle
        removeDetalles();
    }
/*
La gestión de los fragmentos se hace desde la actividad, por eso creamos dos métodos de ayuda
Uno para añadir un fragmento y otro para borrarlo
 */
    private void removeDetalles() {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transicion=fm.beginTransaction();
        transicion.remove(fragmentAct);
        fragmentAct=null;

        transicion.commit();
    }
}

package com.spinoffpyme.biblioteca;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    EditText val1;
    EditText val2;
    private int numLista=0;
    InterfaceDatos myListener;
    DetailsFragment miCtx=null;

    //Interfaz de comunicaciones con la actividad
    public interface InterfaceDatos{
        public void onComunicateData(String val1,String val2);
    }
    //Podemos informar del detalle a mostrar por dos métodos, mediante el constructor
    //o mediante un parámetro
    public DetailsFragment() {
        // Required empty public constructor
    }
    public DetailsFragment(int num){
        this.numLista=num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miCtx=this;
        //recogemos los datos si están, sino dejamos numLista
        this.numLista=getArguments().getInt(MainActivity.KEY_LIST,numLista);
    }

    //Cuando el fragmento está unido a la actividad establecemos el listener de la interfaz
    //para poder comunicar los datos a la clase principal
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            myListener=(InterfaceDatos) context; //la actividad recibirá eventos del interfaz datos
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" No se puede comunicar con la actividad");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);

        //Ponemos los datos en pantalla después de crear el ui, nunca antes
        TextView txt=(TextView) view.findViewById(R.id.txtNombre);
        txt.setText((Datos.lista[numLista][0]));
        txt=(TextView) view.findViewById(R.id.txtAuthor);
        txt.setText(Datos.lista[numLista][1]);
        val1=(EditText) view.findViewById(R.id.txtVal1);
        val2=(EditText) view.findViewById(R.id.txtVal2);

        //Gestionamos el botón de cerrar y devolver datos
        Button btn=(Button)view.findViewById(R.id.cmdVolver);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onComunicateData(val1.getText().toString(),val2.getText().toString());
                //Se cierra el fragmento desde la actividad
            }
        });

        return view;
    }

}

package com.spinoffpyme.biblioteca;

/**
 * Created by Tomás on 29/09/2017.
 */

public final class Datos {
    static public String lista[][]={
            {"Libro 1","Autor 1 "},
            {"Libro 2","Autor 2"},
            {"Libro 3","Autor 3"},
            {"Libro 4","Autor 4"},
            {"Libro 5","Autor 5"},
            {"Libro 6","Autor 6"},
            {"Libro 7","Autor 7"},
            {"Libro 8","Autor 8"},
            {"Libro 9","Autor 9"},

    };

static public  String[] getNombres(){
    String ret[]=new String[9];
    ret[0]=lista[0][0];
    ret[1]=lista[1][0];
    ret[2]=lista[2][0];
    ret[3]=lista[3][0];
    ret[4]=lista[4][0];
    ret[5]=lista[5][0];
    ret[6]=lista[6][0];
    ret[7]=lista[7][0];
    ret[8]=lista[8][0];

    return ret;
}

}

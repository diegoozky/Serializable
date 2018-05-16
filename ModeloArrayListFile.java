
/**
 * Implementa la parte de Modelo de Datos con un array que se salva a fichero
 * cada vez que se cambian los datos ( Solucion poco eficiente pues siempre graba todo 
 * el contenido no solo lo que ha cambiado )
 * Formato del fichero
 *  int codigo;    // Código del producto, se utiliza para buscar
    String nombre; // Nombre un texto
    int stock;    // existencia actuales
    int stock_min; // Número mínimo de existencias recomedadas
    float precio;
    codigo, nombre, stock, stock_min, precio
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

public class ModeloArrayListFile extends ModeloArrayList
{
    static final String nombrefichero = "productos.objetos";
    ArrayList<Producto> ListaProdu;
    public ModeloArrayListFile()
    {
       super();
       cargarDesdeFichero();
       
    }
    
    private void salvarAfichero(){
        File fproductos =new File (nombrefichero);       
        try {
            FileOutputStream fos = new FileOutputStream(fproductos);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Producto i : lista){
                oos.writeObject(i);
            }
            oos.close();
            fos.close();
        }
        catch ( IOException ex){
            System.err.println("Error al leer el fichero de productos.");
        }
   
    }
    
    private void cargarDesdeFichero() {
        File FichProdu = new File(nombrefichero) ;
        if(!FichProdu.exists()){
        return;
        }
        try{
            FileInputStream fis = new FileInputStream(FichProdu);
            ObjectInputStream ois= new ObjectInputStream(fis);
            try{
                Producto aux = (Producto) ois.readObject();
                while(true){
                    lista.add(aux);
                    aux = (Producto) ois.readObject();
                }
            }
            catch(IOException e){}
            ois.close();
            fis.close();
        }
            catch( IOException ex){
                ex.printStackTrace();
            }
            catch(ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
            
        
    }

    public boolean insertarProducto ( Producto p){
      boolean resu = super.insertarProducto(p);
      if ( resu ){
          salvarAfichero();
      }
      return resu;
    }
 
    public boolean borrarProducto ( int codigo ){
      boolean resu = super.borrarProducto(codigo);
      if ( resu ){
          salvarAfichero();
        }
      return resu;
    }
    
    
    public boolean modificarProducto (Producto nuevo){
       boolean resu = super.modificarProducto(nuevo);
       if ( resu ){
           salvarAfichero();
        }
       return (resu);
    }
    
        
}    
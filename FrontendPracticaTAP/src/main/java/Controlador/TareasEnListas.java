package Controlador;

import java.util.ArrayList;

import Modelo.Listas;
import Modelo.Tareas;
import Modelo.TareasLista;

public class TareasEnListas {

	//Singleton
	private static TareasEnListas organizar;
	
	public ArrayList<TareasLista> tareas;
	public ArrayList<String> listas;
	
    private TareasEnListas() {//singleton
    	tareas = new ArrayList<TareasLista>();
    	listas = new ArrayList<String>();
    }

    public static TareasEnListas getSingletonInstance() {
        if (organizar == null){
        	organizar = new TareasEnListas();
        }
        
        return organizar;
    }
    
    public void organizar() {
    	Conexion conexion = Conexion.getSingletonInstance();
    	tareas = new ArrayList<TareasLista>();
    	String lista = "";
    	for (Tareas tarea : conexion.tareas) {
    		if(tarea.idUsuario == conexion.usuario[0].idUsuario) {
    			for (Listas lista_organizar : conexion.listas) {
    				if(tarea.idLista == lista_organizar.idLista) {
    					lista = lista_organizar.nombreLista;
    					NombreLista(lista);
    				}
    			}
    			
    			tareas.add(new TareasLista(tarea.idTarea, tarea.idLista, tarea.nombreTarea, tarea.descripcion, tarea.estado, tarea.idPrioridad, tarea.fecha));
    		}
		}
    }

	private void NombreLista(String nombreListalista) {
		boolean existeNombre = false;
		for (String listaArray : listas) {
			if(listaArray.equals(nombreListalista))
				existeNombre = true;
		}
		if(!existeNombre)
			listas.add(nombreListalista);
		
	}
}

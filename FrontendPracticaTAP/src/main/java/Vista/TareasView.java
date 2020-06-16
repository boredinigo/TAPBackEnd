package Vista;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import Controlador.Conexion;
import Controlador.TareasEnListas;

public class TareasView extends VerticalLayout{

	
	Conexion conexion = Conexion.getSingletonInstance();
	TareasEnListas tareasEnListas = TareasEnListas.getSingletonInstance();
	
	/*
	public VerticalLayout DivTareas() {
		VerticalLayout tareas = new VerticalLayout();
		

        Map<Tab, Component> tabsToPages = new HashMap<>();
        Tabs tabs = new Tabs();
        Div pages = new Div();
        
		conexion.leerLista();
		conexion.leerTareas();
		tareasEnListas.organizar();
		tareas.add(añadirTarea());
		
		if (!tareasEnListas.tareas.isEmpty())
		{
			Tab tab = new Tab();
			Div pagina = new Div();
			
			for (String lista : tareasEnListas.listas) {
				System.out.println("LISTA: ");
				tab = new Tab(lista);
				pagina = new Div();
		        tabsToPages.put(tab, pagina);
		        tabs.add(tab);
		        pagina.setVisible(false);
		        
		        generarTarea(lista, pagina);
		        
		        pages.add(pagina);
			}
	
	        pagina.setVisible(true);
			Set<Component> pagesShown = Stream.of(pagina)
	                .collect(Collectors.toSet());
			
	
	        pagesShown.forEach(page -> page.setVisible(false));
	        pagesShown.clear();
	        Component seleccionarPag = tabsToPages.get(tabs.getSelectedTab());
	        seleccionarPag.setVisible(true);
	        pagesShown.add(seleccionarPag);
			
	        tabs.addSelectedChangeListener(event -> {
	        	seleccionarPagina();
	            pagesShown.forEach(page -> page.setVisible(false));
	            pagesShown.clear();
	            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	            selectedPage.setVisible(true);
	            pagesShown.add(selectedPage);
	        });
			
	        
	        tareas.add(tabs);
	        tareas.add(pages);
		}
        return tareas;
	}

	private Component añadirTarea() {
		HorizontalLayout list1 = new HorizontalLayout();
        
        TextField titleField = new TextField();
        titleField.setLabel("Tarea");
        
        TextField tfLista = new TextField();
        tfLista.setLabel("Lista");
        
        ComboBox<String> cbPrioridad = new ComboBox<>();
        cbPrioridad.setItems("Default", "low", "medium", "high");
        cbPrioridad.setLabel("Prioridad");
        
        ComboBox<String> cbEstado = new ComboBox<>();
        cbEstado.setItems("ToDo", "DONE");
        cbEstado.setLabel("Estado");
        
        
        DatePicker datePicker = new DatePicker();
        datePicker.setClearButtonVisible(true); 
        datePicker.setLabel("Fecha");
        
        
        Button boton = new Button("Añadir Tarea Nueva");
        boton.addClickListener(event -> {
        	Conexion conexion = Conexion.getSingletonInstance();
        	try {
				if(conexion.addTarea(titleField.getValue(), cbPrioridad.getValue(), cbEstado.getValue(), datePicker.getValue(), tfLista.getValue())) {
					Notification.show("Guardada la tarea: " + titleField.getValue());
					UI.getCurrent().getPage().reload();
				}
				else
					Notification.show("NO Guardada la tarea: " + titleField.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				Notification.show("NO Guardada la tarea: " + titleField.getValue());
			}
        });
        
        list1.add(titleField, tfLista, cbPrioridad, cbEstado, datePicker, boton);
        
        list1.setAlignItems(Alignment.CENTER);
		return list1;
	}

	private void generarTarea(String lista, Div pages) {
		for (Tareas tarea : organizarTareasYListas.tareas) {
			if (tarea.list.equals(lista)) {

				System.out.println(tarea.name);
				
				HorizontalLayout list1 = new HorizontalLayout();
		        
		        TextField titleField = new TextField();
		        titleField.setLabel("Tarea");
		        titleField.setValue(tarea.name);
		        
		        ComboBox<String> cbPrioridad = new ComboBox<>();
		        cbPrioridad.setItems("Default", "low", "medium", "high");
		        cbPrioridad.setLabel("Prioridad");
		        cbPrioridad.setValue(tarea.priority);
		        
		        ComboBox<String> cbEstado = new ComboBox<>();
		        cbEstado.setItems("ToDo", "DONE");
		        cbEstado.setLabel("Estado");
		        cbEstado.setValue(tarea.status);
		        
		        
		        DatePicker datePicker = new DatePicker();
		        datePicker.setValue(tarea.deadline);

		        datePicker.setClearButtonVisible(true); 
		        datePicker.setLabel("Fecha");
		        
		        
		        Button boton = new Button("Guardar");
		        boton.setId(Integer.toString(tarea.id));
		        boton.addClickListener(event -> {
	            	ConexionAPI conexionAPI = ConexionAPI.getSingletonInstance();
	            	try {
						if(conexionAPI.actualizarTarea(titleField.getValue(), cbPrioridad.getValue(), cbEstado.getValue(), datePicker.getValue(), boton.getId().toString()))
							Notification.show("Guardada la tarea: " + titleField.getValue());
						else
							Notification.show("NO Guardada la tarea: " + titleField.getValue());
					} catch (Exception e) {
						e.printStackTrace();
						Notification.show("NO Guardada la tarea: " + titleField.getValue());
					}
	            });

		        Button btnBorrar = new Button("BORRAR");
		        btnBorrar.setId(Integer.toString(tarea.id));
		        btnBorrar.addClickListener(event -> {
	            	ConexionAPI conexionAPI = ConexionAPI.getSingletonInstance();
	            	try {
						if(conexionAPI.borrarTarea(boton.getId().toString())) {
							Notification.show("Tarea: \"" + titleField.getValue() + "\" borrada");
	            			list1.removeAll();
						}
						else
							Notification.show("NO borrada la tarea: " + titleField.getValue());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Notification.show("NO borrada la tarea: " + titleField.getValue());
					}
	            });
		        
		        
		        list1.add(titleField, cbPrioridad, cbEstado, datePicker, boton, btnBorrar);

		        pages.add(list1);
			}
		}
	}
	 */
}

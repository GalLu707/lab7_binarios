/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductordemusica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class AdministrarArchivos {
    private final String Ruta_Archivo = "playlist.dat";
    
  public void guardarPlaylist(ArrayList<cancion> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Ruta_Archivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Error al guardar la playlist: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<cancion> cargarPlaylist() {
        File archivo = new File(Ruta_Archivo);
        if (!archivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (ArrayList<cancion>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
           
            return new ArrayList<>();
        }
    }

    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductordemusica;
import java.io.Serializable;
/**
 *
 * @author USER
 */
public class cancion implements Serializable {
    private static final long serialVersionUID = 1L; // Buena práctica para Serializable
    private String nombre;
    private String artista;
    private double duracion; // Guardado como decimal (ej: 3.5 = 3min 30seg)
    private String rutaImagen;
    private String rutaArchivoAudio;
    private Genero genero;
    
    public cancion(String nombre, String artista, double duracion, String rutaImagen, String rutaArchivoAudio, Genero genero){
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.rutaImagen = rutaImagen;
        this.rutaArchivoAudio = rutaArchivoAudio;
        this.genero = genero;
    }
    
      public String getDuracionFormateada() {
        int min = (int) duracion;
        int seg = (int) ((duracion - min) * 60);
        return String.format("%d:%02d", min, seg);
    }

    public String getNombre() { return nombre; }
    public String getArtista() { return artista; }
    public String getRutaImagen() { return rutaImagen; }
    public String getRutaArchivoAudio() { return rutaArchivoAudio; }
    public Genero getGenero() { return genero; }
    
    public String tostring(){
        return nombre + "- "+"\nArtista"+ artista + "( "+genero+")";
    }
    @Override
    public String toString() {
    return this.nombre; 
}
    
    
    
    
    
}

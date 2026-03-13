/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductordemusica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.player.Player;


/**
 *
 * @author USER
 */
public class ReproductorAudio {
    private Clip clip;
    private long posicionPausa;
     private Player player; 

    private String rutaActual;
    


    public boolean estaReproduciendo() {
        return clip != null && clip.isRunning();
    }

    public void reproducirMP3(String ruta) {
        pararMusica();
    try {
        FileInputStream fis = new FileInputStream(ruta);
        Player player = new Player(fis);

        
        new Thread(() -> {
            try {
                System.out.println("Reproduciendo: " + ruta);
                player.play();
            } catch (Exception e) {
                System.out.println("Error al dar play: " + e.getMessage());
            }
        }).start();

    } catch (Exception e) {
        System.out.println("No se encontró el archivo o formato no válido: " + e.getMessage());
    }
}
     public void pararMusica() {
        if (player != null) {
            player.close(); 
            player = null;
            System.out.println("Música detenida ");
        }
    }
    
    
}

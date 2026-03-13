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
    
    public void play(String rutaArchivo) {
        if (rutaArchivo.equals(rutaActual) && clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(posicionPausa);
            clip.start();
            return;
        }
    stop(); 
        try {
            File archivo = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            rutaActual = rutaArchivo;
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir audio: " + e.getMessage());
        }
    }
    
     public void pause() {
        if (clip != null && clip.isRunning()) {
            posicionPausa = clip.getMicrosecondPosition();
            clip.stop();
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
            posicionPausa = 0;
            rutaActual = null;
        }
    }

    public boolean estaReproduciendo() {
        return clip != null && clip.isRunning();
    }

    public void reproducirMP3(String ruta) {
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
            System.out.println("Música detenida ");
        }
    }
    
    
}

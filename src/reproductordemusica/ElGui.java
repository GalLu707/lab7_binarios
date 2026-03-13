/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductordemusica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 *
 * @author USER
 */
public class ElGui extends JFrame{


    
    private JList<cancion> jlPlaylist;
    private DefaultListModel<cancion> modeloLista;
    private JLabel lblImagenPortada;
    private JLabel lblInfoCancion;
    
    
    private AdministrarArchivos adminArchivos;
    private ReproductorAudio reproductorAudio;
    private cancion cancionSeleccionada;
    
    private Color rosadoClaro = new Color(255, 230, 235);
    private Color rosadobtn = new Color(255, 182, 193);
    public ElGui(){
        adminArchivos = new AdministrarArchivos();
        reproductorAudio = new ReproductorAudio();
        modeloLista = new DefaultListModel<>();

        VentanaPro();
        cargarPlaylistInicial();
    }
    
    
    private void VentanaPro(){
        
      
        setTitle("reproductor pro");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(rosadoClaro);
              
        jlPlaylist = new JList<>(modeloLista);
        jlPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlPlaylist.addListSelectionListener(e -> actualizarSeleccion()); 
        JScrollPane scrollPane = new JScrollPane(jlPlaylist);
        add(scrollPane, BorderLayout.CENTER);


        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(250, 0));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelDerecho.setBackground(rosadoClaro);

        
        lblImagenPortada = new JLabel("Sin Portada", SwingConstants.CENTER);
        lblImagenPortada.setPreferredSize(new Dimension(200, 200));
        lblImagenPortada.setMaximumSize(new Dimension(200, 200));
        lblImagenPortada.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblImagenPortada.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblInfoCancion = new JLabel("Seleccione una canción");
        lblInfoCancion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInfoCancion.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panelDerecho.add(lblImagenPortada);
        panelDerecho.add(lblInfoCancion);
        add(panelDerecho, BorderLayout.EAST);

        jlPlaylist.setModel(modeloLista); 
       
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
         panelControles.setBackground(rosadoClaro);
       JButton btnPlay = crearBotonRosado("reproducir");
        JButton btnPause = crearBotonRosado("pausar");
        JButton btnStop = crearBotonRosado("parar");
        JButton btnAdd = crearBotonRosado("añadir");
        JButton btnRemove = crearBotonRosado("remover");
     
        btnPlay.addActionListener(e -> {
  System.out.println("Índice seleccionado: " + jlPlaylist.getSelectedIndex());
    Object valor = jlPlaylist.getSelectedValue();
    System.out.println("Valor recuperado: " + valor);

    if (valor != null) {
        cancion seleccionada = (cancion) valor;
        reproductorAudio.reproducirMP3(seleccionada.getRutaArchivoAudio());
    } else {
        JOptionPane.showMessageDialog(this, "La lista cree que no hay nada seleccionado");
    }
            
            
            
            cancion seleccionada = (cancion) jlPlaylist.getSelectedValue();

            if (seleccionada != null) {
                
                reproductorAudio.reproducirMP3(seleccionada.getRutaArchivoAudio());
            } else {
                JOptionPane.showMessageDialog(this, "Primero selecciona una canción de la lista");
            }
        });
  
        btnPause.addActionListener(e -> accionPause());
        btnStop.addActionListener(e->{
            reproductorAudio.pararMusica(); 
        });
         
        btnAdd.addActionListener(e -> accionAdd());
        btnRemove.addActionListener(e -> accionRemove());

        panelControles.add(btnPlay);
        panelControles.add(btnPause);
        panelControles.add(btnStop);
        panelControles.add(new JSeparator(JSeparator.VERTICAL));
        panelControles.add(btnAdd);
        panelControles.add(btnRemove);

        add(panelControles, BorderLayout.SOUTH);

        setVisible(true);


}
    
     private JButton crearBotonRosado(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(Color.pink);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(rosadoClaro, 2));
        return b;
    }
    
    
    private void cargarPlaylistInicial() {
        ArrayList<cancion> lista = adminArchivos.cargarPlaylist();
        for (cancion c : lista) {
            modeloLista.addElement(c);
        }
    }
 
    private void actualizarSeleccion() {
        cancionSeleccionada = jlPlaylist.getSelectedValue();
        if (cancionSeleccionada != null) {
            lblInfoCancion.setText("<html><center>" + cancionSeleccionada.getNombre() + "<br>" +
                                  cancionSeleccionada.getArtista() + " (" + cancionSeleccionada.getGenero() + ")</center><html>");
            cargarImagen(cancionSeleccionada.getRutaImagen());
        } else {
            lblInfoCancion.setText("Seleccione una canción");
            lblImagenPortada.setIcon(null);
            lblImagenPortada.setText("Sin Portada");
        }
    }
    
    private void cargarImagen(String ruta) {
        if (ruta != null && !ruta.isEmpty()) {
            File file = new File(ruta);
            if (file.exists()) {
                ImageIcon imageIcon = new ImageIcon(ruta);
              
                Image image = imageIcon.getImage();
                Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                lblImagenPortada.setIcon(new ImageIcon(newimg));
                lblImagenPortada.setText(""); 
                return;
            }
        }
        lblImagenPortada.setIcon(null);
        lblImagenPortada.setText("Imagen no encontrada");
    }

    private void accionPlay() {
        if (cancionSeleccionada != null) {
            reproductorAudio.play(cancionSeleccionada.getRutaArchivoAudio());
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una canción de la lista.");
        }
    }

    private void accionPause() {
        reproductorAudio.pause();
    }

    private void accionStop() {
        reproductorAudio.stop();
    }

    private void accionAdd() {
        
        JFileChooser chooserMultimedia = new JFileChooser();
        chooserMultimedia.setDialogTitle("Seleccione archivo (MP3 o MP4)");


        javax.swing.filechooser.FileNameExtensionFilter filtroMedia
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivos Multimedia (MP3, MP4)", "mp3", "mp4");
        chooserMultimedia.setFileFilter(filtroMedia);

        if (chooserMultimedia.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivoMultimedia = chooserMultimedia.getSelectedFile();

        
            double duracionMinutos = calcularDuracionAudio(archivoMultimedia);

       
            JFileChooser chooserImagen = new JFileChooser();
            chooserImagen.setDialogTitle("Seleccione portada (JPG/PNG)");

            javax.swing.filechooser.FileNameExtensionFilter filtroImagen
                    = new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg");
            chooserImagen.setFileFilter(filtroImagen);

            String rutaImagen = "";
            if (chooserImagen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
    
                rutaImagen = chooserImagen.getSelectedFile().getAbsolutePath();
            } else {
                
                rutaImagen = "sin_foto.png";
            }


            String nombre = JOptionPane.showInputDialog(this, "Nombre del archivo:");
            String artista = JOptionPane.showInputDialog(this, "Artista:");

            Genero generoSeleccionado = (Genero) JOptionPane.showInputDialog(this, "Seleccione el Género:", "Género",
                    JOptionPane.QUESTION_MESSAGE, null, Genero.values(), Genero.BACHATA);

            if (nombre != null && artista != null && generoSeleccionado != null) {
                
                cancion nuevaCancion = new cancion(nombre, artista, duracionMinutos, rutaImagen,
                        archivoMultimedia.getAbsolutePath(), generoSeleccionado);

                modeloLista.addElement(nuevaCancion);
                actualizarArchivoBinario();

                
                JOptionPane.showMessageDialog(this, "¡Archivo agregado con éxito!");
            }
        
        
        }
    }
        
        
        
        


    private void accionRemove() {
        int indiceSeleccionado = jlPlaylist.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            cancion cancionAEliminar = modeloLista.getElementAt(indiceSeleccionado);
            
            
            if (reproductorAudio.estaReproduciendo() && cancionSeleccionada != null &&
                cancionAEliminar.getRutaArchivoAudio().equals(cancionSeleccionada.getRutaArchivoAudio())) {
                reproductorAudio.stop();
            }

            modeloLista.remove(indiceSeleccionado);
            actualizarArchivoBinario();
            reproductorAudio.stop();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una canción para eliminar.");
        }
    }

    
    private void actualizarArchivoBinario() {
        ArrayList<cancion> listaTemp = new ArrayList<>();
        for (int i = 0; i < modeloLista.size(); i++) {
            listaTemp.add(modeloLista.get(i));
        }
        adminArchivos.guardarPlaylist(listaTemp);
    }

    private double calcularDuracionAudio(File file) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(file)) {
            AudioFormat format = ais.getFormat();
            long frames = ais.getFrameLength();
            double segundos = (frames + 0.0) / format.getFrameRate();
            return segundos / 60.0; 
        } catch (UnsupportedAudioFileException | IOException e) {
            
            System.err.println("No se pudo calcular la duración: " + e.getMessage());
            return 0.0;
        }
    }
}
    
    

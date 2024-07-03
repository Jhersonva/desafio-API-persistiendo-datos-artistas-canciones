package com.desafioalura.screensound.Principal;

import com.desafioalura.screensound.model.Artista;
import com.desafioalura.screensound.model.Cancion;
import com.desafioalura.screensound.model.TipoArtista;
import com.desafioalura.screensound.repository.ArtistaRepository;
import com.desafioalura.screensound.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    // Repositorio para interactuar con la base de datos
    private final ArtistaRepository repositorio;
    // Escáner para leer la entrada del usuario
    private Scanner teclado = new Scanner(System.in);

    // Constructor que recibe el repositorio
    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Método para mostrar el menú principal
    public void muestraElMenu() {
        var opcion = -1;

        // Bucle para mostrar el menú hasta que el usuario elija salir (opción 9)
        while (opcion != 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Registrar artistas
                    2- Registrar canciones
                    3- Listar canciones
                    4- Buscar canciones por artistas
                    5- Buscar los dados sobre un artista
                                    
                    9 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine(); // Consumir la nueva línea

            // Selección de la opción del menú
            switch (opcion) {
                case 1:
                    registrarArtista();
                    break;
                case 2:
                    registrarCanciones();
                    break;
                case 3:
                    listarCanciones();
                    break;
                case 4:
                    buscarCancionesPorArtista();
                    break;
                case 5:
                    buscarDatosDelArtista();
                    break;
                case 9:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

        private void buscarDatosDelArtista() {
        System.out.println("¿Sobre que artista desea buscar? ");
        var nombre = teclado.nextLine();
        var respueta = ConsultaChatGPT.obtenerInformacion(nombre);
        System.out.println(respueta.trim());
    }

    // Método para buscar canciones por nombre de artista
    private void buscarCancionesPorArtista() {
        System.out.println("¿Desea buscar canciones de que artista? ");
        var nombre = teclado.nextLine();
        List<Cancion> canciones = repositorio.buscaCancionesPorArtista(nombre);
        canciones.forEach(System.out::println);
    }

    // Método para listar todas las canciones registradas
    private void listarCanciones() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getCanciones().forEach(System.out::println));
    }

    // Método para registrar una nueva canción
    private void registrarCanciones() {
        System.out.println("¿Desea registrar la canción de que artista? ");
        var nombre = teclado.nextLine();
        Optional<Artista> artista = repositorio.findByNombreContainingIgnoreCase(nombre);
        if (artista.isPresent()) {
            System.out.println("Indique el título de la canción: ");
            var nombreCancion = teclado.nextLine();
            Cancion cancion = new Cancion(nombreCancion);
            cancion.setArtista(artista.get());
            artista.get().getCanciones().add(cancion);
            repositorio.save(artista.get());
        } else {
            System.out.println("Artista no encontrado");
        }
    }

    // Método para registrar un nuevo artista
    private void registrarArtista() {
        var registrarNuevo = "S";

        // Bucle para registrar múltiples artistas si el usuario lo desea
        while (registrarNuevo.equalsIgnoreCase("s")) {
            System.out.println("Ingrese el nombre del artista: ");
            var nombre = teclado.nextLine();
            System.out.println("Indique el tipo del artista: (solo, duo o banda)");
            var tipo = teclado.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nombre, tipoArtista);
            repositorio.save(artista);
            System.out.println("Desea registrar un nuevo artista? (S/N)");
            registrarNuevo = teclado.nextLine();
        }
    }
}

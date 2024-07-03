package com.desafioalura.screensound.repository;

import com.desafioalura.screensound.model.Artista;
import com.desafioalura.screensound.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Definición de la interfaz ArtistaRepository que extiende JpaRepository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    // Método para buscar un artista por nombre, ignorando mayúsculas y minúsculas
    Optional<Artista> findByNombreContainingIgnoreCase(String nombre);

    // Consulta personalizada para buscar canciones por nombre de artista
    @Query("SELECT m FROM Artista a JOIN a.canciones m WHERE a.nombre ILIKE %:nombre%")
    List<Cancion> buscaCancionesPorArtista(String nombre);
}

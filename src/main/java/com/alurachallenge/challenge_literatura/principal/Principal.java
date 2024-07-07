package com.alurachallenge.challenge_literatura.principal;

import com.alurachallenge.challenge_literatura.models.*;
import com.alurachallenge.challenge_literatura.repository.AutorRepository;
import com.alurachallenge.challenge_literatura.repository.LibroRepository;
import com.alurachallenge.challenge_literatura.service.ApiService;
import com.alurachallenge.challenge_literatura.service.ConvertidorDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ApiService apiService = new ApiService();
    private final ConvertidorDatos convertidorDatos = new ConvertidorDatos();
    private final Scanner scanner = new Scanner(System.in);


    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;


    public Principal(AutorRepository autor, LibroRepository libro) {
        this.autorRepository = autor;
        this.libroRepository = libro;
    }

    public void muestraMenu() {

        int opc = -1;
        while (opc != 0) {
            String menu = """
                    1 - Buscar libros por titulo
                    2 - Mostrar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idiomas
                    0 - Salir
                    """;
            System.out.println(menu);
            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnUnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }

    }

    private Datos getDatosLibro(String titulo) {
        String url = URL_BASE + "?search=" + titulo.replace(" ", "+");
        String json = apiService.obtenerDatos(url);
        return convertidorDatos.obtenerDatos(json, Datos.class);
    }


    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro");
        String titulo = scanner.nextLine();
        Datos datos = getDatosLibro(titulo);

        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(l.titulo().toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            DatosLibros datosLibroEcontrado = libroBuscado.get();
            System.out.println("Libro encontrado");
            System.out.println(datosLibroEcontrado);


            DatosAuthor autor = datosLibroEcontrado.autor().get(0);
            Autor autor1 = new Autor(autor);
            autorRepository.save(autor1);

            Libro libro = new Libro(datosLibroEcontrado);
            libro.setAuthor(autor1);
            libro.setAutor(autor1.getNombre());
            libroRepository.save(libro);

        }else {
            System.out.println("Libro no encontrado");

        }
    }





    private void mostrarLibrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
    }

    private void listarLibrosPorIdiomas() {
    }


}

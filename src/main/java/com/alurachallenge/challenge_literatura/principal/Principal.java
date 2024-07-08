package com.alurachallenge.challenge_literatura.principal;

import com.alurachallenge.challenge_literatura.models.*;
import com.alurachallenge.challenge_literatura.repository.AutorRepository;
import com.alurachallenge.challenge_literatura.repository.LibroRepository;
import com.alurachallenge.challenge_literatura.service.ApiService;
import com.alurachallenge.challenge_literatura.service.ConvertidorDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ApiService apiService = new ApiService();
    private final ConvertidorDatos convertidorDatos = new ConvertidorDatos();
    private final Scanner scanner = new Scanner(System.in);

    private List<Libro> libros;
    private List<Autor> autores;
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
                    ------------------------------
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

    private Datos getDatosLibro() {
        System.out.println("Ingrese el titulo del libro");
        String titulo = scanner.nextLine();

        String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);

        String url = URL_BASE + "?search=" + tituloCodificado.replace(" ", "+");
        String json = apiService.obtenerDatos(url);
        return convertidorDatos.obtenerDatos(json, Datos.class);


    }


    private void buscarLibroPorTitulo() {

        Datos datos = getDatosLibro();

//
//        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(l.titulo().toUpperCase()))
//                .findFirst();
//
//        if(libroBuscado.isPresent()){
//            DatosLibros datosLibroEcontrado = libroBuscado.get();
//            System.out.println("Libro encontrado");
//            System.out.println(datosLibroEcontrado);
//
//
//            DatosAuthor autor = datosLibroEcontrado.autor().get(0);
//            Autor autor1 = new Autor(autor);
//            autorRepository.save(autor1);
//
//            Libro libro = new Libro(datosLibroEcontrado);
//            libro.setAuthor(autor1);
//            libro.setAutor(autor1.getNombre());
//            libroRepository.save(libro);
//
//        }else {
//            System.out.println("Libro no encontrado");
//
//        }

        if (datos != null && !datos.resultados().isEmpty()) {
            DatosLibros datoslibroEcontrado = datos.resultados().get(0);

            DatosAuthor datosAutor = datoslibroEcontrado.autor().get(0);
            Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre()).orElseGet(() -> {
                Autor autor1 = new Autor(datosAutor);
                return autorRepository.save(autor1);
            });

            Optional<Libro> libroExiste = libroRepository.findByTituloIgnoreCase(datoslibroEcontrado.titulo());

            if (libroExiste.isPresent()) {
                System.out.println("\nEl libro ya esta registrado, pruebe con otro libro\n");
            } else {
                Libro libroEcontrado = new Libro(datoslibroEcontrado);
                libroEcontrado.setAutor(autor);
                libroRepository.save(libroEcontrado);
                System.out.println(libroEcontrado);
                System.out.println("Libro registrado exitosamente\n");
            }

        } else {
            System.out.println("\nLibro no encontrado, pruebe con otro libro\n");

        }

    }

    private void mostrarLibrosRegistrados() {
     libros  = libroRepository.findAll();

        if (libros.isEmpty()){
            System.out.println("No hay libros registrados");
        }else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();

        if (autores.isEmpty()){
            System.out.println("No hay autores registrados");
        }else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("Ingrese el año en el cual desea buscar autores vivos");
        int anio = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autoresVivos = autorRepository.autoresVivosEnUnDeterminadoAnio(anio);
        autoresVivos.forEach(System.out::println);

    }
    private void listarLibrosPorIdiomas() {
        System.out.println("Ingrese el idioma en el cual desea buscar los libros");
        int opc = -1;
        while (opc != 0) {
            String menuIdiomas = """
                    1 - Español
                    2 - Ingles
                    3 - Frances
                    4 - Portugues
                    0 - Salir
                    """;
            System.out.println(menuIdiomas);
            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:
                    List<Libro> librosEspañol = libroRepository.findByIdioma(Idioma.ES);
                    if(librosEspañol.isEmpty()){
                        System.out.println("No hay libros en español");
                    }else {
                        librosEspañol.forEach(System.out::println);
                    }
                    break;
                case 2:
                    List<Libro> librosIngles = libroRepository.findByIdioma(Idioma.EN);
                    if(librosIngles.isEmpty()){
                        System.out.println("No hay libros en inglés");
                    }else {
                        librosIngles.forEach(System.out::println);
                    }
                    break;
                case 3:
                    List<Libro> librosFrances = libroRepository.findByIdioma(Idioma.FR);
                    if(librosFrances.isEmpty()){
                        System.out.println("No hay libros en francés");
                    }else {
                        librosFrances.forEach(System.out::println);
                    }
                    break;
                case 4:
                    List<Libro> librosPortugues = libroRepository.findByIdioma(Idioma.PT);
                    if(librosPortugues.isEmpty()){
                        System.out.println("No hay libros en portugués");
                    }else {
                        librosPortugues.forEach(System.out::println);
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        }

    }


}

package com.aluracursos.LiterAluraApplication.Principal;

import com.aluracursos.LiterAluraApplication.Model.*;
import com.aluracursos.LiterAluraApplication.Service.ConsumoAPI;
import com.aluracursos.LiterAluraApplication.Service.ConvierteDatos;
import com.aluracursos.LiterAluraApplication.repository.AutorRepository;
import com.aluracursos.LiterAluraApplication.repository.LibroRepository;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private  static  final String URL_BASE = "https://gutendex.com/books/";
    private static final String IDIOMA ="?languages=es";
    private static final String BUSQUEDA = "?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Optional<DatosLibro> libroBuscado;
    //Variable global para listar los libros de la base de datos
    List<Libro> librosBuscados = new ArrayList<>();
    List <Autor> autoresBuscados = new ArrayList<>();
    private LocalDateTime fecha;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    //private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    public Principal(AutorRepository autorRepository) { // COnstructor necesario para uso de repositorio
        this.repositorioAutor=autorRepository;
    }

    public void muestraElMenu() {

        // No es posible crear de esta manera ya que SeireRepository es una interface que  extiende de otra interface
        //SerieRepository serieRepository =new SerieRepository() {
        //}
        // Debemos hacer una inyeccion de dependencias
        // Se necesita hacer una cierta notacion en una clase que Spring maneja
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                       ELIJA UNA OPCION DEL MENU (SOLO NUMEROS)
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado anio
                    5 - Listar libros por idioma     
                    6 - Borrar libros del registro     
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarlibro();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnUnAnio();
                    break;
                case 5:
                    mostraribrosporIdiomas();
                    break;
                case 6:
                    autoresBuscados =repositorioAutor.findAll();
                    if (autoresBuscados.isEmpty()){
                        System.out.println("La base de datos ya esta vacia");
                    }else{
                        repositorioAutor.deleteAll();
                    }

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }



    }

    private String getDatosJson(String nombreLibro){
        var json = consumoAPI.obtenerDatos(URL_BASE+BUSQUEDA+nombreLibro.replace(" ","+").toLowerCase());
        System.out.println(json);
        return json;
    }

    private DatosLibro getDatosLIbro (String json){
        DatosLibro datosLibro = convierteDatos.obtenerDatos(json, DatosLibro.class);
            return datosLibro;
    }


    private void buscarlibro() {

        System.out.println("Ingresa el titulo del libro");
        var nombreLibro = teclado.nextLine();

        var json = getDatosJson(nombreLibro);
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);

        if (datos.numero() == 0) {
            System.out.println("Libro no encontrado");
        } else {
            Optional<DatosLibro> datosLibro = datos.resultado().stream()
                    .filter(d -> d.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                    .findFirst();

            if (datosLibro.isPresent()) {
                var librobuscado = datosLibro.get();
                System.out.println("--------- Libro ---------" +
                        "\n Titulo: " + librobuscado.titulo() +
                        "\n Autor:" + librobuscado.autor().stream()
                        .map(a -> a.nombreAutor()).limit(1).collect(Collectors.joining()) +
                        "\n Idioma: " + librobuscado.idiomas() +
                        "\n Descargas: " + librobuscado.descargas() +
                        "\n -----------------------\n");
                try {
                    Optional<Libro> libroDB = repositorioAutor.buscarLibroPorNombre(nombreLibro);

                    Optional<Autor> autorDB = repositorioAutor.buscarAutorPorNombre(datosLibro.get().autor().stream()
                            .map(a -> a.nombreAutor())
                            .collect(Collectors.joining()));

                    //Obtenemos el autor del libro buscado
                    Autor primerAutor = datosLibro.stream()
                            .flatMap(d -> d.autor().stream()
                                    .map(a -> new Autor(a)))
                                    .collect(Collectors.toList()).stream().findFirst().get();

                    if( libroDB.isPresent()){
                        System.out.println("EL libro ya se encuentra en la base de datos en la tabla libros con un ID de  " + libroDB.get().getId() );
                    }else{
                        // Creamos autor
                        Autor autor;
                        if(autorDB.isPresent()){
                            autor = autorDB.get();
                            System.out.println("EL autor se encuentra en la base de datos con un ID en la tabla autores de " + autorDB.get().getId());
                        }else{
                            autor = primerAutor;
                            repositorioAutor.save(autor);
                        }
                        autor.setLibros(datosLibro.stream().map(d->new Libro(d)).collect(Collectors.toList()));
                        repositorioAutor.save(autor);
                    }
                }catch (Exception e){
                    System.out.println("Error al procesar libro" + e.getMessage());
                }
            }else{
                System.out.println("Libro no encontrado");
            }
        }
    }

    private void mostrarLibrosBuscados(){
        librosBuscados = repositorioAutor.librosregistrados();
        if(librosBuscados.isEmpty()){
            System.out.println("La base de datos esta vacia");
        }else{
            librosBuscados.forEach(System.out::println);
        }

    }

    // case 3
    private  void mostrarAutoresRegistrados(){
        autoresBuscados = repositorioAutor.findAll();
        if (autoresBuscados.isEmpty()){
            System.out.println("La base de datos esta vacia");
        }else {
            System.out.println("Autores registrados");
            autoresBuscados.stream()
                    .sorted(Comparator.comparing(Autor::getNombreAutor))
                    .forEach(System.out::println);
        }
    }

    // case 4
    private void mostrarAutoresVivosEnUnAnio(){
        System.out.println("Ingresa el anio de tu interes");
        var fecha = teclado.nextInt();
        autoresBuscados = repositorioAutor.findAll();
        System.out.println("---------------------------------------- Autores vivos en el anio " + String.valueOf(fecha).toUpperCase() + " ---------------------");
        List<Autor> filtroVivos = autoresBuscados.stream()
                .filter(a-> a.getFechaDeDefuncion() >= fecha)
                .filter(a -> a.getFechaDeNacimiento() <=fecha)
                .collect(Collectors.toList());

        if (filtroVivos.isEmpty()){
            System.out.println("No hay ningun autor vivo en el anio"+fecha);
        }else{
            filtroVivos.stream()
                            .forEach(v -> System.out.println("Autor: " + v.getNombreAutor() +
                                    "--- Muerte: " +v.getFechaDeDefuncion()+
                                    "--- Nacimiento: " + v.getFechaDeNacimiento()));
        }

    }

    private void mostraribrosporIdiomas(){
        System.out.println("""
                            Ingrese el idioma de busqueda de libros
                            es - espaniol
                            en - ingles
                            fr - frances
                            pt - portugues
                            """);
        var idioma = teclado.nextLine();
        var categoria = Idiomas.fromString(idioma);
        List<Libro> librosPorIdioma = repositorioAutor.buscarLibroPorIdioma(categoria);
        if (librosPorIdioma.isEmpty()){
            System.out.println("No hay libros en el idioma seleccionado");
        }else {
            System.out.println("Las series de la categoria " + categoria + " son:");
            librosPorIdioma.stream().forEach(System.out::println);
        }
    }

    }


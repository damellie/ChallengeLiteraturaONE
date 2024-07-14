package com.aluracursos.LiterAluraApplication.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity()
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //el id incrementara automaticamente
    private Long id;

    @Column(unique = true) // no se puede buscar la misma serie
    private  String nombreAutor;
    private  Integer fechaDeNacimiento;
     private Integer fechaDeDefuncion;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     private List <Libro> libros;

    public Autor(){}


     public Autor(DatosAutor datosAutor){
         this.nombreAutor=datosAutor.nombreAutor();
         this.fechaDeNacimiento= datosAutor.fechaDeNacimiento();
         this.fechaDeDefuncion = datosAutor.fechaDeDefuncion();
     }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(Integer fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros=libros;
    }

    @Override
    public String toString() {
        return " ------------Autor ------------" +
                "\n NombreAutor='" + nombreAutor +
                "\n FechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                "\n FechaDeDefuncion='" + fechaDeDefuncion + '\'' +
                "\n Libros=" + getLibros().stream().map(l -> l.getTitulo()).collect(Collectors.toList())+
                "\n--------------------------------";
    }
}

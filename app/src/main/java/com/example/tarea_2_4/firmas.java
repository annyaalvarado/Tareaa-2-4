package com.example.tarea_2_4;

public class firmas {
    Integer id;
    String Descripcion;
    String imagen;


    public firmas (Integer id, String descripcion, String imagen) {
        this.id = id;
        Descripcion = descripcion;
        this.imagen = imagen;
    }

    public firmas() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}



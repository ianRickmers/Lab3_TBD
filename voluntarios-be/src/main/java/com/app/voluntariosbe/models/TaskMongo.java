package com.app.voluntariosbe.models;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tarea")
public class TaskMongo {
    @Id
    private Integer id;
    private String nombre;
    private String descrip;
    private Integer cant_vol_requeridos;
    private Integer cant_vol_inscritos;
    private Integer id_emergencia;
    private Date finicio;
    private Date ffin;
    private Integer id_estado;

    public TaskMongo(Integer id, String nombre, String descrip, Integer cant_vol_requeridos, Integer cant_vol_inscritos, Integer id_emergencia, Date finicio, Date ffin, Integer id_estado) {
        this.id = id;
        this.nombre = nombre;
        this.descrip = descrip;
        this.cant_vol_requeridos = cant_vol_requeridos;
        this.cant_vol_inscritos = cant_vol_inscritos;
        this.id_emergencia = id_emergencia;
        this.finicio = finicio;
        this.ffin = ffin;
        this.id_estado = id_estado;
    }

    //Getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public Integer getCant_vol_requeridos() {
        return cant_vol_requeridos;
    }

    public Integer getCant_vol_inscritos() {
        return cant_vol_inscritos;
    }

    public Integer getId_emergencia() {
        return id_emergencia;
    }

    public Date getFinicio() {
        return finicio;
    }

    public Date getFfin() {
        return ffin;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    //Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setCant_vol_requeridos(Integer cant_vol_requeridos) {
        this.cant_vol_requeridos = cant_vol_requeridos;
    }

    public void setCant_vol_inscritos(Integer cant_vol_inscritos) {
        this.cant_vol_inscritos = cant_vol_inscritos;
    }

    public void setId_emergencia(Integer id_emergencia) {
        this.id_emergencia = id_emergencia;
    }

    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }
}

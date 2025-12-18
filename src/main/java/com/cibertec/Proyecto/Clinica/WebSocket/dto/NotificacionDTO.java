package com.cibertec.Proyecto.Clinica.WebSocket.dto;

public class NotificacionDTO {

    private String tipo;
    private String mensaje;
    private String fechaHora;

    public NotificacionDTO(String tipo, String mensaje, String fechaHora) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
    }

    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
    public String getFechaHora() { return fechaHora; }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

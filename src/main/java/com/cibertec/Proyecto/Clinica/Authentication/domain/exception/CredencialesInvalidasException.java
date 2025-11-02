package com.cibertec.Proyecto.Clinica.Authentication.domain.exception;

/**
 * Excepción de dominio para credenciales inválidas.
 */
public class CredencialesInvalidasException extends DominioException {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}

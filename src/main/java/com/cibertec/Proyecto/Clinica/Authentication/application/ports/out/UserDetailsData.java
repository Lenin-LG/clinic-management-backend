package com.cibertec.Proyecto.Clinica.Authentication.application.ports.out;

import java.util.Collection;

public interface UserDetailsData {
    String getUsername();
    String getPassword();
    boolean isActive();
    Collection<String> getRoles();
}
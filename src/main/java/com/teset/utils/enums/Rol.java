package com.teset.utils.enums;

public enum Rol {
    CLIENTE;

    public static Rol getRol(String rol) {
        return Rol.valueOf(rol.toUpperCase());
    }
}
package com.project.bddsimple.domain.livre;

public enum Etat {
    /**
     * Livre Disponible � la location
     */
    DISPONIBLE,
    
    /**
     * Livre lou�, indisponible
     */
    LOUE,
    
    /**
     * Livre perdu, non disponible
     */
    PERDU;
}

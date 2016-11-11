package com.project.bddsimple.domain;

public interface Repository<E> {

    E findBy(Long id);
    
    void store(E e);
}

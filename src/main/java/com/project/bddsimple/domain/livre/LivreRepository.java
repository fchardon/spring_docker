package com.project.bddsimple.domain.livre;

import com.project.bddsimple.domain.Repository;

public interface LivreRepository extends Repository<Livre> {

    Livre findBy(Reference reference);
}

package com.project.bddsimple.domain.tier;

import com.project.bddsimple.domain.Repository;

public interface AdherentRepository extends Repository<Adherent> {
    Adherent findBy(NoAdherent noAdherent);
}

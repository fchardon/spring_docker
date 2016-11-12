package projet.jbehave.domain.adherent;

import com.project.bddsimple.domain.exception.EntityNotFoundException;
import com.project.bddsimple.domain.tier.Adherent;
import com.project.bddsimple.domain.tier.AdherentRepository;
import com.project.bddsimple.domain.tier.NoAdherent;

import java.util.ArrayList;
import java.util.List;

public class AdherentRepositoryMock implements AdherentRepository {
    private static AdherentRepositoryMock INSTANCE = null;

    List<Adherent> adherents;

    public static AdherentRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdherentRepositoryMock();
        }
        return INSTANCE;
    }
    
    public static void disposeInstance() {
        INSTANCE = null;
    }

    private AdherentRepositoryMock() {
        adherents = new ArrayList<Adherent>();
        adherents.add(new Adherent("Chardonnereau", "Florent", new NoAdherent("123")));
    }

    public Adherent findBy(NoAdherent noAdherent) {
        for (Adherent adherent : adherents) {
            if (adherent.getNoAdherent().equals(noAdherent)) {
                return adherent;
            }
        }
        throw new EntityNotFoundException("L'adherent avec le numero {0} n'existe pas.", noAdherent);
    }

    public Adherent findBy(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    public void store(Adherent e) {
        // TODO Auto-generated method stub
        
    }

}

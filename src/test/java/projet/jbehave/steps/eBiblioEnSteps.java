package projet.jbehave.steps;

import com.project.bddsimple.domain.livre.Livre;
import com.project.bddsimple.domain.livre.LivreRepository;
import com.project.bddsimple.domain.tier.Adherent;
import com.project.bddsimple.domain.tier.AdherentRepository;
import com.project.bddsimple.domain.tier.NoAdherent;
import com.project.bddsimple.service.calculateur.Calculateur;
import com.project.bddsimple.service.calculateur.Remise;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import projet.jbehave.domain.adherent.AdherentRepositoryMock;
import projet.jbehave.domain.livre.LivreRepositoryMock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;


@UsingSteps
public class eBiblioEnSteps {

    static LivreRepository livreRepository;
    static AdherentRepository adherentRepository;

    static Calculateur calculateur;

    static Adherent adherent;
    static Livre livre;

    static LocalDate dateOfTheDay;
    private BigDecimal prixCalcule;

    @AfterStory
    public void clean() {
    	LivreRepositoryMock.disposeInstance();
    	AdherentRepositoryMock.disposeInstance();
    	adherent = null;
    	livre = null;
    }
    
    @BeforeStory
    public void init() {
        this.calculateur = new Calculateur(new HashMap<Integer,Integer>());
        this.calculateur.addRule(1, 1);
    	livreRepository = LivreRepositoryMock.getInstance();
        adherentRepository = AdherentRepositoryMock.getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Converter
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @AsParameterConverter
    public LocalDate toLocalDate(String name){
        //final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //return LocalDate.parse(name, formatter);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
        DateTime dateTime = formatter.parseDateTime(name);
        return dateTime.toLocalDate();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GIVEN
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Given("we are the $dateOfTheDay.")
    public void initDateOfTheDay(LocalDate dateDuJour) {
        this.dateOfTheDay = dateDuJour;
    }

    @Given("the member $no exists.")
    public void loadMember(String no) {
            adherent = adherentRepository.findBy(new NoAdherent(no));
    }

    @Given("the customer is member since $beginDate.")
    public void initMemberBegin(LocalDate beginDate) {
        adherent.setDateAdhesion(beginDate);
    }

    @Given("the member has <year> of tenure, the rate applied is <percent>%.")
    public void applySeniorityRule(@Named("year") Integer year, @Named("percent") Integer percent) {
        calculateur.addRule(year, percent);
    }

    private Map<Integer, Integer> convertRules(ExamplesTable table) {
        Map<Integer,Integer> regles = new HashMap<Integer,Integer>();
        for (Map<String,String> row : table.getRows()) {
            Integer age = Integer.valueOf(row.get("seniority"));
            Integer tx = Integer.valueOf(row.get("rate"));
            regles.put(age, tx);
        }
        return regles;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // WHEN
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @When("the member rents a book at $price €.")
    public void rentBook(BigDecimal price) {
        Remise remise = calculateur.calculMontantRemise(this.dateOfTheDay, adherent.getDateAdhesion());
        this.prixCalcule = remise.calculPrix(price);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THEN
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Then("the member pays $price €.")
    public void caculMemberPrice(BigDecimal price) {
        Assert.assertThat("The price expected is not correct", this.prixCalcule, equalTo(price));
    }

}

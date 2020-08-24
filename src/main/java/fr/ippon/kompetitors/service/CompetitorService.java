package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompetitorService {

    private final GlobalGroupsRepository globalGroupsRepository;
    private final CompetitorsRepository competitorsRepository;
    private final LegalRepository legalRepository;
    private final CompetitiveRatesRepository competitiveRatesRepository;
    private final OfficesRepository officesRepository;


    public CompetitorService(GlobalGroupsRepository globalGroupsRepository, CompetitorsRepository competitorsRepository,
                             LegalRepository legalRepository, CompetitiveRatesRepository competitiveRatesRepository,
                             OfficesRepository officesRepository) {
        this.globalGroupsRepository = globalGroupsRepository;
        this.competitorsRepository = competitorsRepository;
        this.legalRepository = legalRepository;
        this.competitiveRatesRepository = competitiveRatesRepository;
        this.officesRepository = officesRepository;
    }

    @Transactional
    public Competitors createCompetitor(GlobalGroups group, Legal legal, Offices office, Competitors competitor) {
        if (group.getId() == null) {
            group = globalGroupsRepository.save(group);
        }
        competitor.setGlobalGroups(group);
        competitor = competitorsRepository.save(competitor);

        legal.setCompetitor(competitor);
        competitor.addLegal(legalRepository.save(legal));

        office.setCompetitors(competitor);
        competitor.addOffices(officesRepository.save(office));

        CompetitiveRates rates = new CompetitiveRates();
        rates.setCompetitor(competitor);
        competitor.addCompetitiveRates(competitiveRatesRepository.save(rates));

        return competitor;
    }

    public void deleteCompetitorById(Long competitorId) {
        Optional<Competitors> competitor = competitorsRepository.findById(competitorId);
        competitor.ifPresent(comp -> {
            List<Competitors> competitorsInGroup = competitorsRepository.findByGlobalGroupsId(comp.getGlobalGroups().getId());
            competitorsRepository.delete(comp);
            if (competitorsInGroup.size() <= 1) {
                globalGroupsRepository.deleteById(comp.getGlobalGroups().getId());
            }
        });
    }
}

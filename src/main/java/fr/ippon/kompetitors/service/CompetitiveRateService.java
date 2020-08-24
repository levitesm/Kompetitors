package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompetitiveRateService {
    private final Logger log = LoggerFactory.getLogger(DashboardFinancePrepareService.class);

    private final CompetitiveRatesRepository competitiveRatesRepository;
    private final CompetitiveRatesRawRepository competitiveRatesRawRepository;
    private final RatesWeightsRepository ratesWeightsRepository;

    private final DashboardFinanceRepository dashboardFinanceRepository;
    private final HrInfoRepository hrInfoRepository;
    private final CompetitorsRepository competitorsRepository;
    private final CompetitorIndustryRepository competitorIndustryRepository;
    private final ListIndustriesRepository listIndustriesRepository;
    private final TechCompetanciesRepository techCompetanciesRepository;
    private final TechPartnersRepository techPartnersRepository;
    private final TechPracticesRepository techPracticesRepository;
    private final TechProjectsRepository techProjectsRepository;
    private final TechServicesRepository techServicesRepository;
    private final TechToolsRepository techToolsRepository;
    private final ListCompetanciesRepository listCompetanciesRepository;
    private final ListTechPartnersRepository listTechPartnersRepository;
    private final ListPracticesRepository listPracticesRepository;
    private final ListProjectTypesRepository listProjectTypesRepository;
    private final ListServicesRepository listServicesRepository;
    private final ListToolsRepository listToolsRepository;

    public CompetitiveRateService(CompetitiveRatesRepository competitiveRatesRepository, CompetitiveRatesRawRepository competitiveRatesRawRepository, RatesWeightsRepository ratesWeightsRepository, DashboardFinanceRepository dashboardFinanceRepository, HrInfoRepository hrInfoRepository, CompetitorsRepository competitorsRepository, CompetitorIndustryRepository competitorIndustryRepository, ListIndustriesRepository listIndustriesRepository, TechCompetanciesRepository techCompetanciesRepository, TechPartnersRepository techPartnersRepository, TechPracticesRepository techPracticesRepository, TechProjectsRepository techProjectsRepository, TechServicesRepository techServicesRepository, TechToolsRepository techToolsRepository, ListCompetanciesRepository listCompetanciesRepository, ListTechPartnersRepository listTechPartnersRepository, ListPracticesRepository listPracticesRepository, ListProjectTypesRepository listProjectTypesRepository, ListServicesRepository listServicesRepository, ListToolsRepository listToolsRepository) {
        this.competitiveRatesRepository = competitiveRatesRepository;
        this.competitiveRatesRawRepository = competitiveRatesRawRepository;
        this.ratesWeightsRepository = ratesWeightsRepository;
        this.dashboardFinanceRepository = dashboardFinanceRepository;
        this.hrInfoRepository = hrInfoRepository;
        this.competitorsRepository = competitorsRepository;
        this.competitorIndustryRepository = competitorIndustryRepository;
        this.listIndustriesRepository = listIndustriesRepository;
        this.techCompetanciesRepository = techCompetanciesRepository;
        this.techPartnersRepository = techPartnersRepository;
        this.techPracticesRepository = techPracticesRepository;
        this.techProjectsRepository = techProjectsRepository;
        this.techServicesRepository = techServicesRepository;
        this.techToolsRepository = techToolsRepository;
        this.listCompetanciesRepository = listCompetanciesRepository;
        this.listTechPartnersRepository = listTechPartnersRepository;
        this.listPracticesRepository = listPracticesRepository;
        this.listProjectTypesRepository = listProjectTypesRepository;
        this.listServicesRepository = listServicesRepository;
        this.listToolsRepository = listToolsRepository;
    }

    @Transactional
    public CompetitiveRates save (Long competitorId, String type, Double value) {
        log.debug("SAVE RAW RATE >>> " + competitorId + " , " + type + " , " + value);
        List<RatesWeights> allWeights = ratesWeightsRepository.findAll();
        RatesWeights weights;
        if(allWeights.size() == 0) {
            weights = new RatesWeights();
            weights.setClientsWeight(1.0);
            weights.setFinanceWeight(1.0);
            weights.setHrWeight(1.0);
            weights.setTechnologiesWeight(1.0);
        } else {
            weights = allWeights.get(0);
        }

        List<CompetitiveRatesRaw> allRaw = competitiveRatesRawRepository.findAllByType(type);
        CompetitiveRatesRaw newRaw = new CompetitiveRatesRaw();
        Double max = Double.MIN_VALUE;
        Double oldVal = null;

        for (CompetitiveRatesRaw r : allRaw
        ) {
            if (r.getCompetitorId().longValue() == competitorId.longValue()) {
                newRaw = r;
                oldVal = r.getValue();
            } else if (r.getValue() > max) {
                max = r.getValue();
            }
        }

        Boolean recalculateFlag = false;
        if (oldVal != null && max < oldVal) {
            recalculateFlag = true;
        }
        if (max < value) {
            max = value;
            recalculateFlag = true;
        }

        newRaw.setCompetitorId(competitorId);
        newRaw.setType(type);
        newRaw.setValue(value);
        competitiveRatesRawRepository.save(newRaw);

            CompetitiveRates cr = competitiveRatesRepository.findOneByCompetitorId(competitorId);
            if(cr == null) {
                cr = new CompetitiveRates();
                Competitors comp = new Competitors();
                comp.setId(competitorId);
                cr.setCompetitor(comp);
                cr.setClientsRate(0.0);
                cr.financeRate(0.0);
                cr.setHrRate(0.0);
                cr.setTechRate(0.0);
                cr.setTotalRate(0.0);
            }
            Double val = value / max * 100;
            val = ( val > 0 ? val : 0.0 ); // Real Rates must be from 0 to 100

            switch (type){
                case "tech":
                    cr.setTechRate(val);
                    break;
                case "fin":
                    cr.setFinanceRate(val);
                    break;
                case "hr":
                    cr.setHrRate(val);
                    break;
                case "client":
                    cr.setClientsRate(val);
                    break;
            }
            cr.setTotalRate(
                cr.getClientsRate() * weights.getClientsWeight() +
                cr.getFinanceRate() * weights.getFinanceWeight() +
                cr.getHrRate() * weights.getHrWeight() +
                cr.getTechRate() * weights.getTechnologiesWeight());

            competitiveRatesRepository.save(cr);

        if(recalculateFlag) { // Recalculate all

            for (CompetitiveRatesRaw r:allRaw
            ) {
                CompetitiveRates c = competitiveRatesRepository.findOneByCompetitorId(r.getCompetitorId());
                Double v = ( r.getValue() > 0 ? r.getValue() : 0.0 ); // Real Rates must be from 0 to 100

                switch (type){
                    case "tech":
                        c.setTechRate(v / max * 100);
                        break;
                    case "fin":
                        c.setFinanceRate(v / max * 100);
                        break;
                    case "hr":
                        c.setHrRate(v / max * 100);
                        break;
                    case "client":
                        c.setClientsRate(v / max * 100);
                        break;
                }
                c.setTotalRate(
                    c.getClientsRate() * weights.getClientsWeight() +
                        c.getFinanceRate() * weights.getFinanceWeight() +
                        c.getHrRate() * weights.getHrWeight() +
                        c.getTechRate() * weights.getTechnologiesWeight());

                competitiveRatesRepository.save(c);
            }

        }

    return cr;
    }

    public Double getMaximum() {
        List<RatesWeights> allWeights = ratesWeightsRepository.findAll();
        RatesWeights weights;
        if(allWeights.size() == 0) {
            weights = new RatesWeights();
            weights.setClientsWeight(1.0);
            weights.setFinanceWeight(1.0);
            weights.setHrWeight(1.0);
            weights.setTechnologiesWeight(1.0);
        } else {
            weights = allWeights.get(0);
        }
        return ( 100 * weights.getTechnologiesWeight() + 100 * weights.getHrWeight() + 100 * weights.getFinanceWeight() + 100 * weights.getClientsWeight() );
    }

    @Transactional
    public void updateAll() {
        int sizeCompetancies = this.listCompetanciesRepository.findAll().size();
        int sizePartners = this.listTechPartnersRepository.findAll().size();
        int sizeTools = this.listToolsRepository.findAll().size();
        int sizePractices = this.listPracticesRepository.findAll().size();
        int sizeProjects = this.listProjectTypesRepository.findAll().size();
        int sizeServices = this.listServicesRepository.findAll().size();
        int sizeIndustries = this.listIndustriesRepository.findAll().size();

        if (sizeCompetancies == 0) {sizeCompetancies = 1;}
        if (sizeIndustries == 0) {sizeIndustries = 1;}
        if (sizePartners == 0) {sizePartners = 1;}
        if (sizePractices == 0) {sizePractices = 1;}
        if (sizeProjects == 0) { sizeProjects = 1;}
        if (sizeServices == 0) {sizeServices = 1;}
        if (sizeTools == 0) {sizeTools = 1;}

        List<Competitors> allCompetitors = this.competitorsRepository.findAll();
        List<HrInfo> allHr = this.hrInfoRepository.findAll();
        List<DashboardFinance> allFinance = this.dashboardFinanceRepository.findAll();
        List<CompetitorIndustry> allIndustries = this.competitorIndustryRepository.findAll();

        List<TechCompetancies> allCompetancies = this.techCompetanciesRepository.findAll();
        List<TechPartners> allPartners = this.techPartnersRepository.findAll();
        List<TechTools> allTools = this.techToolsRepository.findAll();
        List<TechPractices> allPractices = this.techPracticesRepository.findAll();
        List<TechProjects> allProject = this.techProjectsRepository.findAll();
        List<TechServices> allServices = this.techServicesRepository.findAll();

        for (Competitors comp: allCompetitors
             ) {
            Long compId = comp.getId();
            // HR Rate
            if(allHr.stream().filter(hr -> compId.equals(hr.getCompetitor().getId())).count() > 0 && allHr.stream().filter(hr -> compId.equals(hr.getCompetitor().getId())).findFirst().get().getRecruitersNumber() != null) {
                this.save(compId, "hr", allHr.stream().filter(hr -> compId.equals(hr.getCompetitor().getId())).findFirst().get().getRecruitersNumber().doubleValue());
            } else {
                this.save(compId, "hr" , 0.0);
            }
            // Clients Rate
            this.save(compId, "client", ((double)allIndustries.stream().filter(ind -> compId.equals(ind.getCompetitor().getId())).count() / sizeIndustries));
            // Finance Rate
            if( allFinance.stream().filter(fin -> compId.equals(fin.getCompetitorId())).filter( fin -> fin.getGrossSales() != null).count() > 0 && allFinance.stream().filter(fin -> compId.equals(fin.getCompetitorId())).filter( fin -> fin.getGrossSales() != null).sorted((a,b) -> b.getYear() - a.getYear()).findFirst().get().getGrossSales() != null) {
                this.save(compId, "fin", (double)allFinance.stream().filter(fin -> compId.equals(fin.getCompetitorId())).filter( fin -> fin.getGrossSales() != null).sorted((a,b) -> b.getYear() - a.getYear()).findFirst().get().getGrossSales());
            } else {
                this.save(compId, "fin", 0.0);
            }
            // Tech Rate
            this.save(compId, "tech", ((double)allCompetancies.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizeCompetancies) +
                ((double)allPartners.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizePartners) +
                ((double)allTools.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizeTools) +
                ((double)allPractices.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizePractices) +
                ((double)allProject.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizeProjects) +
                ((double)allServices.stream().filter(x -> compId.equals(x.getCompetitor().getId())).count() / sizeServices)
            );
        }
    }

    @Transactional
    public void updateWeights(Double f, Double c, Double t, Double h) {
        List<RatesWeights> allWeights = ratesWeightsRepository.findAll();
        RatesWeights weights;
        if(allWeights.size() == 0) {
            weights = new RatesWeights();
        } else  {
            weights = allWeights.get(0);
        }
        weights.setFinanceWeight(f);
        weights.setClientsWeight(c);
        weights.setTechnologiesWeight(t);
        weights.setHrWeight(h);

        ratesWeightsRepository.save(weights);
        this.updateAll();
    }
}

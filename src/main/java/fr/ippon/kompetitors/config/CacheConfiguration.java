package fr.ippon.kompetitors.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, fr.ippon.kompetitors.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, fr.ippon.kompetitors.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, fr.ippon.kompetitors.domain.User.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Authority.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.User.class.getName() + ".authorities");
            createCache(cm, fr.ippon.kompetitors.domain.Clients.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Clients.class.getName() + ".projects");
            createCache(cm, fr.ippon.kompetitors.domain.ClientsProjects.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.CompetitiveRates.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName() + ".dialogs");
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName() + ".finances");
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName() + ".offices");
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName() + ".people");
            createCache(cm, fr.ippon.kompetitors.domain.Competitors.class.getName() + ".prs");
            createCache(cm, fr.ippon.kompetitors.domain.Dialogs.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Finance.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.GlobalGroups.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.GlobalGroups.class.getName() + ".competitors");
            createCache(cm, fr.ippon.kompetitors.domain.HrInfo.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Legal.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListActivities.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListCities.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListCityCountries.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListClientsProjectTypes.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListCompetancies.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListCountries.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListIndustries.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListOwnerships.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListPricings.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListProjectTypes.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListServices.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListTechPartners.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ListTools.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".clients");
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".techCompetancies");
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".techPartners");
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".techProjects");
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".techServices");
            createCache(cm, fr.ippon.kompetitors.domain.Offices.class.getName() + ".techTools");
            createCache(cm, fr.ippon.kompetitors.domain.People.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.PrInfo.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.SocieteMain.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechCompetancies.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechPartners.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechProjects.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechServices.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechTools.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.UserGroupRights.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.AccessKey.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Representatives.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.AnnualAccount.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.AnnualAccountStatistics.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Capital.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ShareHolders.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Updatehistory.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.RegionList.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.RegionZipList.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.EmployeeRole.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.EmployeePricing.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.CompetitorIndustry.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.DashboardFinance.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.TechInfo.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.Workforce.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.EmployeeType.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.EmployeesTypology.class.getName());
            createCache(cm, fr.ippon.kompetitors.domain.ExternalUrls.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}

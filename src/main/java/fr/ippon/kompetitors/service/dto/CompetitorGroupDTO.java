package fr.ippon.kompetitors.service.dto;

import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.domain.GlobalGroups;
import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.domain.Offices;

public class CompetitorGroupDTO {
    private GlobalGroups globalGroups;
    private Legal legal;
    private Offices offices;
    private Competitors competitor;

    public GlobalGroups getGlobalGroups() {
        return globalGroups;
    }

    public Legal getLegal() {
        return legal;
    }

    public Offices getOffices() {
        return offices;
    }

    public Competitors getCompetitor() {
        return competitor;
    }

    @Override
    public String toString() {
        return "CompetitorGroupDTO{" +
            "globalGroups=" + globalGroups +
            ", legal=" + legal +
            ", offices=" + offices +
            ", competitor=" + competitor +
            '}';
    }
}

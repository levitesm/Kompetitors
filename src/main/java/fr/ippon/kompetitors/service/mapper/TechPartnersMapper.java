package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechPartnersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechPartners} and its DTO {@link TechPartnersDTO}.
 */
@Mapper(componentModel = "spring", uses = {ListTechPartnersMapper.class, CompetitorMapper.class})
public interface TechPartnersMapper extends EntityMapper<TechPartnersDTO, TechPartners> {

    @Mapping(source = "value.id", target = "valueId")
    @Mapping(source = "competitor.id", target = "competitorId")
    TechPartnersDTO toDto(TechPartners techPartners);

    @Mapping(source = "valueId", target = "value")
    @Mapping(source = "competitorId", target = "competitor")
    TechPartners toEntity(TechPartnersDTO techPartnersDTO);

    default TechPartners fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechPartners techPartners = new TechPartners();
        techPartners.setId(id);
        return techPartners;
    }
}

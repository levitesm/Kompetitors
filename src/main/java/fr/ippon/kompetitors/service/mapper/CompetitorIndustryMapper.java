package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.CompetitorIndustryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompetitorIndustry} and its DTO {@link CompetitorIndustryDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitorMapper.class, ListIndustryMapper.class})
public interface CompetitorIndustryMapper extends EntityMapper<CompetitorIndustryDTO, CompetitorIndustry> {

    @Mapping(source = "competitor.id", target = "competitorId")
    @Mapping(source = "industry.id", target = "industryId")
    CompetitorIndustryDTO toDto(CompetitorIndustry competitorIndustry);

    @Mapping(source = "competitorId", target = "competitor")
    @Mapping(source = "industryId", target = "industry")
    CompetitorIndustry toEntity(CompetitorIndustryDTO competitorIndustryDTO);

    default CompetitorIndustry fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompetitorIndustry competitorIndustry = new CompetitorIndustry();
        competitorIndustry.setId(id);
        return competitorIndustry;
    }
}

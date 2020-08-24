package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.AnnualAccountStatisticsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnnualAccountStatistics} and its DTO {@link AnnualAccountStatisticsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnnualAccountStatisticsMapper extends EntityMapper<AnnualAccountStatisticsDTO, AnnualAccountStatistics> {



    default AnnualAccountStatistics fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnnualAccountStatistics annualAccountStatistics = new AnnualAccountStatistics();
        annualAccountStatistics.setId(id);
        return annualAccountStatistics;
    }
}

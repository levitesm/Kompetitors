package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.PrInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrInfo} and its DTO {@link PrInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitorMapper.class})
public interface PrInfoMapper extends EntityMapper<PrInfoDTO, PrInfo> {

    @Mapping(source = "competitors.id", target = "competitorsId")
    PrInfoDTO toDto(PrInfo prInfo);

    @Mapping(source = "competitorsId", target = "competitors")
    PrInfo toEntity(PrInfoDTO prInfoDTO);

    default PrInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrInfo prInfo = new PrInfo();
        prInfo.setId(id);
        return prInfo;
    }
}

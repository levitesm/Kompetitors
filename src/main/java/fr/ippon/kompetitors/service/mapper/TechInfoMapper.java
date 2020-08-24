package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.TechInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechInfo} and its DTO {@link TechInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitorMapper.class})
public interface TechInfoMapper extends EntityMapper<TechInfoDTO, TechInfo> {

    @Mapping(source = "competitor.id", target = "competitorId")
    TechInfoDTO toDto(TechInfo techInfo);

    @Mapping(source = "competitorId", target = "competitor")
    TechInfo toEntity(TechInfoDTO techInfoDTO);

    default TechInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TechInfo techInfo = new TechInfo();
        techInfo.setId(id);
        return techInfo;
    }
}

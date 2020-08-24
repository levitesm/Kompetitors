package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ExternalUrlsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExternalUrls} and its DTO {@link ExternalUrlsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitorMapper.class})
public interface ExternalUrlsMapper extends EntityMapper<ExternalUrlsDTO, ExternalUrls> {

    @Mapping(source = "competitor.id", target = "competitorId")
    ExternalUrlsDTO toDto(ExternalUrls externalUrls);

    @Mapping(source = "competitorId", target = "competitor")
    ExternalUrls toEntity(ExternalUrlsDTO externalUrlsDTO);

    default ExternalUrls fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExternalUrls externalUrls = new ExternalUrls();
        externalUrls.setId(id);
        return externalUrls;
    }
}

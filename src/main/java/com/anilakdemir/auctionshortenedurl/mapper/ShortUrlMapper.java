package com.anilakdemir.auctionshortenedurl.mapper;

import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDetailDto;
import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDto;
import com.anilakdemir.auctionshortenedurl.entity.ShortUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author anilakdemir
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ShortUrlMapper{

    ShortUrlMapper INSTANCE = Mappers.getMapper(ShortUrlMapper.class);

    @Mapping(source = "code", target = "shortened")
    ShortUrlDto convertToShortUrlDto(ShortUrl shortUrl);

    List<ShortUrlDto> convertToShortUrlDtoList(List<ShortUrl> shortUrlList);

    ShortUrlDetailDto convertToShortUrlDetailDto(ShortUrl shortUrl);
}

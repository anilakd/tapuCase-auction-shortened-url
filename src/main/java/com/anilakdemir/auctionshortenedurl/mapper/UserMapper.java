package com.anilakdemir.auctionshortenedurl.mapper;

import com.anilakdemir.auctionshortenedurl.dto.UserDto;
import com.anilakdemir.auctionshortenedurl.dto.UserSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author anilakdemir
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper{

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUsrUser(UserSaveRequestDto userSaveRequestDto);

    @Mapping(source = "id", target = "userId")
    UserDto convertToUsrUserDto(User user);
}

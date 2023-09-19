package dev.sirosh.bonus_service.mapper;

import dev.sirosh.bonus_service.dto.BonusDto;
import dev.sirosh.bonus_service.dto.UserDto;
import dev.sirosh.bonus_service.entity.Bonus;
import dev.sirosh.bonus_service.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-19T15:00:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (N/A)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );
        userDto.setRole( user.getRole() );
        userDto.setBonus( bonusToBonusDto( user.getBonus() ) );

        return userDto;
    }

    @Override
    public List<UserDto> toUserDto(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( user.size() );
        for ( User user1 : user ) {
            list.add( toUserDto( user1 ) );
        }

        return list;
    }

    protected BonusDto bonusToBonusDto(Bonus bonus) {
        if ( bonus == null ) {
            return null;
        }

        BonusDto bonusDto = new BonusDto();

        bonusDto.setCount( bonus.getCount() );

        return bonusDto;
    }
}

package br.com.pix_service.ewallet.domain.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.pix_service.ewallet.infrastructure.utils.Utils.notNull;


@Component
@AllArgsConstructor
public class GenericMapper {

    private final ModelMapper modelMapper;

    public <T, O> T map(O source, Class<T> sourceClass) {
        notNull(List.of(source, sourceClass));
        return modelMapper.map(source, sourceClass);
    }

    public <T, O> List<T> mapAll(List<O> sources, Class<T> sourceClass) {
        notNull(List.of(sources, sourceClass));
        return sources.stream().map(list -> modelMapper.map(list, sourceClass)).toList();
    }
}

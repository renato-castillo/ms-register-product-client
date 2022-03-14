package com.bootcamp.msregisterproductclient.util;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public  class MapperUtil {

    static ModelMapper modelMapper = new ModelMapper();


    public static <D, T> D map(final T entity, Class<D> outClass){
        return modelMapper.map(entity,outClass);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass){
        return entityList.stream()
                .map(entity->map(entity, outClass))
                .collect(Collectors.toList());
    }

    public enum TypeAccount{
        CURRENTACCOUNT,
        SAVINGACCOUNT,
        DEPOSITACCOUNT
    }

    public enum TypeClient{
        Company,
        Person,
    }


}

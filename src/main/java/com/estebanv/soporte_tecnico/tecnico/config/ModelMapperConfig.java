package com.estebanv.soporte_tecnico.tecnico.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.internal.objenesis.Objenesis;
import org.modelmapper.internal.objenesis.ObjenesisStd;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Configuration
public class ModelMapperConfig {

    /*CONFIGURACION BASICA PARA TRABAJAR CON CLASES*/
    @Bean
    ModelMapper modelMapper() {

        return new ModelMapper();
    }

    /*CONFIGURACIÓN PARA TRABAJA CON RECORDS*/
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper.getConfiguration().setProvider(new Provider<Object>() {
//            private final Objenesis objenesis = new ObjenesisStd();
//
//            @Override
//            public Object get(ProvisionRequest<Object> request) {
//                Class<Object> type = request.getRequestedType();
//
//                if (type.isRecord()) {
//                    try {
//                        // Para Records, obtener el constructor y crear una instancia temporal
//                        Constructor<?>[] constructors = type.getDeclaredConstructors();
//                        if (constructors.length > 0) {
//                            Object[] defaultValues = new Object[constructors[0].getParameterCount()];
//                            return constructors[0].newInstance(defaultValues);
//                        }
//                    } catch (Exception e) {
//                        throw new RuntimeException("Error ceando instancia de record", e);
//                    }
//                }
//                return objenesis.newInstance(type);
//            }
//        });
//
//        return modelMapper;
//    }

}

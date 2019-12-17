package com.grappus.api.convertors;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by nimish@grappus.com on 14/12/2019.
 * Description - To send the Serialized name of Enums in API calls and vice versa
 */

@Singleton
public class EnumRetrofitConverterFactory extends Converter.Factory {

    @Inject
    EnumRetrofitConverterFactory() {
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        Converter<?, String> convertor = null;
        //check if the value is enum and call EnumUtils to get name of field
        if (type instanceof Class && ((Class<?>) type).isEnum()) {
            convertor = new Converter<Object, String>() {
                @Override
                public String convert(Object value) throws IOException {
                    return EnumUtils.INSTANCE.getSerialisedNameValue((Enum) value);
                }
            };
        }

        return convertor;
    }
}

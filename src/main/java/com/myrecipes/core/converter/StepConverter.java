package com.myrecipes.core.converter;

import com.myrecipes.model.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StepConverter implements Converter<String, Step>
{
    @Override
    public Step convert(String source)
    {
        Step step = new Step();
        step.setStepName(source);
        return step;
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StepConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}

package com.luxoft.carsapp.data.entity.daoconverter;


import org.greenrobot.greendao.converter.PropertyConverter;

public class CarTypeConverter implements PropertyConverter<CarType, String>{
    @Override
    public CarType convertToEntityProperty(String databaseValue) {
        return CarType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(CarType entityProperty) {
        return entityProperty.name();
    }
}

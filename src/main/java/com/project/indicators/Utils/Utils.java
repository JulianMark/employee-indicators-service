package com.project.indicators.Utils;

import com.project.indicators.model.dto.IndicatorDTO;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class Utils {

    private final static String DATE_FORMAT = "yyyy/mm/dd";

    public void validateRequest (Object request){
        if (request == null){
            throw new IllegalArgumentException("el body del metodo no debe ser nulo");
        }
    }

    public void validateIdNumber (Integer numberId){
        if (numberId == null){
            throw new IllegalArgumentException("el id no debe ser nulo");
        }
        if (numberId <= 0){
            throw new IllegalArgumentException("el id no debe ser igual o menor a 0");
        }
    }

    public void validateIndicatorDTO (IndicatorDTO indicatorDTO){
        if (indicatorDTO == null){
            throw new IllegalArgumentException("el objeto de transferencia es nulo");
        }
    }

    public boolean validatePropIndicatorDTO (IndicatorDTO indicatorDTO){
        if (indicatorDTO.getTotalDonations() == null) return false;
        if (indicatorDTO.getTotalAmountDonations() == null) return false;
        if (indicatorDTO.getCreditType() == null) return false;
        if (indicatorDTO.getTotalProductiveHours() == null) return false;
        if (indicatorDTO.getTotalNonProductiveHours() == null) return false;
        return true;
    }

    public void validateMonth (Integer monthNumber){
        if (monthNumber == null){
            throw new IllegalArgumentException("el mes no puede tener el valor de nulo");
        }
        if (monthNumber <= 0 || monthNumber > 12){
            throw new IllegalArgumentException("el mes debe ser un numero dentro del rango 1 a 12");
        }
    }

    public void validateYear (Integer yearNumber){
        if (yearNumber == null){
            throw new IllegalArgumentException("el anio no puede tener el valor de nulo");
        }
        if (yearNumber < 2015){
            throw new IllegalArgumentException("el anio debe ser mayor al 2015");
        }
        Calendar rightNow = Calendar.getInstance();
        if (yearNumber > rightNow.get(Calendar.YEAR)){
            throw new IllegalArgumentException("el anio no puede ser mayor al actual");
        }
    }

    public void isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("la fecha ingresada no corresponde al formato yyyy/mm/dd :"+date);
        }
    }
}

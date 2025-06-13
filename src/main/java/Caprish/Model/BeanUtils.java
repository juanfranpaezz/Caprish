package Caprish.Model;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.MyObject;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils {

    public static <M extends MyObject> List<String> getPropertyNames(Class<M> myClass) {
        return Arrays.stream(new BeanWrapperImpl(myClass).getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> !"class".equals(name) && !"id".equals(name)) // ⬅️ Ignoramos también el campo "id"
                .collect(Collectors.toList());
    }


    public static <M extends MyObject> void verifyValues(M entity) {
        BeanWrapper wrapper = new BeanWrapperImpl(entity);
        for (String property : BeanUtils.getPropertyNames(entity.getClass())) {
            Object value = wrapper.getPropertyValue(property);
            if ((value instanceof Number intValue) ) {
                if (intValue.doubleValue() < 0) {
                    throw new InvalidEntityException("El campo " + property + " no puede ser negativo en " + entity.getClass().getSimpleName());
                }
            }
        }
    }
}

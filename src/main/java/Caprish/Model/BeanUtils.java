package Caprish.Model;

import Caprish.Model.imp.MyObject;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils{
    public static <M extends MyObject> List<String> getPropertyNames(Class<M> myClass) {
        return Arrays.stream(new BeanWrapperImpl(myClass).getPropertyDescriptors())
                .map(PropertyDescriptor::getName).filter(name -> !"class".equals(name)).collect(Collectors.toList());
    }

}


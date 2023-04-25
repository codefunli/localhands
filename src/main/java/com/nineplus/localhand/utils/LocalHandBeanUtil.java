package com.nineplus.localhand.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocalHandBeanUtil {

        /**
         * Copy properties from source to target
         *
         * @param List<S>  sources
         * @param Class<T> targetType
         * @return List<T>
         */
        public <S, T> List<T> copyListToList(List<S> sources, Class<T> targetType) {
            List<T> targets = new ArrayList<>();

            for (S s : sources) {
                T t = org.springframework.beans.BeanUtils.instantiateClass(targetType);
                org.springframework.beans.BeanUtils.copyProperties(s, t);
                targets.add(t);
            }

            return targets;
        }

    public String objectToJsonString(Object object) {
        String returnVal = "";
        if (object != null) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                returnVal = ow.writeValueAsString(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }
}

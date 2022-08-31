package com.epam.rd.net.reflection;

import com.epam.rd.net.exception.CustomException;
import com.epam.rd.net.socket_controller.ISocketController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class GetMappingHandler {

    public static String processRequest(ISocketController controller, String path, Map<String, String> parametersMap) {
        Method[] methods = controller.getClass().getMethods();

        // find a method with @GetMapping 's value == path, otherwise null
        Method method = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(GetMapping.class))
                .filter(m -> path.equalsIgnoreCase(m.getAnnotation(GetMapping.class).value()))
                .findFirst()
                .orElse(null);

        if (method == null) {
            return null;
        }

        // here we prepare all the parameters, which are annotated with @RequestParam
        Parameter[] parameters = method.getParameters();
        Object[] objects = Arrays.stream(parameters)
                .map(p -> parametersMap.get(p.getAnnotation(RequestParam.class).value()))
                .toArray();

        try {
            return (String) method.invoke(controller, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CustomException("Exception during method invoking.");
        }
    }
}

package com.epam.rd.net.reflection;

import com.epam.rd.net.exception.CustomException;
import com.epam.rd.net.socket_controller.ISocketController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class GetMappingHandler {

    public static String processRequest(ISocketController controller, String path, Object[] args) {
        Method[] methods = controller.getClass().getMethods();

        Method method = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(GetMapping.class))
                .filter(m -> path.startsWith(m.getAnnotation(GetMapping.class).value()))
                .findFirst()
                .orElse(null);

        if (method == null) {
            return null;
        }

        args = Arrays.stream(args)
                .filter(Objects::nonNull)
                .toArray();

        try {
            return (String) method.invoke(controller, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CustomException("Exception during method invoking.");
        }
    }
}

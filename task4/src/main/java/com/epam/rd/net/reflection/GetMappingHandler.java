package com.epam.rd.net.reflection;

import com.epam.rd.net.socket_controller.ISocketController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class GetMappingHandler {
    private ISocketController controllerClass;

    public GetMappingHandler(ISocketController controllerClass) {
        this.controllerClass = controllerClass;
    }

    public String processCommand(String path, Object... args) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = controllerClass.getClass().getMethods();

        Method method = Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(GetMapping.class))
                .filter(m -> path.startsWith(m.getAnnotation(GetMapping.class).value()))
                .findFirst()
                .orElse(null);

        if (method == null) {
            return null;
        }

        return (String) method.invoke(controllerClass, args);
    }
}

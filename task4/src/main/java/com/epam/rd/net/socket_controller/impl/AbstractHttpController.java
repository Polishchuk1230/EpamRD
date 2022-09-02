package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.net.reflection.GetMappingHandler;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.net.socket_controller.util.StatusCodeHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractHttpController implements ISocketController {
    private static Logger logger = LogManager.getLogger(AbstractHttpController.class);
    private static final Pattern PATTERN = Pattern.compile("^GET (?<path>[\\w/-]*)(?:\\?(?<parameters>((?<=[?&])\\w+=\\w+&?)++))? HTTP/1.1$");

    @Override
    public String processRequest(String command) {
        if (command == null) {
            return StatusCodeHeader.CODE_400.toString();
        }

        logger.info(command);

        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            String path = matcher.group("path");
            Map<String, String> parameters = mapParameters(matcher.group("parameters"));

            String answer = GetMappingHandler.processRequest(this, path, parameters);

            if (answer != null) {
                return answer;
            }
        }

        return StatusCodeHeader.CODE_404.toString();
    }

    /**
     * Map a String of the following format: aaa=111&bbb=222 to Map-collection
     * @param parameters
     * @return
     */
    private static Map<String, String> mapParameters(String parameters) {
        return parameters == null ? new HashMap<>() :
                Arrays.stream(parameters.split("&"))
                        .collect(
                                Collectors.toMap(
                                        str -> str.substring(0, str.indexOf('=')),
                                        str -> str.substring(str.indexOf('=') + 1)));
    }
}

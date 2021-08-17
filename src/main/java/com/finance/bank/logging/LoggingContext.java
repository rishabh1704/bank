package com.finance.bank.logging;

import com.finance.bank.dto.LoggingDTO;

import java.util.Map;

public class LoggingContext {

    private static ThreadLocal<Map<String, Object>> dataMap = new ThreadLocal<>();

    private static ThreadLocal<LoggingDTO> LOGGING = new ThreadLocal<>();

    private static final LoggingContext LOGGING_CONTEXT = new LoggingContext();

    public static void setLoggingInfo(LoggingDTO loggingInfo) {
        LOGGING.set(loggingInfo);
    }

    public static LoggingDTO getLoggingInfo() {
        return LOGGING.get();
    }

    public static void append(String type, Object value) {
        LoggingDTO loggingDTO = LoggingContext.getLoggingInfo();

        switch (type) {
            case "response":
                loggingDTO.setResponse((String) value);
                break;
            case "request":
                loggingDTO.setRequest((String) value);
                break;
            case "statusCode":
                loggingDTO.setStatusCode((String) value);
                break;
            case "path":
                loggingDTO.setPath((String) value);
                break;
            case "responseTime":
                loggingDTO.setResponseTime((String) value);
                break;
            case "amount":
                loggingDTO.setAmount((String) value);
                break;
            case "fromAccountId":
                value = "***";
                loggingDTO.setFromAccountId((String) value);
                break;
            case "toAccountId":
                value = "***";
                loggingDTO.setToAccountId((String) value);
                break;

            default:
                break;
        }

        LoggingContext.setLoggingInfo(loggingDTO);
    }


}

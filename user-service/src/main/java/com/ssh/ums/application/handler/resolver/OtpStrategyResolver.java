package com.ssh.ums.application.handler.resolver;

import com.ssh.ums.application.handler.strategy.FunctionalOtpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class OtpStrategyResolver {
    private final Map<String, FunctionalOtpStrategy> strategies = new HashMap<>();

    @Autowired
    public OtpStrategyResolver(List<FunctionalOtpStrategy> strategyList) {
        for (FunctionalOtpStrategy strategy : strategyList) {
            strategies.put(strategy.getPurpose(), strategy);
        }
    }

    public FunctionalOtpStrategy resolve(String purpose) {
        return strategies.get(purpose);
    }
}

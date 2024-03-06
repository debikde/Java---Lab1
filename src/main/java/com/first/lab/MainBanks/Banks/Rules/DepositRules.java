package com.first.lab.MainBanks.Banks.Rules;

import com.first.lab.MainBanks.Amount.Amount;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@NoArgsConstructor(force = true)
public class DepositRules {
    private final List<DepositRule> depositRules;
    public final Amount maximumPercent;
    public DepositRules(List<DepositRule> rules) {
        this.depositRules = new ArrayList<>(rules);
        this.maximumPercent = new Amount(rules.stream()
                .mapToDouble(rule -> rule.getPercent().getValue())
                .max()
                .orElse(0));
        Collections.sort(depositRules, (rule1, rule2) -> Double.compare(rule1.getCriticalValue().getValue(), rule2.getCriticalValue().getValue()));
    }

    public static DepositRulesBuilder builder() {
        return new DepositRulesBuilder();
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class DepositRulesBuilder {
        private final List<DepositRule> rules = new ArrayList<>();

        public DepositRulesBuilder addDepositRule(DepositRule rule) {
            if (!rules.contains(rule)) {
                DepositRule finalRule1 = rule;
                boolean overlapping = rules.stream().anyMatch(r -> r.isOverlapping(finalRule1));
                if (overlapping) {
                    DepositRule finalRule = rule;
                    DepositRule overlappingRule = rules.stream()
                            .filter(r -> r.isOverlapping(finalRule))
                            .findFirst()
                            .orElse(null);
                    if (overlappingRule != null) {
                        rule = rule.getMaximumPercent(overlappingRule);
                        rules.remove(overlappingRule);
                    }
                }
                rules.add(rule);
            }
            return this;
        }

        public DepositRules build() {
            return new DepositRules(rules);
        }
    }

}

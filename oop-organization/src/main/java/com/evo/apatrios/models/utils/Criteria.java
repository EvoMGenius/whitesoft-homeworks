package com.evo.apatrios.models.utils;

import java.util.Map;

public class Criteria {
    private Map<String, String> criteria;

    public Map<String, String> getCriteria() {
        return criteria;
    }

    public void addCriteria(String criteria, String description) {
        this.criteria.put(criteria, description);
    }

    public Criteria() {
    }

    public Criteria(Map<String, String> criteria) {
        this.criteria = criteria;
    }
}

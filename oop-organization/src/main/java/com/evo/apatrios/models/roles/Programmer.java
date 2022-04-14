package com.evo.apatrios.models.roles;

import com.evo.apatrios.models.utils.Criteria;

import java.math.BigDecimal;

public interface Programmer extends Role{
    String develop(Criteria criteria, BigDecimal price, Integer daysForDeveloping);
}

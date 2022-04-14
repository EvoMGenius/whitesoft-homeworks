package com.evo.apatrios.models.roles.entitiesRoles;

import com.evo.apatrios.models.roles.Role;

public interface CommonEmployee extends Role {
    void doOvertimeJob(Integer hours);
}

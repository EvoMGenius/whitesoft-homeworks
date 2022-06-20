package com.evo.apatios.dto;

import com.evo.apatios.dto.transfer.New;
import com.evo.apatios.dto.transfer.Update;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostDto {

    @Null(groups = {New.class, Update.class})
    private UUID id;

    @NotNull(groups = {New.class, Update.class})
    private String name;

}

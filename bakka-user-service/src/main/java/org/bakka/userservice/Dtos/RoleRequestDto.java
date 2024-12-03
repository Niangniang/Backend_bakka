package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Enums.TypeRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleRequestDto {

    private TypeRole typeRole;


}

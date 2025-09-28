package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.example.Entities.UsersEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsereEntityDto extends UsersEntity {

    @NonNull
    private String username;

    @NonNull
    private String lastname;

    private Long PhoneNumber;

    private String email;


}

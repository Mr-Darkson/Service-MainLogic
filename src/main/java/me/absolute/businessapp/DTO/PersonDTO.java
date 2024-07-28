package me.absolute.businessapp.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTO {
    @NotNull
    String username;
    @NotNull
    String telephoneNumber;
}

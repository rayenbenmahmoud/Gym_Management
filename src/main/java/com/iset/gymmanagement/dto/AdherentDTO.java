package com.iset.gymmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdherentDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nom;

    @NotBlank
    @Size(min = 2, max = 50)
    private String prenom;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[0-9]{8,15}$")
    private String telephone;

    @NotNull
    private LocalDate dateNaissance;
}

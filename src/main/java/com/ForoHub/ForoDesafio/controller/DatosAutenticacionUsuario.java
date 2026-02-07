package com.ForoHub.ForoDesafio.controller;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
          @NotBlank String login
        , @NotBlank String clave ) {}
CREATE SCHEMA IF NOT EXISTS bonpland;
USE bonpland;



--
-- Estructura de tabla para la tabla `foto`
--

CREATE TABLE `fotos` (
  `id` bigint NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmobiliaria`
--

CREATE TABLE `inmobiliarias` (
  `id` bigint NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `razon_social` varchar(20) NOT NULL,
  `cuit` int NOT NULL,
  `telefono` int NOT NULL,
  `c_fiscal` varchar(20) NOT NULL,
  `usuario_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmueble`
--

CREATE TABLE `inmuebles` (
  `id` bigint NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `codigo` int NOT NULL,
  `direccion` varchar(500) NOT NULL,
  `codigo_postal` int NOT NULL,
  `cant_ambientes` int NOT NULL,
  `cant_dormi` int NOT NULL,
  `cant_banos` int NOT NULL,
  `cant_cochera` int NOT NULL,
  `metros_cuadrados` double NOT NULL,
  `usuario_id` bigint NOT NULL,
  `tipo_propiedad` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion`
--

CREATE TABLE `publicaciones` (
  `id` bigint NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_publicacion` date NOT NULL,
  `precio` double NOT NULL,
  `inmueble_id` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  `estado` varchar(20) NOT NULL,
  `tipo_moneda` varchar(20) NOT NULL,
  `tipo_operacion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



CREATE TABLE `usuarios` (
  `id` bigint NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `clave` varchar(20) NOT NULL,
  `correo` varchar(60) NOT NULL,
  `telefono` varchar(14) NOT NULL,
  `rol_id` bigint NOT NULL,
  `activo` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Indices de la tabla `foto`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliarias`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_inmobiliaria_razon_social` (`razon_social`),
  ADD UNIQUE KEY `uc_inmobiliaria_cuit` (`cuit`),
  ADD UNIQUE KEY `uc_inmobiliaria_telefono` (`telefono`),
  ADD KEY `fk_inmobiliaria_usuario` (`usuario_id`);

--
-- Indices de la tabla `inmueble`
--
ALTER TABLE `inmuebles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_Inmueble_codigo` (`codigo`);

--
-- Indices de la tabla `publicacion`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_publicacion_inmueble` (`inmueble_id`),
  ADD KEY `fk_publicacion_usuario` (`usuario_id`),
  ADD KEY `fk_publicacion_tipo_moneda` (`tipo_moneda`),
  ADD KEY `fk_publicacion_tipo_operacion` (`tipo_operacion`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `roles`
    ADD PRIMARY KEY (`id`);


--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_usuario_correo` (`correo`),
  ADD KEY `fk_usuario_rol` (`rol_id`);


--
-- AUTO_INCREMENT de la tabla `foto`
--
ALTER TABLE `fotos`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliarias`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inmueble`
--
ALTER TABLE `inmuebles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `publicacion`
--
ALTER TABLE `publicaciones`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- Filtros para la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliarias`
  ADD CONSTRAINT `fk_inmobiliaria_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `inmueble`
--
ALTER TABLE `inmuebles`
  ADD CONSTRAINT `fk_inmueble_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `publicacion`
--
ALTER TABLE `publicaciones`
  ADD CONSTRAINT `fk_publicacion_inmueble` FOREIGN KEY (`inmueble_id`) REFERENCES `inmuebles` (`id`),
  ADD CONSTRAINT `fk_publicacion_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`);
COMMIT;


CREATE SCHEMA IF NOT EXISTS bonpland;
USE bonpland;

CREATE TABLE `condicionfiscal` (
  `id` bigint(20) NOT NULL,
  `cFiscal` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foto`
--

CREATE TABLE `foto` (
  `id` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmobiliaria`
--

CREATE TABLE `inmobiliaria` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `mail` varchar(80) NOT NULL,
  `razonSocial` varchar(20) NOT NULL,
  `cuit` int(11) NOT NULL,
  `telefono` int(11) NOT NULL,
  `cFiscal` bigint(20) NOT NULL,
  `usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmueble`
--

CREATE TABLE `inmueble` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `codigo` int(11) NOT NULL,
  `direccion` varchar(500) NOT NULL,
  `codigoPostal` int(11) NOT NULL,
  `cantAmbientes` int(11) NOT NULL,
  `cantDormi` int(11) NOT NULL,
  `cantBanos` int(11) NOT NULL,
  `cantCochera` int(11) NOT NULL,
  `metrosCuadrados` double NOT NULL,
  `fotos` bigint(20) NOT NULL,
  `usuario` bigint(20) NOT NULL,
  `tipoPropiedad` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion`
--

CREATE TABLE `publicacion` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `fechaPublicacion` date NOT NULL,
  `precio` double NOT NULL,
  `inmueble` bigint(20) NOT NULL,
  `usuario` bigint(20) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `tipoMoneda` varchar(20) NOT NULL,
  `tipoOperacion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipomoneda`
--

CREATE TABLE `tipomoneda` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipooperacion`
--

CREATE TABLE `tipooperacion` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopropiedad`
--

CREATE TABLE `tipopropiedad` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `clave` varchar(20) NOT NULL,
  `mail` varchar(60) NOT NULL,
  `telefono` int(11) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `cFiscal` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `condicionfiscal`
--
ALTER TABLE `condicionfiscal`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_CondicionFiscal_nombre` (`nombre`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `foto`
--
ALTER TABLE `foto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliaria`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_Inmobiliaria_mail` (`mail`),
  ADD UNIQUE KEY `uc_Inmobiliaria_razonSocial` (`razonSocial`),
  ADD UNIQUE KEY `uc_Inmobiliaria_cuit` (`cuit`),
  ADD UNIQUE KEY `uc_Inmobiliaria_telefono` (`telefono`),
  ADD KEY `fk_Inmobiliaria_cFiscal` (`cFiscal`),
  ADD KEY `fk_Inmobiliaria_usuario` (`usuario`);

--
-- Indices de la tabla `inmueble`
--
ALTER TABLE `inmueble`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_Inmueble_codigo` (`codigo`),
  ADD KEY `fk_Inmueble_fotos` (`fotos`),
  ADD KEY `fk_Inmueble_usuario` (`usuario`),
  ADD KEY `fk_Inmueble_tipoPropiedad` (`tipoPropiedad`);

--
-- Indices de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Publicacion_inmueble` (`inmueble`),
  ADD KEY `fk_Publicacion_usuario` (`usuario`),
  ADD KEY `fk_Publicacion_estado` (`estado`),
  ADD KEY `fk_Publicacion_tipoMoneda` (`tipoMoneda`),
  ADD KEY `fk_Publicacion_tipoOperacion` (`tipoOperacion`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tipomoneda`
--
ALTER TABLE `tipomoneda`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tipooperacion`
--
ALTER TABLE `tipooperacion`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tipopropiedad`
--
ALTER TABLE `tipopropiedad`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_Usuario_mail` (`mail`),
  ADD KEY `fk_Usuario_rol` (`rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `condicionfiscal`
--
ALTER TABLE `condicionfiscal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `foto`
--
ALTER TABLE `foto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliaria`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inmueble`
--
ALTER TABLE `inmueble`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inmobiliaria`
--
ALTER TABLE `inmobiliaria`
  ADD CONSTRAINT `fk_Inmobiliaria_cFiscal` FOREIGN KEY (`cFiscal`) REFERENCES `condicionfiscal` (`id`),
  ADD CONSTRAINT `fk_Inmobiliaria_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `inmueble`
--
ALTER TABLE `inmueble`
  ADD CONSTRAINT `fk_Inmueble_fotos` FOREIGN KEY (`fotos`) REFERENCES `foto` (`id`),
  ADD CONSTRAINT `fk_Inmueble_tipoPropiedad` FOREIGN KEY (`tipoPropiedad`) REFERENCES `tipopropiedad` (`nombre`),
  ADD CONSTRAINT `fk_Inmueble_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD CONSTRAINT `fk_Publicacion_estado` FOREIGN KEY (`estado`) REFERENCES `estado` (`nombre`),
  ADD CONSTRAINT `fk_Publicacion_inmueble` FOREIGN KEY (`inmueble`) REFERENCES `inmueble` (`id`),
  ADD CONSTRAINT `fk_Publicacion_tipoMoneda` FOREIGN KEY (`tipoMoneda`) REFERENCES `tipomoneda` (`nombre`),
  ADD CONSTRAINT `fk_Publicacion_tipoOperacion` FOREIGN KEY (`tipoOperacion`) REFERENCES `tipooperacion` (`nombre`),
  ADD CONSTRAINT `fk_Publicacion_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_rol` FOREIGN KEY (`rol`) REFERENCES `rol` (`nombre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


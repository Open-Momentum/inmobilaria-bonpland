import type { NextConfig } from 'next'

const nextConfig: NextConfig = {
  images: {
    remotePatterns: [
      {
        // Esto es para aceptar imágenes desde esa página; eliminar al dejar de utilizar imágenes de placeholder
        //! TODO: Eliminar en producción
        protocol: 'https',
        hostname: 'placehold.co',
      },
    ],
  },
}

export default nextConfig

import Image from 'next/image'

import { PropertyFilter } from '../molecules/property-filter'

export const LandingHeroSection = () => {
  return (
    <section className="relative">
      <div className="absolute inset-0 z-0">
        <Image
          src="https://placehold.co/1200x600.png"
          alt="Hero background"
          fill
          priority
          className="object-cover brightness-50"
        />
      </div>
      <div className="container relative z-10 py-24 md:py-32">
        <div className="mx-auto max-w-4xl text-center text-white">
          <h1 className="text-4xl font-bold tracking-tight sm:text-5xl md:text-6xl">
            Encuentra tu propiedad ideal
          </h1>
          <p className="mt-6 text-lg md:text-xl">
            Miles de propiedades disponibles para alquilar o comprar en las
            mejores ubicaciones
          </p>
        </div>
        <div className="mx-auto mt-10 max-w-3xl rounded-xl bg-white p-4 shadow-lg">
          <PropertyFilter />
        </div>
      </div>
    </section>
  )
}

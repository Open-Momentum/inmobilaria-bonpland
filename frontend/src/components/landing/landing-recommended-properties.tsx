import Link from 'next/link'
import { Section } from '../atoms/section'
import { ArrowRight } from 'lucide-react'
import { PropertyCard, PropertyType } from '../molecules/property-card'
import { Button } from '../ui/button'
import { PLACEHOLDER_RECOMMENDED_PROPERTIES } from '@/lib/consts'

export const LandingRecommendedProperties = () => {
  return (
    <Section id="propiedades" className="py-16">
      <div className="container">
        <div className="mb-10 flex items-center justify-between">
          <h2 className="text-3xl font-bold">Propiedades Recomendadas</h2>
          <Link
            href="#"
            className="flex items-center text-primary hover:underline"
          >
            Ver todas <ArrowRight className="ml-2 h-4 w-4" />
          </Link>
        </div>
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {PLACEHOLDER_RECOMMENDED_PROPERTIES.map((property, index) => (
            <PropertyCard
              key={index}
              id={property.id}
              title={property.title}
              location={property.location}
              price={property.price}
              isForRent={property.isForRent}
              status={property.status}
              date={property.date}
              agency={property.agency}
              imageUrl={property.imageUrl}
              propertyType={property.propertyType as PropertyType}
            />
          ))}
        </div>
        <div className="mt-10 text-center">
          <Button size="lg">Ver Más Propiedades</Button>
        </div>
      </div>
    </Section>
  )
}

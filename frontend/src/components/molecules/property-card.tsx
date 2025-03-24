'use client'

import {
  Building,
  Calendar,
  Car,
  Heart,
  Home,
  Info,
  MapPin,
  Warehouse,
} from 'lucide-react'
import Image from 'next/image'
import Link from 'next/link'
import { useEffect, useState } from 'react'

import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardFooter } from '@/components/ui/card'
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from '@/components/ui/tooltip'

interface PropertyCardProps {
  id: string
  title: string
  location: string
  price: number
  isForRent: boolean
  status: string
  date: string
  agency: string
  imageUrl: string
  propertyType: PropertyType
}

export type PropertyType = 'apartment' | 'house' | 'garage' | 'commercial'

export function PropertyCard({
  id,
  title,
  location,
  price,
  isForRent,
  status,
  date,
  agency,
  imageUrl,
  propertyType,
}: PropertyCardProps) {
  const [isFavorite, setIsFavorite] = useState(false)

  // Cargar favoritos desde localStorage al montar el componente
  useEffect(() => {
    const favorites = JSON.parse(localStorage.getItem('favorites') || '[]')
    setIsFavorite(favorites.includes(id))
  }, [id])

  // Manejar el clic en el botón de favoritos
  const handleFavoriteClick = () => {
    const favorites = JSON.parse(localStorage.getItem('favorites') || '[]')

    if (isFavorite) {
      // Eliminar de favoritos
      const updatedFavorites = favorites.filter((favId: string) => favId !== id)
      localStorage.setItem('favorites', JSON.stringify(updatedFavorites))
    } else {
      // Agregar a favoritos
      favorites.push(id)
      localStorage.setItem('favorites', JSON.stringify(favorites))
    }

    setIsFavorite(!isFavorite)
  }

  // Formatear fecha
  const formattedDate = new Date(date).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  })

  // Obtener icono según tipo de propiedad
  const getPropertyIcon = () => {
    switch (propertyType) {
      case 'apartment': {
        return <Building className="mr-1 h-4 w-4" />
      }
      case 'house': {
        return <Home className="mr-1 h-4 w-4" />
      }
      case 'garage': {
        return <Car className="mr-1 h-4 w-4" />
      }
      case 'commercial': {
        return <Warehouse className="mr-1 h-4 w-4" />
      }
      default: {
        return <Building className="mr-1 h-4 w-4" />
      }
    }
  }

  // Obtener nombre del tipo de propiedad
  const getPropertyTypeName = () => {
    switch (propertyType) {
      case 'apartment': {
        return 'Departamento'
      }
      case 'house': {
        return 'Quinta/Casa'
      }
      case 'garage': {
        return 'Garaje'
      }
      case 'commercial': {
        return 'Comercial'
      }
      default: {
        return 'Propiedad'
      }
    }
  }

  return (
    <Card className="overflow-hidden transition-all hover:shadow-lg">
      <div className="relative h-48 w-full">
        <Image
          src={imageUrl || '/placeholder.svg'}
          alt={title}
          fill
          className="object-cover"
        />
        <div className="absolute left-2 top-2 flex gap-2">
          <Badge variant={isForRent ? 'default' : 'secondary'}>
            {isForRent ? 'Alquiler' : 'Venta'}
          </Badge>
          <Badge variant="outline" className="bg-white">
            {getPropertyTypeName()}
          </Badge>
        </div>
        <Button
          variant="ghost"
          size="icon"
          className="absolute right-2 top-2 h-8 w-8 rounded-full bg-white/80 text-primary hover:bg-white hover:text-primary"
          onClick={handleFavoriteClick}
        >
          <Heart className={`h-4 w-4 ${isFavorite ? 'fill-primary' : ''}`} />
        </Button>
      </div>
      <CardContent className="p-4">
        <h3 className="line-clamp-1 text-xl font-bold">{title}</h3>
        <div className="mt-1 flex items-center text-sm text-muted-foreground">
          <MapPin className="mr-1 h-4 w-4" />
          {location}
        </div>
        <p className="mt-4 text-xl font-bold text-primary">
          ${price.toLocaleString()}
          {isForRent ? '/mes' : ''}
        </p>
        <div className="mt-4 flex flex-col gap-2">
          <div className="flex items-center justify-between">
            <div className="flex items-center text-sm">
              <Info className="mr-1 h-4 w-4" />
              Estado: <span className="ml-1 font-medium">{status}</span>
            </div>
            <div className="flex items-center text-sm">
              <Calendar className="mr-1 h-4 w-4" />
              {formattedDate}
            </div>
          </div>
          <div className="flex items-center text-sm">
            {getPropertyIcon()}
            Ofrecido por: <span className="ml-1 font-medium">{agency}</span>
          </div>
        </div>
      </CardContent>
      <CardFooter className="p-4 pt-0">
        <div className="flex w-full gap-2">
          <Link href="#" className="flex-1">
            <Button variant="outline" className="w-full">
              Ver Detalles
            </Button>
          </Link>
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger asChild>
                <Button
                  variant="ghost"
                  size="icon"
                  className="h-10 w-10"
                  onClick={handleFavoriteClick}
                >
                  <Heart
                    className={`h-5 w-5 ${isFavorite ? 'fill-primary' : ''}`}
                  />
                </Button>
              </TooltipTrigger>
              <TooltipContent>
                <p>
                  {isFavorite ? 'Quitar de favoritos' : 'Agregar a favoritos'}
                </p>
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
        </div>
      </CardFooter>
    </Card>
  )
}

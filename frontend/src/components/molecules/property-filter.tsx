'use client'

import { Building, Car, Home, Search, Warehouse } from 'lucide-react'
import { useState } from 'react'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Slider } from '@/components/ui/slider'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'

export function PropertyFilter() {
  const [priceRange, setPriceRange] = useState([500, 2000])
  const [propertyType, setPropertyType] = useState('apartment')

  return (
    <Tabs defaultValue="rent" className="w-full">
      <TabsList className="grid w-full grid-cols-2">
        <TabsTrigger value="rent">Alquilar</TabsTrigger>
        <TabsTrigger value="buy">Comprar</TabsTrigger>
      </TabsList>
      <TabsContent value="rent" className="mt-4 space-y-4">
        <div className="grid gap-4 md:grid-cols-4">
          <div className="space-y-2">
            <Label htmlFor="location">Ubicación</Label>
            <Input id="location" placeholder="Ciudad, barrio o dirección" />
          </div>

          <div className="space-y-2">
            <Label>Tipo de Propiedad</Label>
            <Select
              defaultValue="apartment"
              value={propertyType}
              onValueChange={setPropertyType}
            >
              <SelectTrigger>
                <SelectValue placeholder="Tipo de propiedad" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="apartment">
                  <div className="flex items-center">
                    <Building className="mr-2 h-4 w-4" />
                    Departamento
                  </div>
                </SelectItem>
                <SelectItem value="house">
                  <div className="flex items-center">
                    <Home className="mr-2 h-4 w-4" />
                    Quinta/Casa
                  </div>
                </SelectItem>
                <SelectItem value="garage">
                  <div className="flex items-center">
                    <Car className="mr-2 h-4 w-4" />
                    Garaje
                  </div>
                </SelectItem>
                <SelectItem value="commercial">
                  <div className="flex items-center">
                    <Warehouse className="mr-2 h-4 w-4" />
                    Comercial
                  </div>
                </SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label>Habitaciones</Label>
            <Select defaultValue="any">
              <SelectTrigger>
                <SelectValue placeholder="Cualquiera" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="any">Cualquiera</SelectItem>
                <SelectItem value="1">1+</SelectItem>
                <SelectItem value="2">2+</SelectItem>
                <SelectItem value="3">3+</SelectItem>
                <SelectItem value="4">4+</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label>Baños</Label>
            <Select defaultValue="any">
              <SelectTrigger>
                <SelectValue placeholder="Cualquiera" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="any">Cualquiera</SelectItem>
                <SelectItem value="1">1+</SelectItem>
                <SelectItem value="2">2+</SelectItem>
                <SelectItem value="3">3+</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <Label>Precio ($/mes)</Label>
            <span className="text-sm">
              ${priceRange[0]} - ${priceRange[1]}
            </span>
          </div>
          <Slider
            defaultValue={[500, 2000]}
            min={0}
            max={5000}
            step={100}
            value={priceRange}
            onValueChange={setPriceRange}
          />
        </div>

        <Button className="w-full">
          <Search className="mr-2 h-4 w-4" />
          Buscar Propiedades
        </Button>
      </TabsContent>

      <TabsContent value="buy" className="mt-4 space-y-4">
        <div className="grid gap-4 md:grid-cols-4">
          <div className="space-y-2">
            <Label htmlFor="location-buy">Ubicación</Label>
            <Input id="location-buy" placeholder="Ciudad, barrio o dirección" />
          </div>

          <div className="space-y-2">
            <Label>Tipo de Propiedad</Label>
            <Select defaultValue="apartment">
              <SelectTrigger>
                <SelectValue placeholder="Tipo de propiedad" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="apartment">
                  <div className="flex items-center">
                    <Building className="mr-2 h-4 w-4" />
                    Departamento
                  </div>
                </SelectItem>
                <SelectItem value="house">
                  <div className="flex items-center">
                    <Home className="mr-2 h-4 w-4" />
                    Quinta/Casa
                  </div>
                </SelectItem>
                <SelectItem value="garage">
                  <div className="flex items-center">
                    <Car className="mr-2 h-4 w-4" />
                    Garaje
                  </div>
                </SelectItem>
                <SelectItem value="commercial">
                  <div className="flex items-center">
                    <Warehouse className="mr-2 h-4 w-4" />
                    Comercial
                  </div>
                </SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label>Habitaciones</Label>
            <Select defaultValue="any">
              <SelectTrigger>
                <SelectValue placeholder="Cualquiera" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="any">Cualquiera</SelectItem>
                <SelectItem value="1">1+</SelectItem>
                <SelectItem value="2">2+</SelectItem>
                <SelectItem value="3">3+</SelectItem>
                <SelectItem value="4">4+</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label>Baños</Label>
            <Select defaultValue="any">
              <SelectTrigger>
                <SelectValue placeholder="Cualquiera" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="any">Cualquiera</SelectItem>
                <SelectItem value="1">1+</SelectItem>
                <SelectItem value="2">2+</SelectItem>
                <SelectItem value="3">3+</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <Label>Precio ($)</Label>
            <span className="text-sm">
              ${priceRange[0] * 1000} - ${priceRange[1] * 1000}
            </span>
          </div>
          <Slider
            defaultValue={[50, 300]}
            min={0}
            max={1000}
            step={10}
            value={priceRange}
            onValueChange={setPriceRange}
          />
        </div>

        <Button className="w-full">
          <Search className="mr-2 h-4 w-4" />
          Buscar Propiedades
        </Button>
      </TabsContent>
    </Tabs>
  )
}

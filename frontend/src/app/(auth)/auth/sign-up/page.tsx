'use client'

import { Building, Eye, EyeOff } from 'lucide-react'
import Link from 'next/link'
import { useState } from 'react'

import { Button } from '@/components/ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group'
import { Separator } from '@/components/ui/separator'

export default function SignUpPage() {
  const [showPassword, setShowPassword] = useState(false)
  const [userType, setUserType] = useState('buyer')

  return (
    <div className="flex min-h-screen flex-col">
      <div className="container flex flex-1 items-center justify-center py-12">
        <div className="mx-auto w-full max-w-md space-y-6">
          <div className="flex flex-col items-center space-y-2 text-center">
            <Link href="/" className="flex items-center gap-2">
              <Building className="h-6 w-6" />
              <span className="text-xl font-bold">InmobiliariaYA</span>
            </Link>
            <h1 className="text-2xl font-bold">Crea tu cuenta</h1>
            <p className="text-sm text-muted-foreground">
              Ingresa tus datos para registrarte
            </p>
          </div>

          <div className="space-y-4">
            <div className="space-y-2">
              <Label>Tipo de Usuario</Label>
              <RadioGroup
                defaultValue="buyer"
                value={userType}
                onValueChange={setUserType}
                className="flex"
              >
                <div className="flex items-center space-x-2">
                  <RadioGroupItem value="buyer" id="buyer" />
                  <Label htmlFor="buyer">Comprador/Inquilino</Label>
                </div>
                <div className="ml-4 flex items-center space-x-2">
                  <RadioGroupItem value="seller" id="seller" />
                  <Label htmlFor="seller">Vendedor/Propietario</Label>
                </div>
                <div className="ml-4 flex items-center space-x-2">
                  <RadioGroupItem value="agent" id="agent" />
                  <Label htmlFor="agent">Agente Inmobiliario</Label>
                </div>
              </RadioGroup>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="first-name">Nombre</Label>
                <Input id="first-name" placeholder="Juan" />
              </div>
              <div className="space-y-2">
                <Label htmlFor="last-name">Apellido</Label>
                <Input id="last-name" placeholder="Pérez" />
              </div>
            </div>

            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" placeholder="tu@email.com" />
            </div>

            <div className="space-y-2">
              <Label htmlFor="phone">Teléfono</Label>
              <Input id="phone" type="tel" placeholder="+1234567890" />
            </div>

            {userType === 'agent' && (
              <div className="space-y-2">
                <Label htmlFor="license">Número de Licencia Inmobiliaria</Label>
                <Input id="license" placeholder="Ej: LIC-12345" />
              </div>
            )}

            <div className="space-y-2">
              <Label htmlFor="password">Contraseña</Label>
              <div className="relative">
                <Input
                  id="password"
                  type={showPassword ? 'text' : 'password'}
                  placeholder="••••••••"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground"
                >
                  {showPassword ? (
                    <EyeOff className="h-4 w-4" />
                  ) : (
                    <Eye className="h-4 w-4" />
                  )}
                </button>
              </div>
              <p className="text-xs text-muted-foreground">
                Mínimo 8 caracteres, incluyendo una letra mayúscula y un número
              </p>
            </div>

            <div className="flex items-center space-x-2">
              <Checkbox id="terms" />
              <Label htmlFor="terms" className="text-xs">
                Acepto los{' '}
                <Link href="#" className="text-primary hover:underline">
                  Términos de Servicio
                </Link>{' '}
                y la{' '}
                <Link href="#" className="text-primary hover:underline">
                  Política de Privacidad
                </Link>
              </Label>
            </div>

            <Button className="w-full">Registrarse</Button>
          </div>

          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <Separator className="w-full" />
            </div>
            <div className="relative flex justify-center text-xs uppercase">
              <span className="bg-background px-2 text-muted-foreground">
                O continúa con
              </span>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <Button variant="outline">Google</Button>
            <Button variant="outline">Facebook</Button>
          </div>

          <div className="text-center text-sm">
            ¿Ya tienes una cuenta?{' '}
            <Link href="/auth/sign-in" className="text-primary hover:underline">
              Iniciar Sesión
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

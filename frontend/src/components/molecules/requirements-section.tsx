import { CheckCircle2 } from 'lucide-react'

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'

export function RequirementsSection() {
  return (
    <Tabs defaultValue="rent" className="w-full">
      <TabsList className="mx-auto grid w-full max-w-md grid-cols-2">
        <TabsTrigger value="rent">Requisitos para Alquilar</TabsTrigger>
        <TabsTrigger value="sell">Requisitos para Vender</TabsTrigger>
      </TabsList>
      <TabsContent value="rent" className="mt-6">
        <div className="grid gap-6 md:grid-cols-2">
          <Card>
            <CardHeader>
              <CardTitle>Documentación Personal</CardTitle>
              <CardDescription>
                Documentos necesarios del inquilino
              </CardDescription>
            </CardHeader>
            <CardContent>
              <ul className="space-y-2">
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Documento de identidad (DNI, pasaporte o cédula)</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Comprobantes de ingresos (últimos 3 recibos de sueldo o
                    certificación de ingresos)
                  </span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Declaración jurada de bienes (si corresponde)</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Referencias laborales y personales</span>
                </li>
              </ul>
            </CardContent>
          </Card>
          <Card>
            <CardHeader>
              <CardTitle>Garantías y Avales</CardTitle>
              <CardDescription>
                Respaldos necesarios para el contrato
              </CardDescription>
            </CardHeader>
            <CardContent>
              <ul className="space-y-2">
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Garantía propietaria (título de propiedad)</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Aval bancario o seguro de caución</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Fiador con recibo de sueldo (ingresos 3 veces superior al
                    alquiler)
                  </span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Depósito de garantía (equivalente a 1-2 meses de alquiler)
                  </span>
                </li>
              </ul>
            </CardContent>
          </Card>
        </div>
      </TabsContent>
      <TabsContent value="sell" className="mt-6">
        <div className="grid gap-6 md:grid-cols-2">
          <Card>
            <CardHeader>
              <CardTitle>Documentación de la Propiedad</CardTitle>
              <CardDescription>
                Documentos necesarios del inmueble
              </CardDescription>
            </CardHeader>
            <CardContent>
              <ul className="space-y-2">
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Título de propiedad</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Certificado de dominio vigente</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Planos aprobados de la propiedad</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Comprobantes de pago de impuestos y servicios al día
                  </span>
                </li>
              </ul>
            </CardContent>
          </Card>
          <Card>
            <CardHeader>
              <CardTitle>Trámites y Certificaciones</CardTitle>
              <CardDescription>
                Procedimientos legales necesarios
              </CardDescription>
            </CardHeader>
            <CardContent>
              <ul className="space-y-2">
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Certificado de libre deuda de servicios e impuestos
                  </span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Certificado catastral</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>Tasación profesional de la propiedad</span>
                </li>
                <li className="flex items-start">
                  <CheckCircle2 className="mr-2 mt-0.5 h-5 w-5 flex-shrink-0 text-primary" />
                  <span>
                    Escritura de compraventa (a realizar con escribano)
                  </span>
                </li>
              </ul>
            </CardContent>
          </Card>
        </div>
      </TabsContent>
    </Tabs>
  )
}

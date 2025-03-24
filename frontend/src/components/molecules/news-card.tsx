import { ArrowRight, Calendar } from 'lucide-react'
import Image from 'next/image'
import Link from 'next/link'

import { Button } from '@/components/ui/button'
import { Card, CardContent, CardFooter } from '@/components/ui/card'

interface NewsCardProps {
  title: string
  description: string
  imageUrl: string
  date: string
}

export function NewsCard({
  title,
  description,
  imageUrl,
  date,
}: NewsCardProps) {
  // Formatear fecha
  const formattedDate = new Date(date).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  })

  return (
    <Card className="overflow-hidden transition-all hover:shadow-lg">
      <div className="relative h-48 w-full">
        <Image
          src={imageUrl || '/placeholder.svg'}
          alt={title}
          fill
          className="object-cover"
        />
      </div>
      <CardContent className="p-4">
        <div className="mb-2 flex items-center text-sm text-muted-foreground">
          <Calendar className="mr-1 h-4 w-4" />
          {formattedDate}
        </div>
        <h3 className="text-xl font-bold">{title}</h3>
        <p className="mt-2 text-muted-foreground">{description}</p>
      </CardContent>
      <CardFooter className="p-4 pt-0">
        <Link href="#" className="w-full">
          <Button variant="ghost" className="w-full justify-between">
            Leer más <ArrowRight className="h-4 w-4" />
          </Button>
        </Link>
      </CardFooter>
    </Card>
  )
}

import Image from 'next/image'
import Link from 'next/link'

import { SECTIONS_LANDING_HEADER } from '@/lib/consts'

import { ThemeToggle } from '../molecules/theme-toggle'
import { Button } from '../ui/button'

export const LandingHeader = () => {
  return (
    <header className="sticky top-0 z-40 border-b bg-background px-2 md:px-8">
      <div className="container flex h-16 items-center justify-between py-4">
        <Link href="/" title="Acceder a la página de aterrizaje">
          <Image
            src="/images/logo-bonpland.svg"
            alt="Logo"
            width={42}
            height={42}
            priority
          />
        </Link>
        <nav className="hidden items-center gap-6 md:flex">
          {SECTIONS_LANDING_HEADER.map(section => (
            <Link
              key={section.id}
              href={section.href}
              className="text-sm font-medium"
            >
              {section.name}
            </Link>
          ))}
        </nav>
        <div className="flex items-center gap-4">
          <ThemeToggle />
          <Link href="/auth/sign-in">
            <Button variant="outline" size="sm">
              Iniciar Sesión
            </Button>
          </Link>
          <Link href="/auth/sign-up">
            <Button size="sm">Registrarse</Button>
          </Link>
        </div>
      </div>
    </header>
  )
}

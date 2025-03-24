import Image from 'next/image'
import Link from 'next/link'

import { LINKS_LANDING_FOOTER } from '@/lib/consts'

export const LandingFooter = () => {
  return (
    <footer className="border-t bg-background">
      <div className="container px-2 py-8 md:px-8 md:py-12">
        <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-4">
          <div>
            <Link
              href="/"
              title="Acceder a la página de aterrizaje"
              className="flex items-center gap-2"
            >
              <Image
                src="/images/logo-bonpland.svg"
                alt="Logo"
                width={42}
                height={42}
                priority
              />
              <span className="text-lg font-bold">Inmobiliaria Bonpland</span>
            </Link>

            <p className="mt-2 text-sm text-muted-foreground">
              Tu plataforma confiable para encontrar la propiedad perfecta para
              alquilar o comprar.
            </p>
          </div>
          <div>
            <h3 className="mb-3 text-sm font-medium">Enlaces Rápidos</h3>
            <ul className="space-y-2 text-sm">
              {LINKS_LANDING_FOOTER.QUICK_LINKS.map(link => (
                <li key={link.id}>
                  <Link
                    href={link.href}
                    className="text-muted-foreground hover:text-foreground"
                  >
                    {link.name}
                  </Link>
                </li>
              ))}
            </ul>
          </div>
          <div>
            <h3 className="mb-3 text-sm font-medium">Legal</h3>
            <ul className="space-y-2 text-sm">
              {LINKS_LANDING_FOOTER.LEGAL.map(link => (
                <li key={link.id}>
                  <Link
                    href={link.href}
                    className="text-muted-foreground hover:text-foreground"
                  >
                    {link.name}
                  </Link>
                </li>
              ))}
            </ul>
          </div>
          <div>
            <h3 className="mb-3 text-sm font-medium">Contacto</h3>
            <address className="not-italic">
              <p className="text-sm text-muted-foreground">
                Email: info@inmobiliaria.com
              </p>
              <p className="text-sm text-muted-foreground">
                Teléfono: +1 234 567 890
              </p>
              <p className="text-sm text-muted-foreground">
                Dirección: Calle Principal 123, Ciudad
              </p>
            </address>
          </div>
        </div>
        <div className="mt-8 border-t pt-8 text-center text-sm text-muted-foreground">
          <p>
            &copy; {new Date().getFullYear()} Inmobiliaria Bonpland. Todos los
            derechos reservados.
          </p>
        </div>
      </div>
    </footer>
  )
}

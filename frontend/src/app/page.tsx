import { LandingFooter } from '@/components/landing/landing-footer'
import { LandingHeader } from '@/components/landing/landing-header'
import { LandingHeroSection } from '@/components/landing/landing-hero-section'
import { LandingNews } from '@/components/landing/landing-news'
import { RequirementsSection } from '@/components/molecules/requirements-section'
import { LandingRecommendedProperties } from '@/components/landing/landing-recommended-properties'

export default function HomePage() {
  return (
    <div className="mx-auto my-0 flex min-h-screen max-w-screen-2xl flex-col">
      <LandingHeader />
      <main className="flex-1">
        <LandingHeroSection />

        {/* Sección de Requisitos */}
        <section id="requisitos" className="bg-muted py-16">
          <div className="container">
            <h2 className="mb-10 text-center text-3xl font-bold">Requisitos</h2>
            <RequirementsSection />
          </div>
        </section>

        <LandingRecommendedProperties />

        <LandingNews />
      </main>
      <LandingFooter />
    </div>
  )
}

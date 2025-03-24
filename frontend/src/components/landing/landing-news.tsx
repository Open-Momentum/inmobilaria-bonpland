import { PLACEHOLDER_NEWS } from '@/lib/consts'

import { Section } from '../atoms/section'
import { NewsCard } from '../molecules/news-card'

export const LandingNews = () => {
  return (
    <Section id="news" className="bg-muted py-16">
      <div className="container">
        <h2 className="mb-10 text-center text-3xl font-bold">
          ¿Cómo usar nuestra aplicación?
        </h2>
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {PLACEHOLDER_NEWS.map((news, index) => (
            <NewsCard
              key={index}
              title={news.title}
              description={news.description}
              imageUrl={news.imageUrl}
              date={news.date}
            />
          ))}
        </div>
      </div>
    </Section>
  )
}

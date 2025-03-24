import { type JSX } from 'react'

type SectionProps = {
  children: React.ReactNode
  className?: string
  component?: keyof JSX.IntrinsicElements | React.ComponentType<any>
} & React.HTMLAttributes<HTMLElement>

export const Section = ({
  children,
  className,
  component: Component = 'section',
  ...props
}: SectionProps) => {
  return (
    <Component className={`px-2 md:px-8 ${className}`} {...props}>
      {children}
    </Component>
  )
}

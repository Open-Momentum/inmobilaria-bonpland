interface LayoutProps {
  children: React.ReactNode
}

export default function Layout({ children }: LayoutProps) {
  return (
    <>
      <header>Header</header>
      {children}
      <footer>footer</footer>
    </>
  )
}

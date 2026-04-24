import { useEffect } from "react";
import { NavLink, Route, Routes, useLocation } from "react-router-dom";
import Home from "./pages/Home.jsx";
import Projects from "./pages/Projects.jsx";

function App() {
  const location = useLocation();

  useEffect(() => {
    const revealItems = document.querySelectorAll(".reveal");

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("is-visible");
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.16 },
    );

    revealItems.forEach((item) => observer.observe(item));

    return () => observer.disconnect();
  }, [location.pathname]);

  return (
    <div className="app-shell">
      <div className="ambient-bg" aria-hidden="true">
        <span />
        <span />
        <span />
        <span />
      </div>

      <header className="site-header">
        <NavLink className="brand" to="/" aria-label="Go to home page">
          <span className="brand-mark" aria-hidden="true">
            <span />
          </span>
          <span>
            <strong>Sai Sushan</strong>
            <small>CS student / builder / researcher</small>
          </span>
        </NavLink>

        <nav className="site-nav" aria-label="Primary navigation">
          <NavLink to="/" end>
            Home
          </NavLink>
        </nav>
      </header>

      <main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/projects" element={<Projects />} />
          <Route path="*" element={<Home />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;

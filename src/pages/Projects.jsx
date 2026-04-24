import { projects } from "../data/profile.js";

function Projects() {
  return (
    <section className="projects-page">
      <div className="projects-hero glass-panel reveal">
        <span className="eyebrow">Selected Work</span>
        <h1>Projects that connect software engineering, data, and impact.</h1>
        <p>
          These repositories are placeholder links for now, ready to be replaced
          when the final GitHub project repos are available.
        </p>
      </div>

      <div className="project-list">
        {projects.map((project, index) => (
          <article className="project-card reveal" key={project.title}>
            <div className="project-number">
              <span>0{index + 1}</span>
            </div>
            <div className="project-body">
              <div className="project-heading">
                <div>
                  <p className="muted-label">{project.period}</p>
                  <h2>{project.title}</h2>
                </div>
                <a href={project.repo} target="_blank" rel="noreferrer">
                  GitHub Repo
                </a>
              </div>
              <p>{project.description}</p>
              <p className="project-impact">{project.impact}</p>
              <div className="chip-row">
                {project.stack.map((tech) => (
                  <span className="chip" key={tech}>
                    {tech}
                  </span>
                ))}
              </div>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
}

export default Projects;

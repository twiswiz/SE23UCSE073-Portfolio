import { profile, projects } from "../data/profile.js";

function Home() {
  const skillGroups = [
    { label: "Languages", items: profile.skills.languages },
    { label: "Tools", items: profile.skills.tools },
  ];

  return (
    <div className="scroll-story home-page">
      <section className="story-section hero-section reveal">
        <div className="section-copy">
          <div className="eyebrow">Personal Portfolio</div>
          <h1>Sai Sushan Govardhanam</h1>
          <p className="hero-copy">{profile.about}</p>
        </div>
      </section>

      <section className="story-section reveal">
        <div className="section-copy">
          <span className="section-kicker">About Me</span>
          <h2>Curious, hands-on, and drawn to systems that solve real problems.</h2>
          <p>
            I enjoy moving between code, data, and product thinking. My work has
            included web applications, forecasting pipelines, and machine
            learning models that translate technical ideas into usable outcomes.
          </p>
        </div>
      </section>

      <section className="story-section reveal">
        <div className="section-copy">
          <span className="section-kicker">Research Interests</span>
          <h2>Areas I want to keep exploring</h2>
          <div className="interest-list">
            {profile.researchInterests.map((interest) => (
              <span key={interest}>{interest}</span>
            ))}
          </div>
        </div>
      </section>

      <section className="story-section split-section reveal">
        <div className="section-copy">
          <span className="section-kicker">Personal Details</span>
          <h2>Quick Facts</h2>
          <dl className="details-list">
            <div>
              <dt>Name</dt>
              <dd>{profile.name}</dd>
            </div>
            <div>
              <dt>Phone</dt>
              <dd>
                <a href={`tel:${profile.phone.replaceAll(" ", "")}`}>
                  {profile.phone}
                </a>
              </dd>
            </div>
            <div>
              <dt>Personal Email</dt>
              <dd>
                <a href={`mailto:${profile.personalEmail}`}>
                  {profile.personalEmail}
                </a>
              </dd>
            </div>
            <div>
              <dt>College Email</dt>
              <dd>
                <a href={`mailto:${profile.collegeEmail}`}>
                  {profile.collegeEmail}
                </a>
              </dd>
            </div>
          </dl>
        </div>

        <div className="section-copy compact-panel">
          <span className="section-kicker">Skills</span>
          <h2>Current Toolkit</h2>
          <div className="skill-columns">
            {skillGroups.map((group) => (
              <div className="skill-group" key={group.label}>
                <h3>{group.label}</h3>
                <div className="chip-row">
                  {group.items.map((item) => (
                    <span className="chip" key={item}>
                      {item}
                    </span>
                  ))}
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="story-section reveal">
        <div className="section-copy">
          <span className="section-kicker">Projects</span>
          <h2>Selected work</h2>
          <div className="home-project-list">
            {projects.map((project) => (
              <article className="home-project reveal" key={project.title}>
                <div>
                  <p className="muted-label">{project.period}</p>
                  <h3>{project.title}</h3>
                  <p>{project.description}</p>
                  <div className="chip-row compact-chips">
                    {project.stack.map((tech) => (
                      <span className="chip" key={tech}>
                        {tech}
                      </span>
                    ))}
                  </div>
                </div>
                <a href={project.repo} target="_blank" rel="noreferrer">
                  GitHub
                </a>
              </article>
            ))}
          </div>
        </div>
      </section>

      <section className="story-section reveal">
        <div className="section-copy">
          <span className="section-kicker">Highlights</span>
          <h2>A few markers from the journey so far.</h2>
          <div className="timeline">
            {profile.highlights.map((item) => (
              <div className="timeline-item" key={item}>
                <span />
                <p>{item}</p>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;

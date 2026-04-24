# Sai Sushan Govardhanam Portfolio

A personal portfolio built with React, React Router, and Vite.

## Pages

- Home: about me, research interests, personal details, skills, and profile section
- Projects: selected projects with GitHub repository links

## Run Locally

```bash
npm install
npm run dev
```

## Build

```bash
npm run build
```

## GitHub Pages Deployment

The project is configured for:

```text
https://twiswiz.github.io/portfolio
```

Push the project to a GitHub repository named `portfolio`, then deploy:

```bash
git init
git add .
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/twiswiz/portfolio.git
git push -u origin main
npm run deploy
```

After deployment, enable GitHub Pages from the repository settings and select
the `gh-pages` branch.

Note: Vite outputs production files into `dist`, so the deploy script uses
`gh-pages -d dist`.

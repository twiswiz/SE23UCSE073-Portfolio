export const profile = {
  name: "Sai Sushan Govardhanam",
  phone: "+91 7036304262",
  personalEmail: "sushan.gov@gmail.com",
  collegeEmail: "se23ucse073@mahindrauniversity.edu.in",
  linkedin: "https://www.linkedin.com/in/sushangovardhanam",
  github: "https://github.com/twiswiz",
  education:
    "B.Tech Computer Science student at Mahindra University with an 8.64/10 CGPA through the 5th semester.",
  about:
    "I am a Computer Science undergraduate who enjoys building practical software systems and experimenting with machine learning, forecasting, and optimization. I like projects that combine clean engineering with useful insights.",
  researchInterests: [
    "Machine learning for decision support",
    "Time-series forecasting and demand prediction",
    "Optimization techniques for AI systems",
    "Evolutionary computing and applied analytics",
  ],
  skills: {
    languages: ["C", "Python", "Java", "MATLAB", "MySQL"],
    tools: ["VS Code", "GitHub", "Jupyter Notebook", "Apache Tomcat"],
  },
  highlights: [
    "Python Intern at Blackbuck Education Pvt Ltd",
    "MU Merit Scholarship recipient",
    "R&D Member at Enigma, the Computer Science Club",
  ],
};

export const projects = [
  {
    title: "Employee Management Portal",
    period: "Dec 2025",
    stack: ["Java", "Servlets", "JDBC", "MySQL"],
    description:
      "An HR portal with role-based login, attendance tracking, leave approval, and payroll modules.",
    impact:
      "Designed the database schema, used prepared statements for safer data access, and deployed the app on Apache Tomcat.",
    repo: "https://github.com/twiswiz/Employment-Management-Portal",
  },
  {
    title: "Electricity Load Forecasting System",
    period: "Nov 2025",
    stack: ["Python", "SARIMA", "GRU", "Time-Series"],
    description:
      "A forecasting system for electricity demand using statistical and deep learning models.",
    impact:
      "Implemented rolling online updates and evaluated multi-step predictions for real-world style demand planning.",
    repo: "https://github.com/twiswiz/Electricity-Load-Forecasting-System",
  },
  {
    title: "Employee Attrition & Financial Impact Prediction",
    period: "May 2025",
    stack: ["Machine Learning", "Random Forest", "SVM", "Logistic Regression"],
    description:
      "A machine learning project that predicts employee attrition and estimates possible salary loss for HR planning.",
    impact:
      "Visualized insights through correlation heatmaps, ROC curves, and salary projection charts.",
    repo: "https://github.com/twiswiz/ML-Project",
  },
];

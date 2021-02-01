# Revoverflow-STARTUP

RevOverflow STARTUP Documentation.

# Technologies Necessary

- Java 8 https://www.java.com/en/download/
- VSCode https://code.visualstudio.com/
- Spring Tool Suite 3.9.13 (for Java 8) https://github.com/spring-projects/toolsuite-distribution/wiki/Spring-Tool-Suite-3
- Git Bash https://git-scm.com/

# Cloning the Git Repository

- Navigate to a file directory within the local machine to make a central hub for all repos.
- Create and navigate to the new directory.
  - mkdir git_repos
  - cd git_repos
- As necessary, create a folder to hold the rev-overflow contents.
- Clone the repos for revoverflow frontend and backend.
  - git clone https://github.com/875-Trevin-Chester/revoverflow-frontend.git
  - git clone https://github.com/875-Trevin-Chester/revoverflow-backend.git
- Navigate to the desired repo.
  - cd |created directory|
- Either create a new branch or navigate to an existing branch.
  - git checkout master
- As necessary, create a new branch and pull from an existing branch.
  - git checkout -b New_Branch_Name
  - git pull origin Trev-Dev
  
# Importing the Front End Files

- Launch VSCode and import the front end project files.
  - File -> Open Folder -> |git_repos| -> |rev-overflow| -> |revoverflow-frontend| -> Select Folder
- Install npm files for project.
  - Terminal -> New Terminal -> bash -> npm -i
- Within the same terminal, start the React project.
  - npm start
- To stop the project running, simply Ctrl+C to terminate the process.

# Importing the Back End Files

- Launch STS and switch to a workspace dedicated to the back end project files (i.e. the folder the backend was cloned to).
  - File -> Switch Workspace -> Other -> |rev-overflow|
- Within the empty workspace, import the project files to the workspace after navigating to the files within .
  - File -> Open Projects from File System... -> Import Source -> Directory... -> |git_repos| -> |rev-overflow| -> |revoverflow-backend| -> Finish
- Navigate to the Boot Dashboard window and run the Spring Boot project.
  - Boot Dashboard -> |revoverflow-backend| -> (Re)start


- git clone https://github.com/875-Trevin-Chester/revoverflow-frontend.git
  - The above git command will bring the repository into your specified directory.
- Import the project files into an IDE (VSCode used in development).
- npm install
  - The above command will install any dependencies and node modules necessary to run the React project.
  
# Noteworthy Syntax

- Text within | | indicates a subjective and theoretical file location.

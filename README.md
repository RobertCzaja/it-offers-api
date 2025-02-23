[![it-offers ci/cd](https://github.com/RobertCzaja/it-offers/actions/workflows/cicd.yml/badge.svg)](https://github.com/RobertCzaja/it-offers/actions/workflows/cicd.yml)
# Installation

1. set GitHub token `git remote set-url origin https://RobertCzaja:<<TOKEN>>@github.com/RobertCzaja/it-offers-api.git`
2. create SSL `jks` key in main catalog 
   * `keytool -keystore keystore.jks -genkey -alias tomcat -keyalg RSA`
   * put password that you've used in `.env` `SSL_PASSWORD`
2. generate JWT Token secret in `.env` `JWT_SECRET`
3. run `docker compose up` to start DB
4. Add to IntelliJ IDEA Application runner active profile `dev` 
5. Install IntelliJ IDEA plugins:
   * WireMock
6. To use `ci` command: set up the alias in 
   * `nano ~/.zshrc`
   * add at the end of the file: `alias ci="/Path/to/Project/it-offers/ci.sh"` 
   * set up the changes: `source ~/.zshrc`

## Static Analysis/tests tools 
* `mvn spotless:apply`
* `mvn pmd:check`
* `mvn test -Dpmd.skip=true`
* `mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Admin1!admin` http://localhost:9000/
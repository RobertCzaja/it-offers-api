[![it-offers ci/cd](https://github.com/RobertCzaja/it-offers/actions/workflows/maven.yml/badge.svg)](https://github.com/RobertCzaja/it-offers/actions/workflows/maven.yml)
# Installation

1. set GitHub token `git remote set-url origin https://RobertCzaja:<<TOKEN>>@github.com/RobertCzaja/it-offers.git`
2. create SSL `jks` key in main catalog 
   * `keytool -keystore keystore.jks -genkey -alias tomcat -keyalg RSA`
   * put password that you've used in `.env` `SSL_PASSWORD`
2. generate JWT Token secret in `.env` `JWT_SECRET`
3. run `docker compose up` to start DB
4. Add to IntelliJ IDEA Application runner active profile `dev`
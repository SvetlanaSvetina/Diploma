# Дипломный проект

### Проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение представляет собой веб-сервис, который предоставляет возможность купить тур с помощью двух способов:

* покупка через дебетовую карту

* покупка в кредит

### Документация:

* План по автоматизации тестирования

* Отчет по итогам автоматизированного тестирования

* Отчет по итогам автоматизации



### Необходимое окружение для запуска проекта:

* Склонируйте репозиторий по ссылке https://github.com/SvetlanaSvetina/Diploma.git

* Откройте проект в IntelliJ IDEA

* Скачайте образы с MySQL, Postgres через команду **docker pull mysql**  и Postgres через команду **docker pull postgres**

* Запустите контейнер, в котором разворачивается база данных MySQL и Postgres (через команду docker-compose up)

* Параметры для запуска хранятся в файле docker-compose.yml

* Запустить симулятор банковского сервиса либо через Docker, либо через установленный Node.js

*Симулятop позволяет генерировать предопределённые ответы для заданного набора карт. Набор карт представлен в формате JSON в файле data.json.*
*Симулятор расположен в каталоге gate-simulator. Для запуска нужно перейти в этот каталог.*
*Симулятор запускается командой npm start на порту 9999*



* Параметры для настройки внешних данных конфигурации (учётные данные и URL для подключения) в файле application.properties

* порты 8080, 9999 и 5432 или 3306 (в зависимости от выбранной базы данных) должны быть свободны

* Запустить SUT во вкладке Terminal Intellij IDEA командой: java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar

* Запустить SUT для БД PostgreSQL - java -Dspring.datasource-postgresql.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar 

* Приложение запускается на порту 8080, по умолчанию используется БД MySQL


### Запуск автотестов

Для запуска авто-тестов в Terminal Intellij IDEA открыть новую сессию и ввести команду: ./gradlew clean test allureReport -Dheadless=true где: 

* - allureReport - подготовка данных для отчета Allure

* - Dheadless=true - запускает авто-тесты в headless-режиме (без открытия браузера)


### Отчетные данные Allure

Для создания отчета тестирования, запустите тесты следующим образом:

* gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport - для БД MysSQL

* gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app allureReport - для БД Postgresql

* allureReport - используется при первой генерации отчета


* При повторной генерации отчета необходимо запускать тесты командой:

* gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app allureServe - для БД MysSQL

* gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app allureServe - для БД Postgresql

* Отчет открывается после прохождения тестов автоматически в браузере по умолчанию.



Для просмотра отчета Allure в терминале ввести команду: ./gradlew allureServe
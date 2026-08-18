# Проект автоматизации тестирования API Book Club

Проект содержит автоматизированные API-тесты сервиса [Book Club](https://book-club.qa.guru).  

## Содержание

- [Технологии и инструменты](#технологии-и-инструменты)
- [Покрытый функционал](#покрытый-функционал)
- [Локальный запуск](#локальный-запуск)
- [Запуск в Jenkins](#запуск-в-jenkins)
- [Allure Report](#allure-report)
- [Allure TestOps](#allure-testops)
- [Интеграция с Jira](#интеграция-с-jira)
- [Уведомления в Telegram](#уведомления-в-telegram)

## Технологии и инструменты

![Java](https://img.shields.io/badge/Java-17%2B-E76F00?logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-9.0-02303A?logo=gradle&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-5.10-25A162?logo=junit5&logoColor=white)
![REST Assured](https://img.shields.io/badge/REST_Assured-5.5-43B02A)
![Allure](https://img.shields.io/badge/Allure_Report-2.40-FCC525)
![Allure TestOps](https://img.shields.io/badge/Allure_TestOps-Test_management-744C9E)
![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939?logo=jenkins&logoColor=white)
![Jira](https://img.shields.io/badge/Jira-Issue_tracking-0052CC?logo=jira&logoColor=white)

- Java и Gradle
- JUnit 5
- REST Assured
- AssertJ и Hamcrest
- Allure TestOps
- Jira
- Jenkins и Telegram Bot

## Покрытый функционал

### Регистрация

- успешная регистрация нового пользователя;
- повторная регистрация существующего пользователя;
- валидация пустых и `null` значений;
- проверка ограничения длины пароля.

### Авторизация и выход

- успешное получение access- и refresh-токенов;
- авторизация с неправильным паролем или несуществующим пользователем;
- валидация обязательных полей;
- успешный logout;
- logout без токена, с невалидным токеном и с access-токеном вместо refresh-токена.

### Профиль пользователя

- полное обновление профиля через `PUT`;
- частичное обновление через `PATCH`;
- проверка обязательных полей и пустых значений.

### Книжные клубы

- создание клуба через `POST`;
- получение и поиск клубов через `GET`;
- обновление клуба через `PATCH`;
- удаление через `DELETE` и проверка отсутствия удалённого клуба.

Все сценарии проверяют HTTP status code. Для ответов также используются проверки JSON Schema, значений по JSON path и десериализованных DTO-моделей.

## Локальный запуск

Требуется Java 17 или новее. 

```bash
./gradlew clean test
```

По умолчанию используются настройки из `local.properties`. При необходимости адрес API можно переопределить из командной строки:

```bash
./gradlew clean test -DbaseUri=https://book-club.qa.guru -DbasePath=/api/v1
```

Сформировать локальный Allure-отчёт:

```bash
./gradlew allureServe
```

## Запуск в Jenkins

Проект настроен для удалённого запуска в [Jenkins](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/).

[![Jenkins job](images/Jenkins3.png)](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/)

Для запуска необходимо открыть job и нажать **Build Now**. Jenkins получает код из GitHub и выполняет:

```bash
./gradlew clean test -Denv=remote
```

В этом режиме используются настройки из `remote.properties`. 

## Allure Report

После завершения сборки Jenkins публикует [Allure Report](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/lastSuccessfulBuild/allure/). Отчёт содержит:

- результаты и длительность тестов;
- группировку по feature и story;
- шаги тестов;
- HTTP-запросы и ответы;
- историю запусков.


[![Графики Allure Report](images/allure%20graf%203.png)](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/lastSuccessfulBuild/allure/#graph)

## Allure TestOps

Результаты запусков из Jenkins автоматически отправляются в проект [Book Club в Allure TestOps](https://allure.qa.guru/project/5350/test-cases?treeId=0). В TestOps доступны:

- [тест-кейсы проекта](https://allure.qa.guru/project/5350/test-cases?treeId=0);
- [история запусков](https://allure.qa.guru/project/5350/launches);
- [Jenkins job](https://allure.qa.guru/project/5350/jobs), связанная с удалёнными прогонами;
- интеграция с Jira для связи запусков и тест-кейсов с задачами.

[![Allure TestOps](images/Allure%20TestOps%20Dashboard2.png)](https://allure.qa.guru/project/5350/test-cases?treeId=0)

## Интеграция с Jira

Проект связан с задачей [MUL-34 — «Смоук API автотесты для Book Club»](https://jira.qa.guru/browse/MUL-34). Успешный запуск Jenkins №9 передан в Allure TestOps и отображается в Jira в блоке **Allure: Launches** с результатом 26 успешно выполненных тестов.

[![Интеграция Jira и Allure TestOps](images/jira3.png)](https://jira.qa.guru/browse/MUL-34)

## Уведомления в Telegram

После завершения Jenkins job Telegram-бот отправляет уведомление с результатом прогона и ссылкой на Allure Report.

Работа интеграции подтверждена [контрольной сборкой №9](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/9/): 26 тестов успешно выполнены, сообщение с диаграммой и ссылкой на отчёт отправлено.

[![Уведомление о результатах тестов в Telegram](images/telegram3.png)](https://jenkins.qa.guru/job/41-m_a_l_qa-diploma-api/9/allure/)

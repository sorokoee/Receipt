# Приложение по работе с чеками

Данное приложение позволяет сформировать товарный чек и распечатать его в консоль или файл.

# Подробное описание

### Версии и технологии

Какие технологии используются в проекте
* [Spring Framework](https://spring.io/)
* [JDK 17](https://www.oracle.com/java/technologies/javase)

### Инструкция для запуска

Для запуска требуется сначала выполнить команду:

.\gradlew build

После этого перейти в папку build/libs и оттуда запускать приложение

**Пример запуска с параметрами**:

java -jar Receipt.jar 3-1 2-5 5-1 card-1234

java -jar Receipt.jar \Receipt\input.txt

В первом случае параметры продуктов чека передаются напрямую. Во втором случае - внутри текстового файла (в том же формате, как и в 1м случае)

Результатом работы приложения будет файл result.txt, в котором сохранится чек. Также чек распечатается в консоль


Приложение для перевода денег между счетами.

Реализованы функции:
По ссылке ../api/beneficiaries можно 
1. посмотреть список всех владельцев счетов.
2. Добавить нового владельца счета
3. добавить новый счет для владельца по его id /api/beneficiaries/{id}/accounts

по ссылке ../api/bank можно
1. выполнять пополнение баланса /api/bank/deposit
2. выполнять снятие наличных с баланса /api/bank/withdraw
3. выполнять переводы между счетами /api/bank/transfer 
 
по ссылке ../api/historis/accounts{id}можно
1. посмотреть историю операций по лицевому счету

При создании приложения использовались следующие технологии:
1. Java 17
2. Spring
3. Spring boot
4. Spring web
5. Spring data jpa
6. ORM Hibernate
7. Библиотека миграции БД Liquibase
8. БД H2 (in memory)
9. Mapstruct
10. Lombok
11. Тестирование с помощью Junit, Mockto

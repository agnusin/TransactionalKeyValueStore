# TransactionalKeyValueStore

Консольное приложение, которое взаимодействует с транзакционным key-value хранилищем. Позволяет отправлять команды, получать и отображать результат

### Набор поддерживаемых команд
```html
SET <key> <value>   //сохраняет значение по ключу
GET <key>           // возвращает значение по ключу
DELETE <key>        // удаляет значение по ключу
COUNT <value>       // возвращает число ключей, которые имеют заданное значение
BEGIN               // начало новой транзакции
COMMIT              // применение текущей транзакции
ROLLBACK            // отмена текущей транзакции
```
### Структура приложения
Приложение реализовано в виде двух Gradle модулей:
* main - основной модуль, в котором реализовано считывание команд вводимых пользователем через консоль и вывод результата их выполнения
* key-value-store - модуль,  в котором реализована функциоанльность key-value хранилища и менеджера транзакций 
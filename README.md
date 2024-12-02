

Мой первый pet-проект - мини-аналог пиццы-фабрики или что-то наподобие этого. Существует два раздела - для гостей сайта и для администратора.</br>
Первый раздел состоит из двух страниц. Первая страница это вводная информация о заведении. Вторая страница - меню заведения. Р.S. Так скажем попытался на html+css сделать актуальный дизайн.</br>
Второй раздел - панель администратора. Перед входом требует логин и пароль(открывается стандартная страница от Spring Boot Security). Дальше начальный дизайн - набор категорий блюд, которые можно добавить/удалить/изменить. При нажатии на категории блюд открывается страница конкретной категории. Список блюд также на странице можно добавить/удалить/изменить блюдо.</br>

В коммитах есть три версии работы с postgre sql:</br>
Первый коммит - через классы DAO с прямыми sql запросами через JdbcTemplate.</br>
Второй коммит - через классы DAO с ORM подходом через EntityManager используя Session(hibernate)</br>
И третья версия(актуальный коммит) - реализация работы с postgre sql через интерфейсы JPA Repository, также добавлено логирование через Spring Boot AOP, также добавлено Spring Boot Security(о котором говорилось ранее).

<!-- Check_VIN -->
<br />
<div align="center">
  <h3 align="center">CinemaChain<br>(сеть кинотеатров)</h3>
</div>


<!-- ABOUT THE PROJECT -->
## О проекте

Проект представляет собой сайт для использования кинотеатром или сетью кинотеатров.

Возможности с точки зрения пользователя: выбор актуальных фильмов в кино, выбор доступных киносеансов в различных
кинотеатрах и кинозалах, выбор мест в кинозале и покупка билетов на киносеансы. Последнее только для зарегистрированных пользователей.

Возможности с точки зрения администратора сайта: добавление новых фильмов на киносайт (изначально новые фильмы попадают
в секцию сайта "Скоро в кино"), добавление новых киносеансов для фильмов, изменение информации для уже существующих фильмов и сеансов.
Все добавляемые/изменяемые данные валидируются на сервере.

Проект покрыт unit-тестами и интеграционными тестами. Процент покрытия классов - 90%, строк - 93%.

Эта версия проекта неокончательная, работа над ним будет продолжаться.




## Перед тем, как запустить

Необходимо установить PostgreSQL 15.6 и создать БД с именем cinema.
Для запуска интеграционных тестов, на компьютер нужно установить Docker и запустить.




## Иллюстрации сайта
<div>
  <img src="https://github.com/user-attachments/assets/b0445939-55b8-4ba7-b49c-c947a1a10f13">
  <h5 align="center">Рисунок 1 - Основной вид сайта (1)</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/be66a102-79b1-4abe-8eb2-673cfba810a7">
  <h5 align="center">Рисунок 2 - Основной вид сайта (2)</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/4810449a-f6d9-42d2-b95d-22c6b1f4ca41">
  <h5 align="center">Рисунок 3 - Страница для администратора</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/e795875b-0d48-4c9f-97de-4bb649beee00">
  <h5 align="center">Рисунок 4 - Редактирование информации о фильме и работа валидации</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/98806f66-ab85-4f20-a5c9-bb989151d103">
  <h5 align="center">Рисунок 5 - Информация о фильме</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/cca335b1-828a-41a5-b611-7d5d4f7462bb">
  <h5 align="center">Рисунок 6 - Демонстрация выбора киносеанса на фильм</h5>
</div>
<div>
  <img src="https://github.com/user-attachments/assets/2fac1231-1cd7-4534-8ebc-e8d5a19ffcc0">
  <h5 align="center">Рисунок 7 - Выбор мест в кинозале для покупки билетов</h5>
</div>





### Built With

- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Liquibase
- Thymeleaf
- Lombok
- Spring Test
- Mockito


# Mini Task Tracker (backend)

Учебный проект: Spring Boot 4, Java 17, PostgreSQL, JWT.
Готово: **регистрация, вход, JWT-защита, GET /api/users/me**. Остальное (проекты, задачи, комментарии, метки) делаем сами.

## Запуск

```bash
docker compose up -d           # PostgreSQL на 5432
mvn spring-boot:run            # порт 8081
```

Настройки БД и секрета JWT берутся из `application.yml`, их можно переопределить переменными
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET` (Base64, минимум 64 байта).

## Проверка в Postman / curl

```bash
# 1. Регистрация -> 201 и token
curl -X POST localhost:8081/api/auth/sign-up -H "Content-Type: application/json" \
  -d '{"fullName":"Aibek T","email":"aibek@mail.kg","password":"12345678"}'

# 2. Вход -> 200 и token
curl -X POST localhost:8081/api/auth/sign-in -H "Content-Type: application/json" \
  -d '{"email":"aibek@mail.kg","password":"12345678"}'

# 3. Защищённый эндпоинт -> 200; без токена -> 401
curl localhost:8081/api/users/me -H "Authorization: Bearer <TOKEN>"
```

## Коды ответов

| Ситуация | Код |
|---|---|
| Успешная регистрация | 201 |
| Ошибка валидации (пустое поле, плохой email, короткий пароль) | 400 |
| Неверный пароль, нет токена, токен просрочен или подделан | 401 |
| Токен верный, но роли не хватает | 403 |
| Email уже занят | 409 |

## Структура

```
config/      SecurityConfig  (цепочка фильтров, правила доступа, BCrypt)
controller/  AuthController, UserController
dto/         запросы и ответы (record)
entity/      User, Role
exception/   GlobalExceptionHandler, ApiError
repository/  UserRepository
security/    JwtService, JwtAuthFilter, CustomUserDetailsService
service/     AuthService
```

## Как добавлять новые эндпоинты

1. Новый путь по умолчанию **закрыт**: `anyRequest().authenticated()`. Открытые пути перечисляются в `SecurityConfig`.
2. Права по ролям: `.requestMatchers("/api/admin/**").hasRole("ADMIN")` или `@PreAuthorize` (нужен `@EnableMethodSecurity`).
3. Текущий пользователь в контроллере: параметр `Authentication authentication`, email = `authentication.getName()`.
4. Ошибки бросайте своими исключениями и добавляйте обработчик в `GlobalExceptionHandler`.

## Частые проблемы

- **403 на POST /sign-up**: включён CSRF. Здесь он отключён в `SecurityConfig`.
- **Lombok не работает в IDE**: установите плагин Lombok и включите *Enable annotation processing*.
- **Не запускается из-за БД**: проверьте, что `docker compose up -d` отработал.

## Права доступа (упрощённая схема)

| Кто | Что может |
|---|---|
| Владелец проекта (`project.owner`) | редактировать и удалять проект, добавлять участников |
| Участник проекта | смотреть проект, создавать задачи, писать комментарии, менять статус своих задач |
| `ADMIN` | всё и везде |
| Остальные | 403 |

Заготовки уже есть: `ForbiddenException` (403), `NotFoundException` (404) и `CurrentUser.email()` / `CurrentUser.isAdmin()`.
Пример проверки в сервисе (когда появится сущность `Project`):

```java
private void checkMember(Project project) {
    if (CurrentUser.isAdmin()) return;
    String email = CurrentUser.email();
    boolean ok = project.getOwner().getEmail().equals(email)
            || project.getMembers().stream().anyMatch(u -> u.getEmail().equals(email));
    if (!ok) throw new ForbiddenException("Вы не участник проекта");
}

private void checkOwner(Project project) {
    if (CurrentUser.isAdmin()) return;
    if (!project.getOwner().getEmail().equals(CurrentUser.email())) {
        throw new ForbiddenException("Только владелец проекта");
    }
}
```
# task-tracker-23

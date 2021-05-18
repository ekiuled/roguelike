# roguelike
Консольная roguelike-игра.

# Авторы
* Леденева Екатерина ([@ekiuled](https://github.com/ekiuled))
* Елисеев Егор ([@EliseevEgor](https://github.com/EliseevEgor))

# Зависимости
Перед запуском игры необходимо [установить](https://www.rabbitmq.com/download.html) и запустить сервис `rabbitmq`. Например, в Arch Linux это можно сделать так:
```bash
pacman -S rabbitmq
systemctl start rabbitmq.service 
```

Также необходимо настроить сервис, запустив следующие команды:
```bash
rabbitmqctl add_user 'roguelike' 'roguelike'
rabbitmqctl add_vhost roguelike
rabbitmqctl set_permissions -p "roguelike" "roguelike" ".*" ".*" ".*"
```

# Использование
## Singleplayer
Игра запускается командой `./gradlew singleplayer:run` в корне проекта. Генерируется лабиринт и игрок (`@`), которым можно управлять с помощью <kbd>WASD</kbd>.
Чтобы выйти из игры, нажмите на <kbd>Esc</kbd> или закройте окно.
## Multiplayer
Сервер запускается командой `./gradlew server:run --args=<IP>`, где `<IP>` — адрес сервера. Клиенты подключаются командой `./gradlew client:run --args='<username> <IP>'`, где `<username>` — имя игрока, `<IP>` — адрес сервера. `<IP>` можно не указывать, тогда будет использоваться `localhost`.

# Документация
См. [Design Document.md](https://github.com/ekiuled/roguelike/blob/main/Design%20Document.md).

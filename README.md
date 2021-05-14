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

# Использование
Игра запускается командой `./gradlew singleplayer:run` в корне проекта. Генерируется лабиринт и игрок (`@`), которым можно управлять с помощью <kbd>WASD</kbd>.
Чтобы выйти из игры, нажмите на <kbd>Esc</kbd> или закройте окно.

# Документация
См. [Design Document.md](https://github.com/ekiuled/roguelike/blob/main/Design%20Document.md).

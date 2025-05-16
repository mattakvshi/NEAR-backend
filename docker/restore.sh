#!/bin/bash
BACKUP_DIR="./backups"

# Выбираем последний дамп (можно переключать между форматами)
NEAR_DUMP=$(ls -t $BACKUP_DIR/near_*.dump | head -1)
NEAR_SQL=$(ls -t $BACKUP_DIR/near_*.sql | head -1)
TELEGRAM_DUMP=$(ls -t $BACKUP_DIR/telegram_*.dump | head -1)
TELEGRAM_SQL=$(ls -t $BACKUP_DIR/telegram_*.sql | head -1)

# Восстановление Near DB (по умолчанию используем бинарный формат)
echo "Restoring Near DB from $NEAR_DUMP"
docker exec -i near_db psql -U postgres -c "DROP DATABASE IF EXISTS FirstDB4NEAR; CREATE DATABASE FirstDB4NEAR;"
docker exec -i near_db pg_restore -U postgres -d FirstDB4NEAR < $NEAR_DUMP

# Восстановление Telegram DB (по умолчанию используем SQL-формат)
echo "Restoring Telegram DB from $TELEGRAM_SQL"
docker exec -i telegram_db psql -U postgres -c "DROP DATABASE IF EXISTS TelegramInfoDB; CREATE DATABASE TelegramInfoDB;"
docker exec -i telegram_db psql -U postgres -d TelegramInfoDB -f $TELEGRAM_SQL

echo "Restore completed"
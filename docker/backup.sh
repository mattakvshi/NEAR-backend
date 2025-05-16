#!/bin/bash
# Бэкап в двух форматах: .dump (бинарный) и .sql (текстовый)
BACKUP_DIR="./backups"
DATE=$(date +%Y-%m-%d_%H-%M-%S)

# Near DB
docker exec near_db pg_dump -U postgres -Fc FirstDB4NEAR > $BACKUP_DIR/near_$DATE.dump
docker exec near_db pg_dump -U postgres --format=plain FirstDB4NEAR > $BACKUP_DIR/near_$DATE.sql

# Telegram DB
docker exec telegram_db pg_dump -U postgres -Fc TelegramInfoDB > $BACKUP_DIR/telegram_$DATE.dump
docker exec telegram_db pg_dump -U postgres --format=plain TelegramInfoDB > $BACKUP_DIR/telegram_$DATE.sql

echo "Backup created in $BACKUP_DIR/"
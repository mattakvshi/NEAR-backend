#!/bin/bash
BACKUP_DIR="./backups"

# Выбираем последний дамп (можно переключать между форматами)
NEAR_DUMP=$(ls -t $BACKUP_DIR/near_*.dump | head -1)
TELEGRAM_DUMP=$(ls -t $BACKUP_DIR/telegram_*.dump | head -1)

# Убедимся, что файлы существуют
[ -f "$NEAR_DUMP" ] || { echo "Near dump file not found!"; exit 1; }
[ -f "$TELEGRAM_DUMP" ] || { echo "Telegram dump file not found!"; exit 1; }

# --- Восстановление Near DB ---
echo "Restoring Near DB from $NEAR_DUMP"

# Чистое удаление и создание БД
docker exec -i near_db psql -U postgres -c "DROP DATABASE IF EXISTS FirstDB4NEAR;"
docker exec -i near_db psql -U postgres -c "CREATE DATABASE FirstDB4NEAR WITH TEMPLATE template0;"

# Восстановление с чисткой старых объектов
docker exec -i near_db pg_restore -U postgres -d FirstDB4NEAR --clean --if-exists < "$NEAR_DUMP"

# --- Восстановление Telegram DB ---
echo "Restoring Telegram DB from $TELEGRAM_DUMP"

docker exec -i telegram_db psql -U postgres -c "DROP DATABASE IF EXISTS TelegramInfoDB;"
docker exec -i telegram_db psql -U postgres -c "CREATE DATABASE TelegramInfoDB;"

docker exec -i telegram_db pg_restore -U postgres -d TelegramInfoDB --clean --if-exists < "$TELEGRAM_DUMP"
#docker exec -i telegram_db psql -U postgres -d TelegramInfoDB -f "$TELEGRAM_DUMP"


echo "Restore completed"

# =============================
# === Проверка наличия таблиц ===
# =============================

# --- Проверка Near DB ---
echo -e "\n=== Checking tables in 'FirstDB4NEAR' (Near DB) ==="
NEAR_TABLES=$(docker exec -i near_db psql -U postgres -d FirstDB4NEAR -t -A -c "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';")

if [ -z "$NEAR_TABLES" ]; then
  echo "No tables found in FirstDB4NEAR."
else
  echo "Tables in FirstDB4NEAR:"
  echo "$NEAR_TABLES"
fi

# --- Проверка Telegram DB ---
echo -e "\n=== Checking tables in 'TelegramInfoDB' (Telegram DB) ==="
TELEGRAM_TABLES=$(docker exec -i telegram_db psql -U postgres -d TelegramInfoDB -t -A -c "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';")

if [ -z "$TELEGRAM_TABLES" ]; then
  echo "No tables found in TelegramInfoDB."
else
  echo "Tables in TelegramInfoDB:"
  echo "$TELEGRAM_TABLES"
fi

# --- Пример: Проверка конкретных таблиц ---
# Можете добавить или изменить на нужные вам
NEAR_EXPECTED_TABLES=(
"communities"
"email_verification_token"
"email_change_storage"
"community_account_data"
"owners_data"
"friend_requests_received"
"subscribers_subscriptions"
"user_refresh_storage"
"groups"
"groups_members"
"friend_requests_sent"
"emergency_type"
"monitored_emergency"
"user_account_data"
"notification_options"
"selected_options"
"notification_templates"
"users"
"user_friends"
"community_refresh_storage"
) 


TELEGRAM_EXPECTED_TABLES=("user_info")  # замените на актуальные

function check_tables_exist() {
  local db_container=$1
  local dbname=$2
  local user=$3
  shift 3
  local tables=("$@")

  echo -e "\nChecking expected tables in '$dbname':"
  for table in "${tables[@]}"; do
    exists=$(docker exec -i "$db_container" psql -U "$user" -d "$dbname" -t -c "SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = '$table');")
    if [[ "$exists" == *"t"* ]]; then
      echo "✅ Table '$table' exists."
    else
      echo "❌ Table '$table' does NOT exist!"
    fi
  done
}

# Проверяем конкретные таблицы
check_tables_exist near_db FirstDB4NEAR postgres "${NEAR_EXPECTED_TABLES[@]}"
check_tables_exist telegram_db TelegramInfoDB postgres "${TELEGRAM_EXPECTED_TABLES[@]}"
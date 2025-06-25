#!/bin/bash

# Настройки
DB_CONTAINER="near_db"       # имя Docker-контейнера с PostgreSQL
DB_NAME="FirstDB4NEAR"       # имя вашей БД
DB_USER="postgres"           # пользователь БД

# Таблицы, которые НЕ нужно очищать
EXCLUDE_TABLES=("emergency_type" "notification_options")  # <-- добавляй сюда те, что не трогаем

echo "Очистка таблиц в БД '$DB_NAME', кроме: ${EXCLUDE_TABLES[*]}..."

# Получаем список всех таблиц в схеме public
TABLES=$(docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -t -A -c "
    SELECT table_name FROM information_schema.tables
    WHERE table_schema = 'public';
")

# Проверяем, есть ли таблицы
if [ -z "$TABLES" ]; then
  echo "Нет таблиц в схеме public."
  exit 0
fi

# Формируем список таблиц для очистки, исключая те, что в EXCLUDE_TABLES
TABLES_TO_CLEAR=()
for table in $TABLES; do
  exclude=false
  for exclude_table in "${EXCLUDE_TABLES[@]}"; do
    if [[ "$table" == "$exclude_table" ]]; then
      exclude=true
      break
    fi
  done
  if ! $exclude; then
    TABLES_TO_CLEAR+=("$table")
  fi
done

# Если нет таблиц для очистки — выходим
if [ ${#TABLES_TO_CLEAR[@]} -eq 0 ]; then
  echo "Нет таблиц для очистки. Все таблицы находятся в списке исключений."
  exit 0
fi

# Выводим список таблиц, которые будем очищать
echo "Будут очищены следующие таблицы:"
printf ' - %s\n' "${TABLES_TO_CLEAR[@]}"

# Формируем команду TRUNCATE
TABLES_STR=$(IFS=','; echo "${TABLES_TO_CLEAR[*]}")
TRUNCATE_SQL="TRUNCATE TABLE $TABLES_STR CASCADE;"

# Выполняем команду
echo "Выполняется очистка..."
docker exec -i "$DB_CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" -c "$TRUNCATE_SQL"

# Проверяем результат
if [ $? -eq 0 ]; then
  echo "✅ Успешно очищено: ${#TABLES_TO_CLEAR[@]} таблиц."
else
  echo "❌ Ошибка при очистке таблиц."
  exit 1
fi